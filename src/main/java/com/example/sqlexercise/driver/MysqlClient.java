package com.example.sqlexercise.driver;

import com.example.sqlexercise.lib.ResultOfTask;
import com.example.sqlexercise.lib.SqlDatabaseConfig;
import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.mysql.cj.jdbc.MysqlPooledConnection;
import lombok.extern.slf4j.Slf4j;

import javax.sql.PooledConnection;
import java.sql.*;
import java.util.ArrayList;

@Slf4j(topic = "com.example.sqlexercise.driver.MysqlClient")
public class MysqlClient implements Client {

    private MysqlConnectionPoolDataSource poolDataSource;

    @Override
    public void init(SqlDatabaseConfig config) {
        this.poolDataSource = new MysqlConnectionPoolDataSource();
        this.poolDataSource.setServerName(config.host);
        this.poolDataSource.setDatabaseName(config.tags.get("schemaName").toString());
        this.poolDataSource.setPort(config.port);
        this.poolDataSource.setUser(config.username);
        this.poolDataSource.setPassword(config.password);
        try {
            this.poolDataSource.setUseSSL(true);
            this.poolDataSource.setConnectTimeout(5 * 60);
            this.poolDataSource.setAllowMultiQueries(true);
            this.poolDataSource.setInteractiveClient(true);
            this.poolDataSource.setServerTimezone("UTC");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        this.poolDataSource = null;
    }

    @Override
    public ResultOfTask runQuery(String query) {
        ResultOfTask resultOfTask = new ResultOfTask();
        try {
            PooledConnection connections = poolDataSource.getPooledConnection();
            Connection connection = connections.getConnection();
            Statement statement = connection.createStatement();
            //限制查询时间在2s以内
            statement.setQueryTimeout(2);
            ResultSet rs = statement.executeQuery(query);
            int columnCount = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                ArrayList<String> row = new ArrayList<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(rs.getString(i));
                }
                resultOfTask.sheet.add(row);
            }
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLTimeoutException e) {
            e.printStackTrace();
            resultOfTask.error = "SQLTimeoutException!";
        } catch (SQLSyntaxErrorException e) {
            e.printStackTrace();
            resultOfTask.error = "SQLSyntaxErrorException!";
        } catch (SQLException e) {
            e.printStackTrace();
            resultOfTask.error = "SQLException!";
        }
        return resultOfTask;
    }

    @Override
    public boolean operateIndex(String sqlText) {
        return false;
    }

    @Override
    public String getSchemaSql(String database) {
        String whereSql;
        if (database != null) {
            whereSql = "WHERE t.table_schema = '" + database + "'";
        } else {
            whereSql = "WHERE t.table_schema NOT IN (\n'mysql',\n'performance_schema',\n'information_schema'\n)";
        }
        return "SELECT\nt.table_schema,\nt.table_name,\nc.column_name,\nc.data_type\nFROM\nINFORMATION_SCHEMA.TABLES t\n" +
                "JOIN INFORMATION_SCHEMA.COLUMNS c ON t.table_schema = c.table_schema AND t.table_name = c.table_name\n" +
                whereSql + "\nORDER BY\nt.table_schema,\nt.table_name,\nc.ordinal_position";
    }

    @Override
    public String initSchemaSql(String database) {
        return "CREATE DATABASE IF NOT EXISTS " + database + ";\n" +
                "Use " + database + ";\n";
    }

    @Override
    public String cleanSchemaSql(String database) {
        return "DROP DATABASE IF EXISTS " + database;
    }

    @Override
    public void createDatabase(String sqlText) {
    }

    @Override
    public void createTable(String sqlText) {
        Connection connection = null;
        Statement statement = null;
        try {
            PooledConnection connections = poolDataSource.getPooledConnection();
            connection = connections.getConnection();
            statement = connection.createStatement();
            statement.execute(sqlText);
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean createUser(String sqlText) {
        try {
            PooledConnection connections = poolDataSource.getPooledConnection();
            Connection connection = connections.getConnection();
            Statement statement = connection.createStatement();
            //执行多条语句并返回第一条语句的结果;
            //true表示返回结果集为resultSet
            //false表示返回结果是更新计数或没有结果
            boolean rs = statement.execute(sqlText);
            while (true) {
                if (rs) {
                    log.info("An error occurred when create user");
                    return false;
                } else {
                    //rs为false，且updateCount=-1时,所有结果已取出
                    if (statement.getUpdateCount() == -1) {
                        break;
                    }
                    rs = statement.getMoreResults();
                }
            }
            statement.close();
            connection.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
