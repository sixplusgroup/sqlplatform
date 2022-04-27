package com.qiao.ga.db;

import com.qiao.ga.sql.TableSchema;

import java.util.Map;

/**
 * Created by mauricioaniche on 31/07/2017.
 */
public interface ISchemaExtractor {
    TableSchema extract(String table);

    Map<String, TableSchema> getTablesFromQuery(String pathToBeTested);
}
