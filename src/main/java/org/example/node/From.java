package org.example.node;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOperator;
import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.alibaba.druid.sql.ast.statement.SQLJoinTableSource;
import com.alibaba.druid.sql.ast.statement.SQLSubqueryTableSource;
import com.alibaba.druid.sql.ast.statement.SQLTableSource;
import com.alibaba.druid.sql.repository.SchemaRepository;
import org.example.BuildAST;
import org.example.node.enums.JoinType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shenyichen
 * @date 2021/12/8
 **/
public class From implements Node {
    public List<Table> tables;
    public List<JoinPattern> joinPatterns;
    public List<Condition> joinConditions;
    public List<Select> subqueries;

    public From(){
        tables = new ArrayList<>();
        joinPatterns = new ArrayList<>();
        joinConditions = new ArrayList<>();
        subqueries = new ArrayList<>();
    }

    public void init(SQLTableSource tableSource, SQLExpr conds, SchemaRepository repository) {
        handleTableSource(tableSource,repository);
        handleConditions(conds);
    }

    public void handleTableSource(SQLTableSource tableSource, SchemaRepository repository){
        if (tableSource instanceof SQLSubqueryTableSource) {
            Select subquery = BuildAST.buildSelect(((SQLSubqueryTableSource) tableSource).getSelect().getQuery(),repository);
            subquery.name = tableSource.getAlias();
            subqueries.add(subquery);
        } else if (tableSource instanceof SQLJoinTableSource) {
            handleTableSource(((SQLJoinTableSource) tableSource).getLeft(),repository);
            handleTableSource(((SQLJoinTableSource) tableSource).getRight(),repository);
            handleJoinPattern(((SQLJoinTableSource) tableSource).getJoinType());
            handleCondsFromSQLExpr(((SQLJoinTableSource) tableSource).getCondition());
        } else if (tableSource instanceof SQLExprTableSource) {
            Table table = new Table(((SQLExprTableSource) tableSource).getExpr().toString());
            tables.add(table);
        }
    }

    public void handleJoinPattern(SQLJoinTableSource.JoinType joinType){
        String left = null;
        String right = null;
        if (joinType == SQLJoinTableSource.JoinType.COMMA || joinType == SQLJoinTableSource.JoinType.JOIN
        || joinType == SQLJoinTableSource.JoinType.NATURAL_JOIN || joinType == SQLJoinTableSource.JoinType.NATURAL_INNER_JOIN
        || joinType == SQLJoinTableSource.JoinType.INNER_JOIN) {
            joinPatterns.add(new JoinPattern(left,right, JoinType.INNER_JOIN));
        } else if (joinType == SQLJoinTableSource.JoinType.RIGHT_OUTER_JOIN) {
            joinPatterns.add(new JoinPattern(right,left,JoinType.LEFT_OUTER_JOIN));
        } else if (joinType == SQLJoinTableSource.JoinType.LEFT_OUTER_JOIN) {
            joinPatterns.add(new JoinPattern(left,right,JoinType.LEFT_OUTER_JOIN));
        } else if (joinType == SQLJoinTableSource.JoinType.FULL_OUTER_JOIN) {
            joinPatterns.add(new JoinPattern(left,right,JoinType.FULL_OUTER_JOIN));
        } else {
            joinPatterns.add(new JoinPattern(left,right,JoinType.OTHER_JOIN));
        }
    }

    public void handleConditions(SQLExpr conds){
        if (tables.size()==1){
            return;
        }
        // TODO: comma情况下把 where里的考虑进来
    }

    public void handleCondsFromSQLExpr(SQLExpr conds){
        if (conds==null){
            return;
        }
        if (conds instanceof SQLBinaryOpExpr){
            SQLBinaryOpExpr conditions = (SQLBinaryOpExpr) conds;
            SQLBinaryOperator operator = conditions.getOperator();
            SQLExpr left = conditions.getLeft();
            SQLExpr right = conditions.getRight();
            if (operator == SQLBinaryOperator.BooleanAnd) {
                handleCondsFromSQLExpr(left);
                handleCondsFromSQLExpr(right);
            } else {// 表达式
                if (CommutativeCond.isCommutativeCond(operator.name)){
                    addCommutativeCond(operator.name,left.toString(),right.toString());
                } else {
                    UncommutativeCond cond = new UncommutativeCond(operator.name,left.toString(),right.toString());
                    joinConditions.add(cond);
                }
            }
        }
    }

    private void addCommutativeCond(String operator, String left, String right) {
        for(Condition con: joinConditions) {
            if (con instanceof CommutativeCond) {
                CommutativeCond c = (CommutativeCond) con;
                if (c.operator.equals(operator)){
                    if (c.operands.contains(left)) {
                        c.operands.add(right);
                        return;
                    } else if (c.operands.contains(right)) {
                        c.operands.add(left);
                        return;
                    }
                }
            }
        }
        CommutativeCond c = new CommutativeCond(operator);
        c.operands.add(left);
        c.operands.add(right);
        joinConditions.add(c);
    }

    @Override
    protected From clone() throws CloneNotSupportedException {
        From from = new From();
        for (Table t: tables){
            from.tables.add(t.clone());
        }
        for (JoinPattern p: joinPatterns){
            from.joinPatterns.add(p.clone());
        }
        for (Condition c: joinConditions){
            from.joinConditions.add(c.clone());
        }
        for (Select s: subqueries){
            from.subqueries.add((Select) s.clone());
        }
        return from;
    }
}
