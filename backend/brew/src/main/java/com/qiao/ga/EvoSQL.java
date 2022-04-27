package com.qiao.ga;

import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import com.github.sergdelft.sqlcorgi.SQLCorgi;
import com.github.sergdelft.sqlcorgi.schema.Schema;
import com.qiao.spring.pojo.Table;
import com.qiao.ga.db.SchemaConverter;
import net.sf.jsqlparser.JSQLParserException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qiao.ga.db.SeedExtractor;
import com.qiao.ga.db.Seeds;
import com.qiao.ga.fixture.Fixture;
import com.qiao.ga.metaheuristics.Approach;
import com.qiao.ga.metaheuristics.StandardGA;
//import com.qiao.ga.metaheuristics.RandomApproach;
import com.qiao.ga.sql.ColumnSchema;
import com.qiao.ga.sql.TableSchema;
import com.qiao.ga.sql.parser.SqlSecurer;
import com.qiao.ga.sql.parser.UsedColumnExtractor;

public class EvoSQL {

    private static final Logger log = LogManager.getLogger(EvoSQL.class);

    private static class PathState {
        int pathNo;
        String path;
        Approach approach;
        long timePassed;
        long timeBudget;
        List<Fixture> population;
        Set<ColumnSchema> usedColumns;

        PathState(int pathNo, String path, Approach approach, long timePassed, List<Fixture> population, long timeBudget) {
            this.pathNo = pathNo;
            this.path = path;
            this.approach = approach;
            this.timePassed = timePassed;
            this.timeBudget = timeBudget;
            this.population = population;
            usedColumns = null;
        }
    }

    private final boolean baseline;

    public EvoSQL(boolean baseline) {
        this.baseline = baseline;
    }

