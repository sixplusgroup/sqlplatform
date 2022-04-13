package com.example.sqlexercise.lib;

import java.util.Map;

public class SqlDatabase {

    private String id;
    private String name;
    private String driver;
    private SqlDatabaseConfig config;
    private SqlDatabase root;
    public int consecutiveErrors;
    public int lockedQueries;

    public SqlDatabase(SqlDatabaseConfig config, SqlDatabase root){
        this.root = root;
        this.config = config;
        this.consecutiveErrors = 0;
        this.lockedQueries = 0;
    }

    public SqlDatabase(SqlDatabaseConfig config){
        this.config = config;
        this.consecutiveErrors = 0;
        this.lockedQueries = 0;
    }

    private int lock(){
        int errors = this.consecutiveErrors;
        this.lockedQueries = errors * errors;
        return this.lockedQueries;
    }

    public int unlock(){
        if(this.lockedQueries>0){
            this.lockedQueries--;
        }
        return this.lockedQueries;
    }


}
