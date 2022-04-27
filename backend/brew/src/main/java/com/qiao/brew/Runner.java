package com.qiao.brew;

import com.qiao.brew.data.Path;
import com.qiao.brew.data.Result;
import com.qiao.brew.db.EvoSQLRunner;
import com.qiao.brew.sql.InsertionBuilder;
import com.qiao.brew.sql.vendor.MySQLOptions;
import com.qiao.brew.sql.vendor.VendorOptions;
import com.qiao.spring.pojo.Table;

import java.util.ArrayList;
import java.util.List;

public class Runner {


    public static List<String> run(String query, Table[] tables, List<String> coveragePaths) {
        EvoSQLRunner queryRunner = new EvoSQLRunner();
        Result queryRunnerResult = queryRunner.runQuery(query, tables, coveragePaths);

        List<String> result = new ArrayList<>();
        InsertionBuilder insertionBuilder = new InsertionBuilder(new MySQLOptions());
        // 取出每一个覆盖标准
        List<Path> paths = queryRunnerResult.getPaths();
        for (Path path : paths) {
            List<String> insertionStatements = insertionBuilder.buildQueries(path);
            for (String statement : insertionStatements) {
                System.out.println(statement); // 打印生成的插入语句
                result.add(statement);
            }
        }
        return result;

    }

}