    public Result execute(String sqlToBeTested, Table[] tables, List<String> fpcGeneratedPaths) {
        String originalQuery = sqlToBeTested; // Runner 中给出的 query 原话
        genetic.Instrumenter.startDatabase();

//        registerDrivers();

        // Check if query can be parsed
        try {
            // Make sql safe，将关键字全都大写，表名加上了引号，做了一下词法分析，等号两边加空格
            sqlToBeTested = new SqlSecurer(sqlToBeTested).getSecureSql();
        } catch (RuntimeException e) {
            log.error("Could not parse input query.");
            throw new EvoSQLException("无法解析输入sql语句，请检查");
        }

        log.info("SQL to be tested: " + sqlToBeTested);

        // 获取数据库结构，两行就s够，schema 就是获取到的结构
        SchemaConverter schemaConverter = new SchemaConverter(tables);
        Schema schema = schemaConverter.getSchema();

        // A path is a SQL query that only passes a certain condition set.
        // 生成覆盖目标，SQLCorgi 这个类是 gradle 引入依赖的
//        List<String> CorgiGeneratedPaths = new ArrayList<>(SQLCorgi.generateRules(originalQuery, schema));

        List<String> allPaths = fpcGeneratedPaths;
        log.info("Found " + allPaths.size() + " paths: ");
        allPaths.forEach(log::info);

        Map<String, TableSchema> tableSchemas;
        Seeds seeds;

        long start;
        long end = -1;

        int pathNo;
        int totalPaths = allPaths.size();
        int coveredPaths = 0;

        // 计算每一个目标的执行时间 = 2分钟 / 目标数量，这就是 timeBudget
        long eachPathTime = (long) (EvoSQLConfiguration.MS_EXECUTION_TIME / (double) totalPaths);

        Result result = new Result(originalQuery, System.currentTimeMillis());

        List<Fixture> population = new ArrayList<>();

        // Holds all paths not yet solved and not tried in the current cycle
        // 下面几行是给 unattempted path 初始化的，所有路径其实都被放进去了
        Queue<PathState> unattemptedPaths = new LinkedList<>();

        for (int iPathNo = 1; iPathNo <= allPaths.size(); iPathNo++) {
            String path = allPaths.get(iPathNo - 1);
            unattemptedPaths.add(new PathState(iPathNo, path, null, 0, null, eachPathTime));
        }

        // Holds all paths that have been attempted but are not yet solved
        List<PathState> attemptedPaths = new ArrayList<>();

        while (!unattemptedPaths.isEmpty()) { // 当还有路径没有尝试解过时：
            PathState pathState = unattemptedPaths.poll(); // 拿出一个路径

            // Check if there is time budget right now
            if (pathState.timeBudget <= 0) { // 初始时为 eachPathTime
                attemptedPaths.add(pathState);
                continue;
            }

            String pathSql = pathState.path;
            pathNo = pathState.pathNo;
            log.info("Testing " + pathSql);

            start = System.currentTimeMillis();

            // Secure sql
            pathSql = new SqlSecurer(pathSql).getSecureSql();

            try {
                if (pathState.approach == null) {
                    // Get all table schema's for current path
                    // tableSchemas = schemaExtractor.getTablesFromQuery(pathSql);
                    tableSchemas = schemaConverter.getTableSchemas();

                    // 这里就直接取种子了
                    seeds = new SeedExtractor(pathSql).extract();
// 这里改动比较大
//                    if (baseline) // 我们这里 baseline 传的都是 false，在 EvoSQLFactory 的 createEvoSQL 里
//                        pathState.approach = new RandomApproach(tableSchemas, pathSql, seeds);
//                    else
                    // 初始化一个 GA 实例，赋值给这个方法
                    // 种群，是个空的 ArrayList<Fixture>
                    // tableSchemas 是由 sql 语句中解析出的数据库格式
                    pathState.approach = new StandardGA(population, tableSchemas, pathSql, seeds);
                } else { // 已经=初始化过 approach 了
                    // Find table schemas from approach
                    tableSchemas = pathState.approach.getTableSchemas();

                    // Set the current population to where it left off
                    population = pathState.population;
                }


                // Reset table schema usedness
                tableSchemas.forEach((name, ts) -> ts.resetUsed());

                // Set used columns
                if (EvoSQLConfiguration.USE_USED_COLUMN_EXTRACTION) {
                    // Get the used columns in the current path
                    pathState.usedColumns = new UsedColumnExtractor(pathSql, tableSchemas).extract();
                    if (pathState.usedColumns != null) {
                        for (ColumnSchema col : pathState.usedColumns) {
                            col.setUsedColumn();
                            col.getTable().addUsedColumn();
                        }
                    }
                } else {
                    // Use all columns
                    for (TableSchema ts : tableSchemas.values()) {
                        for (ColumnSchema cs : ts.getColumns()) {
                            cs.setUsedColumn();
                            ts.addUsedColumn();
                        }
                    }
                }

                // Create schema on instrumenter
                for (TableSchema ts : tableSchemas.values()) {
                    genetic.Instrumenter.execute(ts.getDropSQL());
                    genetic.Instrumenter.execute(ts.getCreateSQL());
                }


                Fixture generatedFixture = pathState.approach.execute(pathState.timeBudget);

                // Store some vars
                end = System.currentTimeMillis();
                pathState.timePassed += (end - start);

                log.debug("Generated fixture for this path: {}", generatedFixture);

                // Done with path 生成一条插入语句
                if (pathState.approach.hasOutput(generatedFixture)) {
                    log.info("Generated fixture: " + generatedFixture.getInsertStatements());

                    // Add success
                    result.addPathSuccess(pathNo, pathSql, pathState.timePassed, generatedFixture
                            , pathState.approach.fetchOutput(generatedFixture, sqlToBeTested)
                            , pathState.approach.getGenerations(), pathState.approach.getIndividualCount()
                            , pathState.approach.getExceptions());

                    // Update coverage
                    coveredPaths++;
                    result.addCoveragePercentage(100 * ((double) coveredPaths) / totalPaths);
                } else {
                    log.info("Could not find a solution for this path.");

                    // Check if it didn't think it was a solution (because then there is no point to keep trying
                    if (generatedFixture.getFitness() != null && generatedFixture.getFitness().getDistance() != 0) {
                        // Add this path to the attemptedPaths
                        pathState.population = new ArrayList<Fixture>(population); // new list pointing to the last population
                        attemptedPaths.add(pathState);
                    }

                    String msg = "Has no output, distance is ";
                    if (generatedFixture.getFitness() != null)
                        msg += generatedFixture.getFitness().getDistance();
                    else
                        msg += "unknown!";
                    result.addPathFailure(pathNo, pathSql, pathState.timePassed, msg
                            , pathState.approach.getGenerations(), pathState.approach.getIndividualCount()
                            , pathState.approach.getExceptions());
                }
            } catch (SQLDataException e) {
                throw new EvoSQLException("data exception: invalid character value for cast");
            } catch (Exception e) {
                if (end < start) {
                    end = System.currentTimeMillis();
                    pathState.timePassed += (end - start);
                }
                e.printStackTrace();
                StackTraceElement[] st = e.getStackTrace();
                StringBuilder stackStr = new StringBuilder();
                for (StackTraceElement s : st)
                    stackStr.append(s.toString()).append('\t');
                result.addPathFailure(pathNo, pathSql, pathState.timePassed, e.getMessage() + "\t" + stackStr
                        , pathState.approach.getGenerations(), pathState.approach.getIndividualCount()
                        , pathState.approach.getExceptions());
                
            }

            // If it took shorter than given time budget, redistribute the time accordingly
            long timediff = (end - start);
            if (timediff < pathState.timeBudget) {
                int statesLeft = unattemptedPaths.size() + attemptedPaths.size();
                if (statesLeft > 0) {
                    long spareTime = pathState.timeBudget - timediff;
                    // Get time per path state
                    long timeInc = (long) (spareTime / (double) statesLeft);
                    // Increase budgets
                    unattemptedPaths.forEach(ps -> ps.timeBudget += timeInc);
                    attemptedPaths.forEach(ps -> ps.timeBudget += timeInc);
                }
            }
            pathState.timeBudget -= timediff;

            // If all paths are done, there are unsolved paths and we have time left, add the unsolved paths back in
            // 当还有剩余时间时，把第一次没解出来的再解一遍
            if (unattemptedPaths.size() == 0 && !attemptedPaths.isEmpty()) {
                // Check if any attempted paths have time left, if not stop
                boolean timeLeft = false;
                for (PathState ps : attemptedPaths) {
                    if (ps.timeBudget > 0) {
                        timeLeft = true;
                        break;
                    }
                }

                if (timeLeft)
                    unattemptedPaths.addAll(attemptedPaths);
                attemptedPaths.clear();
            }
        }

        genetic.Instrumenter.stopDatabase();

        return result;
    }

    /**
     * Registers JDBC drivers for the following database systems:
     * <ul>
     * 	<li>PostgreSQL</li>
     * 	<li>MySQL</li>
     * </ul>
     */
    private void registerDrivers() {
        try {
            Class.forName("org.postgresql.Driver");
            Class.forName("com.mysql.cj.jdbc.Driver");
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Class.forName("com.ibm.db2.jcc.DB2Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
