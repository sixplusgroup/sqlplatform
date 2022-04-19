package com.example.sqlexercise.driver;

import com.example.sqlexercise.lib.ResultOfTask;
import com.example.sqlexercise.lib.SqlDatabaseConfig;
import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.mysql.cj.jdbc.MysqlPooledConnection;

import javax.sql.PooledConnection;
import java.sql.*;
import java.util.ArrayList;

public class MysqlClient implements Client {

    private MysqlConnectionPoolDataSource poolDataSource;

    @Override
    public void init(SqlDatabaseConfig config){
        this.poolDataSource = new MysqlConnectionPoolDataSource();
        this.poolDataSource.setServerName(config.host);
        this.poolDataSource.setDatabaseName(config.tags.get("schemaName").toString());
        this.poolDataSource.setPort(config.port);
        this.poolDataSource.setUser(config.username);
        this.poolDataSource.setPassword(config.password);


    }

    @Override
    public void destroy() {

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
            while(rs.next()) {
                ArrayList<String> row = new ArrayList<>();
                for(int i=1;i<=columnCount;i++){
                    row.add(rs.getString(i));
                }
                resultOfTask.sheet.add(row);
            }
            rs.close();
            statement.close();
        }catch(SQLTimeoutException e){
            e.printStackTrace();
            resultOfTask.error = "SQLTimeoutException!";
        }catch (SQLSyntaxErrorException e){
            e.printStackTrace();
            resultOfTask.error = "SQLSyntaxErrorException!";
        }catch (SQLException e){
            e.printStackTrace();
            resultOfTask.error = "SQLException!";
        }
        return resultOfTask;
    }

    @Override
    public String getSchemaSql(String database){
        String whereSql;
        if(database!=null){
            whereSql = "WHERE t.table_schema = "+database;
        }else{
            whereSql = "WHERE t.table_schema NOT IN (\n\'mysql\',\n\'performance_schema\',\n\'information_schema\'\n)";
        }
        return "SELECT\nt.table_schema,\nt.table_name,\nc.column_name,\nc.data_type\nFROM\nINFORMATION_SCHEMA.TABLES t\n" +
                "JOIN INFORMATION_SCHEMA.COLUMNS c ON t.table_schema = c.table_schema AND t.table_name = c.table_name\n"+
                whereSql + "\nORDER BY\nt.table_schema,\nt.table_name,\nc.ordinal_position";
    }

    @Override
    public String initSchemaSql(String database){
        return "CREATE DATABASE IF NOT EXISTS "+database+";\nUse"+database+";\n";
    }

    @Override
    public String cleanSchemaSql(String database){
        return "DROP DATABASE IF EXISTS "+database;
    }

}
