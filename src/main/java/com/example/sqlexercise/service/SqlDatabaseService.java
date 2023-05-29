package com.example.sqlexercise.service;

import com.example.sqlexercise.lib.ResultOfTask;
import com.example.sqlexercise.po.SubQuestion;

import javax.xml.transform.Result;
import java.util.ArrayList;
import java.util.Map;

public interface SqlDatabaseService {

    /**
     * 执行MySQL任务
     */
    Object runMysqlTask(int mainId, String driver, String sqlText, Map<String, Object> options);

    /**
     * 执行OceanBase任务
     */
    Map<String, String> runOceanbaseTask(String createIndexSql, String querySql, int targetTableNum);

    ResultOfTask getStandardAnswer(int subId, String driver);

//    ResultOfTask runTaskOfGettingSchemaInfo(int mainId, String driver);
//
//    ArrayList<ResultOfTask> runTaskOfUpdatingSchemaInfo(int mainId, String driver);
//
//    ArrayList<ResultOfTask> runTaskOfCleaningSchema(int mainId, String driver);

}
