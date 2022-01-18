package org.example;

import com.alibaba.druid.sql.repository.SchemaRepository;

/**
 * @author shenyichen
 * @date 2022/1/18
 **/
public class Env {
    /**
     * 数据库类型
     */
    public String dbType;
    /**
     * 缓存SQL Schema信息，用于SQL语义解析中的ColumnResolve等操作
     */
    public SchemaRepository repository;

    public Env(){}

    public Env(String dbType, SchemaRepository repository) {
        this.dbType = dbType;
        this.repository = repository;
    }
}
