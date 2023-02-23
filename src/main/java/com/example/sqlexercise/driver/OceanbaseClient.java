package com.example.sqlexercise.driver;

import com.example.sqlexercise.lib.ResultOfTask;
import com.example.sqlexercise.lib.SqlDatabaseConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "com.example.sqlexercise.driver.OceanbaseClient")
public class OceanbaseClient implements Client{

    @Override
    public void init(SqlDatabaseConfig config){}

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
