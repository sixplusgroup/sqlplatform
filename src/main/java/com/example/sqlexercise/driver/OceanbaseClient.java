package com.example.sqlexercise.driver;

import com.example.sqlexercise.lib.ResultOfTask;
import com.example.sqlexercise.lib.SqlDatabaseConfig;
import com.oceanbase.jdbc.OceanBasePoolDataSource;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;

@Slf4j(topic = "com.example.sqlexercise.driver.OceanbaseClient")
public class OceanbaseClient implements Client{

    private OceanBasePoolDataSource poolDataSource;
    @Override
    public void init(SqlDatabaseConfig config){
        this.poolDataSource = new OceanBasePoolDataSource();
        try {
            this.poolDataSource.setServerName(config.host);
            this.poolDataSource.setDatabaseName(config.tags.get("schemaName").toString());
            this.poolDataSource.setPort(config.port);
            this.poolDataSource.setUser(config.username);
            this.poolDataSource.setPassword(config.password);
            this.poolDataSource.setLoginTimeout(5 * 60);
            this.poolDataSource.setMaxIdleTime(5 * 60);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void destroy(){}

    @Override
    public ResultOfTask runQuery(String query){
        return null;
    }

    @Override
    public String getSchemaSql(String database){
        return null;
    }

    @Override
    public String initSchemaSql(String database){
        return null;
    }

    @Override
    public String cleanSchemaSql(String database){
        return null;
    }

    @Override
    public boolean createUser(String sqlText){
        return false;
    }

    @Override
    public void createTable(String sqlText){}
}
