package org.example;

import com.alibaba.druid.sql.ast.statement.SQLCreateTableStatement;
import com.alibaba.druid.sql.ast.statement.SQLSelectOrderByItem;
import com.alibaba.druid.sql.ast.statement.SQLTableElement;
import com.alibaba.druid.sql.dialect.mysql.ast.MySqlPrimaryKey;
import com.alibaba.druid.sql.repository.SchemaObject;
import com.alibaba.druid.sql.repository.SchemaRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    /**
     * 存储主键信息，以便后续order by, group by 的canonicalize
     */
    public HashMap<String, List<String>> primaryKeys;

    public Env(){}

    public Env(String dbType, List<String> sqls) {
        this.dbType = dbType;
        repository = new SchemaRepository(dbType);
//        repository.console("use sc00;");
        // 执行建表语句
        for (String sql: sqls) {
            repository.console(sql);
        }
        // 获取primary key
        primaryKeys = new HashMap<>();
        for (SchemaObject object: repository.getDefaultSchema().getObjects()) {
            SQLCreateTableStatement stmt = (SQLCreateTableStatement) object.getStatement();
            String tableName = stmt.getTableSource().toString();
            List<String> pks = new ArrayList<>();
            List<SQLTableElement> sqlTableElements = stmt.getTableElementList();
            for (SQLTableElement sqlTableElement : sqlTableElements) {
                if (sqlTableElement instanceof MySqlPrimaryKey) {
                    for (SQLSelectOrderByItem item: ((MySqlPrimaryKey) sqlTableElement).getColumns()) {
                        String name = item.getExpr().toString();
                        pks.add(name);
                    }
                }
            }
            if (pks.size() > 0) {
                primaryKeys.put(tableName, pks);
            }
        }
    }
}
