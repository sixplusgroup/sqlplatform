package com.example.sqlexercise.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.example.sqlexercise.config.YmlProperties;
import com.example.sqlexercise.lib.Constants;
import com.example.sqlexercise.lib.ResultOfTask;
import com.example.sqlexercise.lib.SqlDatabase;
import com.example.sqlexercise.lib.SqlDatabasePool;
import com.example.sqlexercise.po.SubQuestion;
import com.example.sqlexercise.service.QuestionService;
import com.example.sqlexercise.service.SqlDatabaseService;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

@Slf4j(topic = "com.example.sqlexercise.serviceImpl.SqlDatabaseServiceImpl")
@Service
public class SqlDatabaseServiceImpl implements SqlDatabaseService {

    private final RedisTemplate<Integer, Object> redisTemplate;
    private final Cache<Integer, Object> caffeineCache;

    private final SqlDatabasePool sqlDatabasePool;

    private final QuestionService questionService;

    private Semaphore[] semaphores;

    @Autowired
    public SqlDatabaseServiceImpl(QuestionService questionService, SqlDatabasePool sqlDatabasePool,
                                  RedisTemplate<Integer, Object> redisTemplate,
                                  @Qualifier("localCacheManager") Cache<Integer, Object> caffeineCache, YmlProperties ymlProperties) {
        this.questionService = questionService;
        this.sqlDatabasePool = sqlDatabasePool;
        this.redisTemplate = redisTemplate;
        this.caffeineCache = caffeineCache;
        this.semaphores = new Semaphore[ymlProperties.getOceanbaseTableDuplicateNum()];
        for (int i = 0; i < this.semaphores.length; i++) {
            this.semaphores[i] = new Semaphore(1);
        }
    }

    @Override
    public Object runMysqlTask(int mainId, String driver, String sqlText, Map<String, Object> options) {
        int maxRetryTimes = (int) options.getOrDefault("maxRetryTimes", 1);
        boolean forEach = (boolean) options.getOrDefault("forEach", false);
        boolean skipPre = (boolean) options.getOrDefault("skipPre", false);
        boolean skipPost = (boolean) options.getOrDefault("skipPost", false);
        String schemaName = questionService.getSchemaNameByMainId(mainId);
        String schemaConstructor = questionService.getSchemaConstructorByMainId(mainId);
        if (forEach) {
            ArrayList<SqlDatabase> sqlDatabaseArrayList = sqlDatabasePool.getSqlDatabaseList(schemaName, driver);
            ArrayList<ResultOfTask> results = new ArrayList<>();
            for (SqlDatabase sqlDatabase : sqlDatabaseArrayList) {
                if (!skipPre) {
                    sqlDatabase.preTask(schemaConstructor);
                }
                results.add(sqlDatabase.task(sqlText, maxRetryTimes));
                if (!skipPost) {
                    sqlDatabase.postTask();
                }
            }
            return results;
        } else {
            // 选择一个数据库
            SqlDatabase sqlDatabase = sqlDatabasePool.pickSqlDatabase(schemaName, driver);
            if (!skipPre) {
                sqlDatabase.preTask(schemaConstructor);
            }
            ResultOfTask result = sqlDatabase.task(sqlText, maxRetryTimes);
            if (!skipPost) {
                sqlDatabase.postTask();
            }
            return result;
        }
    }

