package com.example.sqlexercise.service;

import com.example.sqlexercise.po.SubQuestion;

import java.util.Map;

public interface SqlDatabaseService {

    String getSchemaNameByMainId(int mainId);

    String getSchemaConstructorByMainId(int mainId);

    SubQuestion getSubQuestionBySubId(int subId);

    String runSqlTask(int mainId, String driver, String sqlText, Map options);

    String getStandardAnswer(int subId, String driver);

    Object runTaskOfGettingSchemaInfo(int mainId, String driver);

    Object runTaskOfUpdatingSchemaInfo(int mainId, String driver);

    Object runTaskOfCleaningSchema(int mainId, String driver);

}
