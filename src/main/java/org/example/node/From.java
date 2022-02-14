package org.example.node;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.statement.*;
import org.example.BuildAST;
import org.example.Env;
import org.example.edit.CostConfig;
import org.example.enums.JoinType;
import org.example.node.condition.CompoundCond;
import org.example.node.condition.Condition;
import org.example.node.expr.Expr;
import org.example.node.select.PlainSelect;
import org.example.node.select.Select;
import org.example.node.table.JoinTable;
import org.example.node.table.PlainTable;
import org.example.node.table.Table;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author shenyichen
 * @date 2021/12/8
 **/
public class From {
    // todo left/right + null判断 => inner join
    public List<Table> tables;
    public HashMap<String, Integer> joinTypes;
    // 下面这些仅用于传给Select
    public Condition joinCondition;
    public HashMap<String, Table> tableAliasMap;
    public HashMap<String, Expr> attrAliasMap;


    public From() {
        tables = new ArrayList<>();
        joinTypes = new HashMap<>();
        joinCondition = null;
        tableAliasMap = new HashMap<>();
        attrAliasMap = new HashMap<>();
    }

    public From(List<Table> tables, HashMap<String, Integer> joinTypes, Condition joinCondition) {
        this();
        this.tables = tables;
        this.joinTypes = joinTypes;
        this.joinCondition = joinCondition;
    }

    public From(SQLTableSource tableSource, Env env, PlainSelect outerSelect){
        this();
        handleTableSource(tableSource, env, outerSelect);
    }

    public Table handleTableSource(SQLTableSource tableSource, Env env, PlainSelect outerSelect) {
        Table t;
        if (tableSource instanceof SQLJoinTableSource) {
            Table left = handleTableSource(((SQLJoinTableSource) tableSource).getLeft(), env, outerSelect);
            Table right = handleTableSource(((SQLJoinTableSource) tableSource).getRight(), env, outerSelect);
            JoinType type = handleJoinType(((SQLJoinTableSource) tableSource).getJoinType());
            handleCondsFromSQLExpr(((SQLJoinTableSource) tableSource).getCondition(),env);
            t = new JoinTable(left,right,type);
        }
        else if (tableSource instanceof SQLUnionQueryTableSource) {
            SQLUnionQuery query = ((SQLUnionQueryTableSource) tableSource).getUnion();
            Select subquery = BuildAST.buildSelect(query,env);
            if (subquery == null){
                return null;
            }
            subquery.setOuterSelect(outerSelect);
            tables.add(subquery);
            tableAliasMap.putAll(subquery.tableAliasMap);
            attrAliasMap.putAll(subquery.attrAliasMap);
            t = subquery;
        }
        else if (tableSource instanceof SQLSubqueryTableSource) {
            Select subquery = BuildAST.buildSelect(
                    ((SQLSubqueryTableSource) tableSource).getSelect().getQuery(),env);
            if (subquery == null){
                return null;
            }
            subquery.setOuterSelect(outerSelect);
            tables.add(subquery);
            tableAliasMap.putAll(subquery.tableAliasMap);
            attrAliasMap.putAll(subquery.attrAliasMap);
            t = subquery;
        }
        else if (tableSource instanceof SQLExprTableSource) {
            String tableName = ((SQLExprTableSource) tableSource).getExpr().toString();
            t = new PlainTable(tableName);
            tables.add(t);
        }
        else {
            t = new PlainTable(tableSource.getAlias());
            tables.add(t);
        }
        String alias = tableSource.getAlias();
        if (alias != null){
            tableAliasMap.put(alias, t);
        }
        return t;
    }

    public JoinType handleJoinType(SQLJoinTableSource.JoinType joinType) {
        String type = "";
        if (joinType == SQLJoinTableSource.JoinType.COMMA || joinType == SQLJoinTableSource.JoinType.JOIN
        || joinType == SQLJoinTableSource.JoinType.NATURAL_JOIN || joinType == SQLJoinTableSource.JoinType.NATURAL_INNER_JOIN
        || joinType == SQLJoinTableSource.JoinType.INNER_JOIN) {
            type = "INNER_JOIN";
        } else if (joinType == SQLJoinTableSource.JoinType.RIGHT_OUTER_JOIN) {
            type = "LEFT_OUTER_JOIN";
        } else if (joinType == SQLJoinTableSource.JoinType.LEFT_OUTER_JOIN) {
            type = "LEFT_OUTER_JOIN";
        } else if (joinType == SQLJoinTableSource.JoinType.FULL_OUTER_JOIN) {
            type = "FULL_OUTER_JOIN";
        } else {
            type = "OTHER_JOIN";
        }
        if (this.joinTypes.containsKey(type)) {
            this.joinTypes.put(type, this.joinTypes.get(type) + 1);
        } else {
            this.joinTypes.put(type,1);
        }
        return JoinType.valueOf(type);
    }

    public void handleCondsFromSQLExpr(SQLExpr conds, Env env){
        if (conds == null){
            return;
        }
        Condition c = Condition.build(conds,env);
        if (joinCondition != null){
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
        for (Integer item: joinTypes.values()) {
            score += item;
        }
        return score;
    }

    public float score(From f) {
        return scoreOfTables(f.tables) + scoreOfJoinTypes(f.joinTypes);
    }

    private float scoreOfTables(List<Table> t) {
        if (tables == null || tables.size() == 0) {
            if (t == null || t.size() == 0)
                return 0;
            else
                return (float) (-1 * t.stream()
                        .mapToDouble(Table::score)
                        .sum()
                        * CostConfig.delete_cost_rate);
        }
        if (t == null || t.size() == 0)
            return 0;
        float score = 0;
        List<Table> t_clone = new ArrayList<>(t);
        for (Table item: tables) {
            Table match = Table.isIn(item,t_clone);
            if (match != null) {
                score += item.score(match);
                t_clone.remove(item);
            }
        }
        for (Table item: t_clone) {
            score -= item.score() * CostConfig.delete_cost_rate;
        }
        return score;
    }

    private float scoreOfJoinTypes(HashMap<String, Integer> types) {
        if (joinTypes == null || joinTypes.size() == 0) {
            if (types == null || types.size() == 0)
                return 0;
            else {
                float score = 0;
                for (Integer i: types.values()) {
                    score -= i * CostConfig.delete_cost_rate;
                }
                return score;
            }
        }
        if (types == null || types.size() == 0)
            return 0;
        float score = 0;
        for (Map.Entry<String, Integer> entry: joinTypes.entrySet()) {
            String key = entry.getKey();
            if (types.containsKey(key) && types.get(key).equals(entry.getValue()))
                score += Math.min(entry.getValue(),types.get(key));
        }
        for (Map.Entry<String, Integer> entry: types.entrySet()) {
            String key = entry.getKey();
            if (!(joinTypes.containsKey(key)))
                score -= entry.getValue() * CostConfig.delete_cost_rate;
        }
        return score;
    }

    @Override
    public From clone() {
        List<Table> tables_clone = tables.stream()
                .map(Table::clone)
                .collect(Collectors.toList());
        HashMap<String, Integer> types_clone = new HashMap<>(joinTypes);
        return new From(tables_clone,types_clone,null);
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
        for (Map.Entry<String, Integer> entry: joinTypes.entrySet()) {
            String key = entry.getKey();
            if ((!(from.joinTypes.containsKey(key)))
                    || (!from.joinTypes.get(key).equals(entry.getValue())))
                return false;
        }
        return true;
    }

}