    @Override
    public Map<String, String> runOceanbaseTask(String createIndexSql, String querySql, int targetTableNum) {
        String schemaName = "index_test";
        String driver = "oceanbase";
        SqlDatabase sqlDatabase = sqlDatabasePool.pickSqlDatabase(schemaName, driver);

        try {
            semaphores[targetTableNum].acquire();

            // 删除所有索引，保证初始状态下没有任何索引
            dropAllIndexForOceanBase(sqlDatabase, targetTableNum);

            int runTimeCount = 3;
            // 无索引时，运行sql，运行runTimeCount次
            for (int i = 0; i < runTimeCount; i++) {
                ResultOfTask queryResultSet1 = sqlDatabase.task(querySql, 1);
            }
            // 获取平均执行时间
            int avgQueryTimeWithoutIndex = getAvgExecuteTimeForOceanBase(sqlDatabase, runTimeCount, querySql);

            // 创建索引
            boolean isIndexCreationSucceed = sqlDatabase.operateIndex(createIndexSql);
            // 如果创建索引失败，则直接返回
            if (!isIndexCreationSucceed) {
                return null;
            }
            int createIndexTime = getAvgExecuteTimeForOceanBase(sqlDatabase, 1, createIndexSql);

            // 创建索引后，重新运行sql，运行runTimeCount次
            // 此处在查询的where条件中与创建索引前的查询语句做以区分，因为获取执行时间时涉及sql内容
            querySql = querySql.replaceAll("ORDER", "AND 1 = 1 ORDER");
            for (int i = 0; i < runTimeCount; i++) {
                ResultOfTask queryResultSet2 = sqlDatabase.task(querySql, 1);
            }
            // 获取创建索引后的平均执行时间
            int avgQueryTimeAfterIndexCreation = getAvgExecuteTimeForOceanBase(sqlDatabase, runTimeCount, querySql);

            // 删除创建的所有索引
            dropAllIndexForOceanBase(sqlDatabase, targetTableNum);

            semaphores[targetTableNum].release();

            Map<String, String> result = new HashMap<>();
            result.put("queryTimeWithoutIndex", avgQueryTimeWithoutIndex + "ms");
            result.put("createIndexTime", createIndexTime + "ms");
            result.put("queryTimeAfterIndexCreation", avgQueryTimeAfterIndexCreation + "ms");
            return result;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (semaphores[targetTableNum].availablePermits() == 0) {
                semaphores[targetTableNum].release();
            }
        }
        return null;
    }

    /**
     * 仅供OceanBase使用，获取最近n次执行查询语句的平均耗时
     *
     * @param querySql 被执行的查询语句
     * @return 执行平均耗时，单位 ms
     */
    private int getAvgExecuteTimeForOceanBase(SqlDatabase sqlDatabase, int n, String querySql) {
        ResultOfTask time = sqlDatabase.task("SELECT EXECUTE_TIME, QUERY_SQL, RETURN_ROWS, REQUEST_TIME\n" +
                "FROM oceanbase.GV$OB_SQL_AUDIT\n" +
                "WHERE DB_NAME = 'index_test' AND RET_CODE = 0 AND QUERY_SQL = '" + querySql + "'\n" +
                "ORDER BY REQUEST_TIME DESC\n" +
                "LIMIT " + n + ";", 1);
        return time.sheet.stream().mapToInt((ArrayList<String> s) -> Integer.parseInt(s.get(0))).sum() / n / 1000;
    }

    /**
     * 仅供OceanBase使用，删除表中除主键索引外的所有索引
     *
     * @param targetTableNum 目标表编号
     */
    private void dropAllIndexForOceanBase(SqlDatabase sqlDatabase, int targetTableNum) {
        // 查询除主键外，创建的所有索引
        String selectIndexSql = "SELECT DISTINCT INDEX_NAME FROM INFORMATION_SCHEMA.STATISTICS\n" +
                "WHERE TABLE_SCHEMA = 'index_test' and TABLE_NAME = 'app_user' and INDEX_NAME <> 'primary';"
                        .replaceAll("app_user", "app_user_" + targetTableNum);
        ResultOfTask selectIndexResultSet = sqlDatabase.task(selectIndexSql, 1);
        if (selectIndexResultSet.sheet.size() > 0) {
            // 删除创建的索引. drop index email_birthday_idx on app_user_i
            StringBuilder dropIndexSql = new StringBuilder("drop index ");
            for (ArrayList<String> line : selectIndexResultSet.sheet) {
                String indexName = line.get(0);
                dropIndexSql.append(indexName).append(" ");
            }
            dropIndexSql.append("on app_user;".replaceAll("app_user", "app_user_" + targetTableNum));
            sqlDatabase.operateIndex(dropIndexSql.toString());
        }
    }

