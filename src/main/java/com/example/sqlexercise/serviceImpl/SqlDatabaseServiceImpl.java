package com.example.sqlexercise.serviceImpl;

import com.example.sqlexercise.data.MainQuestionMapper;
import com.example.sqlexercise.data.SubQuestionMapper;
import com.example.sqlexercise.driver.Client;
import com.example.sqlexercise.driver.MysqlClient;
import com.example.sqlexercise.lib.ResultOfTask;
import com.example.sqlexercise.lib.SqlDatabase;
import com.example.sqlexercise.lib.SqlDatabasePool;
import com.example.sqlexercise.po.MainQuestion;
import com.example.sqlexercise.po.SubQuestion;
import com.example.sqlexercise.service.SqlDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class SqlDatabaseServiceImpl implements SqlDatabaseService {

    private final SqlDatabasePool sqlDatabasePool;
    private final MainQuestionMapper mainQuestionMapper;
    private final SubQuestionMapper subQuestionMapper;

    @Autowired
    public SqlDatabaseServiceImpl(MainQuestionMapper mainQuestionMapper, SubQuestionMapper subQuestionMapper, SqlDatabasePool sqlDatabasePool) {
        this.mainQuestionMapper = mainQuestionMapper;
        this.subQuestionMapper = subQuestionMapper;
        this.sqlDatabasePool = sqlDatabasePool;
    }

    @Override
    public String getSchemaNameByMainId(int mainId){
        if(mainQuestionMapper.getByMainId(mainId)==null){
            System.out.println("Info of main question "+mainId+" is not found!");
            return "error!";
        }
        return "main_"+mainId;
    }

    @Override
    public String getSchemaConstructorByMainId(int mainId){
        MainQuestion mainQuestion = mainQuestionMapper.getByMainId(mainId);
        if(mainQuestion==null){
            System.out.println("Info of main question "+mainId+" is not found!");
        }
        try {
            Path dbPath = Paths.get("src/main/resources"+mainQuestion.getDbPath());
            if (!Files.exists(dbPath)) {
                System.out.println("File of main question" + mainId + "is not found!");
            }
            return Files.readString(dbPath);
        }catch (Exception e){
            e.printStackTrace();
            return "IOException occurred!";
        }
    }

    @Override
    public SubQuestion getSubQuestionBySubId(int subId){
        return subQuestionMapper.getBySubId(subId);
    }

    @Override
    public Object runSqlTask(int mainId, String driver, String sqlText, Map options){
        int maxRetryTimes = Integer.parseInt(options.getOrDefault("maxRetryTimes", 1).toString());
        boolean forEach = Boolean.parseBoolean(options.getOrDefault("forEach", false).toString());
        boolean skipPre = Boolean.parseBoolean(options.getOrDefault("skipPre", false).toString());
        boolean skipPost = Boolean.parseBoolean(options.getOrDefault("skipPost", false).toString());
        String schemaName = getSchemaNameByMainId(mainId);
        String schemaConstructor = getSchemaConstructorByMainId(mainId);
        if(forEach){
            ArrayList<SqlDatabase> sqlDatabaseArrayList = sqlDatabasePool.getSqlDatabaseList(schemaName, driver);
            ArrayList<ResultOfTask> results = new ArrayList<>();
            for(SqlDatabase sqlDatabase : sqlDatabaseArrayList ){
                if(!skipPre) {
                    sqlDatabase.pretask(schemaConstructor);
                }
                results.add(sqlDatabase.task(sqlText, maxRetryTimes));
                if(!skipPost){
                    sqlDatabase.posttask();
                }
            }
            return results;
        }else{
            SqlDatabase sqlDatabase = sqlDatabasePool.pickSqlDatabase(schemaName, driver);
            if(!skipPre) {
                sqlDatabase.pretask(schemaConstructor);
            }
            ResultOfTask result = sqlDatabase.task(sqlText, maxRetryTimes);
            if(!skipPost){
                sqlDatabase.posttask();
            }
            return result;
        }
    }

    @Override
    public ResultOfTask getStandardAnswer(int subId, String driver){
        SubQuestion subQuestion = subQuestionMapper.getBySubId(subId);
        int mainId = subQuestion.getMainId();
        String sqlText = subQuestion.getAnswer();
        boolean ordered = subQuestion.isOrdered();
        Map options = new HashMap();
        options.put("skipPost", true);
        ResultOfTask result = (ResultOfTask) this.runSqlTask(mainId, driver, sqlText, options);
        result.ordered = ordered;
        return result;
    }

    @Override
    public ResultOfTask runTaskOfGettingSchemaInfo(int mainId, String driver){
        String schemaName = this.getSchemaNameByMainId(mainId);
        Client client = null;
        if(driver.equals("mysql")){
             client = new MysqlClient();
        }
        String sqlText = client.getSchemaSql(schemaName);
        Map options = new HashMap();
        options.put("skipPre", true);
        options.put("skipPost", true);
        return (ResultOfTask) this.runSqlTask(mainId, driver, sqlText, options);
    }

    @Override
    public ArrayList<ResultOfTask> runTaskOfUpdatingSchemaInfo(int mainId, String driver){
        String schemaName = this.getSchemaNameByMainId(mainId);
        String schemaConstructor = this.getSchemaConstructorByMainId(mainId);
        Client client = null;
        if(driver.equals("mysql")){
            client = new MysqlClient();
        }
        String sqlText = client.initSchemaSql(schemaName)+schemaConstructor;
        Map options = new HashMap();
        options.put("forEach", true);
        options.put("skipPre", true);
        options.put("skipPost", true);
        return (ArrayList<ResultOfTask>) this.runSqlTask(0, driver, sqlText, options);
    }

    @Override
    public ArrayList<ResultOfTask> runTaskOfCleaningSchema(int mainId, String driver){
        String schemaName = this.getSchemaNameByMainId(mainId);
        Client client = null;
        if(driver.equals("mysql")){
            client = new MysqlClient();
        }
        String sqlText = client.cleanSchemaSql(schemaName);
        Map options = new HashMap();
        options.put("forEach", true);
        options.put("skipPre", true);
        options.put("skipPost", true);
        return (ArrayList<ResultOfTask>) this.runSqlTask(0, driver, sqlText, options);
    }

}
