package com.qiao.brew.db;

import com.qiao.brew.data.Result;

import com.qiao.spring.pojo.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * A runner that returns the same data set, regardless of input.
 */
@Data
@AllArgsConstructor
public class ExistingDataRunner implements QueryRunner {

    /**
     * The instance of Result that will always be returned,
     * regardless of input to the runQuery function.
     */
    private Result result;

    @Override
    public Result runQuery(String sqlQuery, Table[] tables, List<String> coveragePaths) {
        return result;
    }
}
