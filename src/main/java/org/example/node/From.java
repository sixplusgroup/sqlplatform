package org.example.node;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.statement.*;
import org.example.BuildAST;
import org.example.Env;
import org.example.edit.CostConfig;
import org.example.node.condition.CompoundCond;
import org.example.node.condition.Condition;
import org.example.enums.JoinType;
import org.example.node.expr.Expr;
import org.example.node.select.Select;
import org.example.node.table.JoinTable;
import org.example.node.table.PlainTable;
import org.example.node.table.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shenyichen
 * @date 2021/12/8
 **/
public class From {
    // todo left/right + null判断 => inner join
    public List<Table> tables;
    public List<JoinTable> joinTables;
    // 下面这些仅用于传给Select
    public Condition joinCondition;
    public List<Select> subqueries;
    public HashMap<String, Table> tableAliasMap;
    public HashMap<String, Expr> attrAliasMap;


    public From() {
        tables = new ArrayList<>();
        joinTables = new ArrayList<>();
        joinCondition = null;
        subqueries = new ArrayList<>();
        tableAliasMap = new HashMap<>();
        attrAliasMap = new HashMap<>();
    }

    public From(List<Table> tables, List<JoinTable> joinTables, Condition joinCondition) {
        this();
        this.tables = tables;
        this.joinTables = joinTables;
        this.joinCondition = joinCondition;
    }

    public From(SQLTableSource tableSource, Env env){
        this();
        handleTableSource(tableSource,env);
    }

    public Table handleTableSource(SQLTableSource tableSource, Env env){
        if (tableSource instanceof SQLJoinTableSource) {
            Table left = handleTableSource(((SQLJoinTableSource) tableSource).getLeft(), env);
            Table right = handleTableSource(((SQLJoinTableSource) tableSource).getRight(), env);
            JoinTable table = handleJoinPattern(((SQLJoinTableSource) tableSource).getJoinType(),
                    left, right);
            handleCondsFromSQLExpr(((SQLJoinTableSource) tableSource).getCondition(),env);
            return table;
        }
        else if (tableSource instanceof SQLUnionQueryTableSource) {
            SQLUnionQuery query = ((SQLUnionQueryTableSource) tableSource).getUnion();
            return handleSQLSelectQuery(query);
        }
        else if (tableSource instanceof SQLSubqueryTableSource) {
            Select subquery = BuildAST.buildSelect(
                    ((SQLSubqueryTableSource) tableSource).getSelect().getQuery(),env);
            if (subquery==null){
                return null;
            }
            subqueries.add(subquery);
            tables.add(subquery);
            tableAliasMap.putAll(subquery.tableAliasMap);
            attrAliasMap.putAll(subquery.attrAliasMap);
            String alias = tableSource.getAlias();
            if (alias != null){
                tableAliasMap.put(alias,subquery);
            }
            return subquery;
        }
        else if (tableSource instanceof SQLExprTableSource) {
            String tableName = ((SQLExprTableSource) tableSource).getExpr().toString();
            String alias = tableSource.getAlias();
            Table table = new PlainTable(tableName);
            tables.add(table);
            if (alias != null){
                tableAliasMap.put(alias,table);
            }
            return table;
        }
        else {
            Table table = new PlainTable(tableSource.getAlias());
            tables.add(table);
            return table;
        }
    }

    public Table handleSQLSelectQuery(SQLSelectQuery query) {
        return null;
    }

    public JoinTable handleJoinPattern(SQLJoinTableSource.JoinType joinType,
                                  Table left, Table right){
        if (joinType == SQLJoinTableSource.JoinType.COMMA || joinType == SQLJoinTableSource.JoinType.JOIN
        || joinType == SQLJoinTableSource.JoinType.NATURAL_JOIN || joinType == SQLJoinTableSource.JoinType.NATURAL_INNER_JOIN
        || joinType == SQLJoinTableSource.JoinType.INNER_JOIN) {
            return new JoinTable(left,right,JoinType.INNER_JOIN);
        } else if (joinType == SQLJoinTableSource.JoinType.RIGHT_OUTER_JOIN) {
            return new JoinTable(right,left,JoinType.LEFT_OUTER_JOIN);
        } else if (joinType == SQLJoinTableSource.JoinType.LEFT_OUTER_JOIN) {
            return new JoinTable(left,right,JoinType.LEFT_OUTER_JOIN);
        } else if (joinType == SQLJoinTableSource.JoinType.FULL_OUTER_JOIN) {
            return new JoinTable(left,right,JoinType.FULL_OUTER_JOIN);
        } else {
            return new JoinTable(left,right,JoinType.OTHER_JOIN);
        }
    }

    public void handleCondsFromSQLExpr(SQLExpr conds, Env env){
        if (conds == null){
            return;
        }
        Condition c = Condition.build(conds,env);
        if (joinCondition!=null){
            joinCondition = new CompoundCond("AND", Arrays.asList(joinCondition,c));
        }else {
            joinCondition = c;
        }
    }

    public float score() {
        float score = 0.0f;
        score += tables.stream()
                .mapToDouble(Table::score)
                .sum();
        score += joinTables.stream()
                .mapToDouble(Table::score)
                .sum();
        return score;
    }

    public float score(From f) {
        return scoreOfTables(f.tables)
                + scoreOfJoinTables(f.joinTables)
                + scoreOfSubQs(f.subqueries);
    }

    private float scoreOfTables(List<Table> t) {
        if (t == null || t.size() == 0)
            return 0;
        float score = 0;
        List<Table> t_clone = new ArrayList<>(t);
        for (Table item: tables) {
            if (Table.isStrictlyIn(item, t)) {
                score += CostConfig.table;
                t_clone.remove(item);
            }
        }
        score -= t_clone.size();
        return score;
    }

    private float scoreOfJoinTables(List<JoinTable> joinTables) {
        return 0;
    }

    private float scoreOfSubQs(List<Select> student) {
        return 0;
    }

    @Override
    public From clone() {
        List<Table> tables_clone = tables.stream()
                .map(Table::clone)
                .collect(Collectors.toList());
        List<JoinTable> joinTables_clone = joinTables.stream()
                .map(JoinTable::clone)
                .collect(Collectors.toList());
        return new From(tables_clone,joinTables_clone,null);
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof From))
            return false;
        From from = (From) obj;
        for (Table tmp: tables) {
            if (!Table.isStrictlyIn(tmp,from.tables))
                return false;
        }
        for (JoinTable tmp: joinTables) {
            if (!Table.isStrictlyIn(from.joinTables,tmp))
                return false;
        }
        return true;
    }

}