    @Override
//    @Cacheable("standardAnswer")
    public ResultOfTask getStandardAnswer(int subId, String driver) {
        // 1.先从Caffeine缓存中读取
        Object o = caffeineCache.getIfPresent(subId);
        if (Objects.nonNull(o)) {
            System.out.println("从Caffeine中查询到数据...");
            return (ResultOfTask) o;
        }

        // 2.如果缓存中不存在，则从Redis缓存中查找
        String jsonString = (String) redisTemplate.opsForValue().get(subId);
        ResultOfTask result = JSON.parseObject(jsonString, ResultOfTask.class);
        if (Objects.nonNull(result)) {
            System.out.println("从Redis中查询到数据...");

            // 保存Caffeine缓存
            caffeineCache.put(subId, result);
            return result;
        }

        // 3.如果Redis缓存中不存在，则从数据库中查询
        SubQuestion subQuestion = questionService.getSubQuestionBySubId(subId);
        int mainId = subQuestion.getMainId();
        String sqlText = subQuestion.getAnswer();
        boolean ordered = subQuestion.isOrdered();
        Map<String, Object> options = new HashMap<>();
        options.put("skipPost", true);
        result = (ResultOfTask) this.runMysqlTask(mainId, driver, sqlText, options);
        result.ordered = ordered;

        if (result != null) {
            // 保存Caffeine缓存
            caffeineCache.put(subId, result);

            // 保存Redis缓存,20s后过期
            redisTemplate.opsForValue().set(subId, JSON.toJSONString(result), 15, TimeUnit.MINUTES);
        }

        System.out.println("从数据库中查询到数据...");
        return result;
    }

//    @Override
//    public ResultOfTask runTaskOfGettingSchemaInfo(int mainId, String driver) {
//        String schemaName = questionService.getSchemaNameByMainId(mainId);
//        Client client = null;
//        if (driver.equals("mysql")) {
//            client = new MysqlClient();
//        }
//        String sqlText = client.getSchemaSql(schemaName);
//        Map<String, Object> options = new HashMap<>();
//        options.put("skipPre", true);
//        options.put("skipPost", true);
//        return (ResultOfTask) this.runSqlTask(mainId, driver, sqlText, options);
//    }
//
//    @Override
//    public ArrayList<ResultOfTask> runTaskOfUpdatingSchemaInfo(int mainId, String driver) {
//        String schemaName = questionService.getSchemaNameByMainId(mainId);
//        String schemaConstructor = questionService.getSchemaConstructorByMainId(mainId);
//        Client client = null;
//        if (driver.equals("mysql")) {
//            client = new MysqlClient();
//        }
//        String sqlText = client.initSchemaSql(schemaName) + schemaConstructor;
//        Map<String, Object> options = new HashMap<>();
//        options.put("forEach", true);
//        options.put("skipPre", true);
//        options.put("skipPost", true);
//        return (ArrayList<ResultOfTask>) this.runSqlTask(mainId, driver, sqlText, options);
//    }
//
//    @Override
//    public ArrayList<ResultOfTask> runTaskOfCleaningSchema(int mainId, String driver) {
//        String schemaName = questionService.getSchemaNameByMainId(mainId);
//        Client client = null;
//        if (driver.equals("mysql")) {
//            client = new MysqlClient();
//        }
//        String sqlText = client.cleanSchemaSql(schemaName);
//        Map<String, Object> options = new HashMap<>();
//        options.put("forEach", true);
//        options.put("skipPre", true);
//        options.put("skipPost", true);
//        return (ArrayList<ResultOfTask>) this.runSqlTask(mainId, driver, sqlText, options);
//    }

}
