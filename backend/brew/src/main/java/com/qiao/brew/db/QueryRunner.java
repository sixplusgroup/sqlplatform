package com.qiao.brew.db;


import com.qiao.brew.data.Result;
import com.qiao.spring.pojo.Table;

import java.util.List;

/**
 * An interface for obtaining test fixtures from a database.
 */
public interface QueryRunner {
    /**
     * Returns test data fixtures for the given SQL query.
     * @param sqlQuery The SQL query to generate fixtures for.
     * @param tables schema
     * @return A list of fixtures for the given SQL query.
     */
    Result runQuery(String sqlQuery, Table[] tables, List<String> coveragePaths);
}
