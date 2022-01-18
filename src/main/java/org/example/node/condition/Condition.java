package org.example.node.condition;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.expr.*;
import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.alibaba.druid.sql.ast.statement.SQLSelectQuery;
import org.example.BuildAST;
import org.example.Canonicalizer;
import org.example.Env;
import org.example.node.PlainSelect;
import org.example.node.Select;
import org.example.node.SetOpSelect;
import org.example.node.enums.SetOp;
import org.example.node.expr.Expr;

/**
 * @author shenyichen
 * @date 2021/12/8
 **/
public abstract class Condition implements Expr {
    public String operator;
    public boolean not;
    /* 对应字符串 */
    public String value;
    /* 分数 */
    public float score;

    /**
     * SQLExpr转Condition
     * @param expr
     * @return
     */
    public static Condition build(SQLExpr expr, Env env){
        if (expr == null)
            return null;
        if (expr instanceof SQLBinaryOpExpr){
            SQLBinaryOpExpr biExpr = (SQLBinaryOpExpr) expr;
            SQLBinaryOperator operator = biExpr.getOperator();
            SQLExpr left = biExpr.getLeft();
            SQLExpr right = biExpr.getRight();
            // 复合条件
            if (operator == SQLBinaryOperator.BooleanAnd || operator == SQLBinaryOperator.BooleanOr || operator == SQLBinaryOperator.BooleanXor) {
                return new CompoundCond(operator.name,build(left,env),build(right,env));
            } else {// 单个条件
                String op = getNormalizedOp(operator.name);
                // some/any转exist
                if (left instanceof SQLSomeExpr || left instanceof SQLAnyExpr
                        || right instanceof SQLSomeExpr || right instanceof SQLAnyExpr){
                    if (left instanceof SQLSomeExpr || left instanceof SQLAnyExpr){
                        SQLExpr tmp = left;
                        left = right;
                        right = tmp;
                        op = getOppositeOp(op);
                    }
                    SQLSelectQuery selectQuery = null;
                    if (right instanceof SQLSomeExpr)
                        selectQuery = ((SQLSomeExpr) right).subQuery.getQuery();
                    if (right instanceof SQLAnyExpr)
                        selectQuery = ((SQLAnyExpr) right).subQuery.getQuery();
                    Expr expr_some = Expr.build(left);
                    Select subQ = BuildAST.buildSelect(selectQuery,env);
                    some2Exist(expr_some,subQ,op);
                    return new Exist(false,subQ);
                }
                // all转not exist
                else if (left instanceof SQLAllExpr || right instanceof SQLAllExpr){
                    if (left instanceof SQLAllExpr){
                        SQLExpr tmp = left;
                        left = right;
                        right = tmp;
                        op = getOppositeOp(op);
                    }
                    Expr expr_all = Expr.build(left);
                    SQLSelectQuery selectQuery = ((SQLAllExpr)right).subQuery.getQuery();
                    Select subQ = BuildAST.buildSelect(selectQuery,env);
                    all2Exist(expr_all,subQ,op);
                    return new Exist(true,subQ);
                }
                // 普通运算符的情况
                else if (isCommutative(op)){
                    return new CommutativeCond(op,Expr.build(left),Expr.build(right));
                } else {
                    return new UncommutativeCond(op,Expr.build(left),Expr.build(right));
                }
            }
        } else if (expr instanceof SQLNotExpr) { // not
            Condition c = build(((SQLNotExpr) expr).expr,env);
            setNot(c);
            return c;
        } else if (expr instanceof SQLExistsExpr) { // exist
            SQLExistsExpr existsExpr = (SQLExistsExpr) expr;
            Select subQ = BuildAST.buildSelect(existsExpr.subQuery.getQuery(),env);
            return new Exist(existsExpr.not, Canonicalizer.existNormalize(subQ));
        } else if (expr instanceof SQLInSubQueryExpr) { // in转exist
            SQLInSubQueryExpr inExpr = (SQLInSubQueryExpr) expr;
            Expr expr_in = Expr.build(inExpr.getExpr());
            Select subQ = BuildAST.buildSelect(inExpr.subQuery.getQuery(),env);
            in2Exist(expr_in, subQ);
            return new Exist(inExpr.isNot(),Canonicalizer.existNormalize(subQ));
        } else if (expr instanceof SQLBetweenExpr) {
            // between转CompoundCond
            SQLBetweenExpr betweenExpr = (SQLBetweenExpr) expr;
            Expr testExpr = Expr.build(betweenExpr.testExpr);
            Expr beginExpr = Expr.build(betweenExpr.beginExpr);
            Expr endExpr = Expr.build(betweenExpr.endExpr);
            if (betweenExpr.isNot()){
                Condition beginC = new UncommutativeCond("<",testExpr,beginExpr);
                Condition endC = new UncommutativeCond(">",testExpr,endExpr);
                return new CompoundCond("OR",beginC,endC);
            }else {
                Condition beginC = new UncommutativeCond(">=",testExpr,beginExpr);
                Condition endC = new UncommutativeCond("<=",testExpr,endExpr);
                return new CompoundCond("AND",beginC,endC);
            }
        } else {

        }
        return null;
    }

    /**
     * 展平：e.g. A and B and C 按 and 展平
     * 提not? e.g. not A and not B and not C 也可以考虑下放not，e.g. not between的情况
     * 运算符规则化：>转<
     */
    public abstract void flatten();

    public static void in2Exist(Expr expr, Select subQ){
        if (subQ instanceof SetOpSelect){
            in2Exist(expr,((SetOpSelect) subQ).left);
            in2Exist(expr,((SetOpSelect) subQ).right);
        } else if (subQ instanceof PlainSelect) {
            // todo: (a,b) in (select x,y ...)
            Condition c = new CommutativeCond("=",expr,((PlainSelect) subQ).selections.get(0));
            ((PlainSelect) subQ).where = new CompoundCond("AND",((PlainSelect) subQ).where,c);
        }
    }

    public static void some2Exist(Expr expr, Select subQ, String operator){
        if (subQ instanceof SetOpSelect){
            some2Exist(expr,((SetOpSelect) subQ).left,operator);
            some2Exist(expr,((SetOpSelect) subQ).right,operator);
        } else if (subQ instanceof PlainSelect) {
            // todo: (a,b) in (select x,y ...)
            Condition c = null;
            if (isCommutative(operator)){
                c = new CommutativeCond(operator,expr,((PlainSelect) subQ).selections.get(0));
            }else {
                c = new UncommutativeCond(operator,expr,((PlainSelect) subQ).selections.get(0));
            }
            ((PlainSelect) subQ).where = new CompoundCond("AND",((PlainSelect) subQ).where,c);
        }
    }

    public static void all2Exist(Expr expr, Select subQ, String operator){
        if (subQ instanceof SetOpSelect){
            all2Exist(expr,((SetOpSelect) subQ).left,operator);
            all2Exist(expr,((SetOpSelect) subQ).right,operator);
        } else if (subQ instanceof PlainSelect) {
            // todo: (a,b) in (select x,y ...)
            Condition c = null;
            if (isCommutative(operator)){
                c = new CommutativeCond(operator,expr,((PlainSelect) subQ).selections.get(0));
            }else {
                c = new UncommutativeCond(operator,expr,((PlainSelect) subQ).selections.get(0));
            }
            ((PlainSelect) subQ).where = new CompoundCond("AND",((PlainSelect) subQ).where,c);
        }
    }

    /**
     * 是否可交换
     * @param operator
     * @return
     */
    public static boolean isCommutative(String operator) {
        if (operator.equals("UNION") || operator.equals("^") || operator.equals("^=")
                || operator.equals("*") || operator.equals("+") || operator.equals("&")
                || operator.equals("|") || operator.equals("<=>") || operator.equals("<>")
                || operator.equals("SIMILAR TO") || operator.equals("=") || operator.equals("||")
                || operator.equals("AND") || operator.equals("OR")) {
            return true;
        }
        return false;
    }

    /**
     * 用于交换left, right时
     * @param operator
     * @return
     */
    public static String getOppositeOp(String operator){
        if (isCommutative(operator))
            return operator;
        switch (operator){
            case ">":
                return "<";
            case ">=":
                return "<=";
            case "<":
                return ">";
            case "<=":
                return ">=";
            default:
                // 其他情况应该不用考虑
                return operator;
        }
    }

    /**
     * 运算符规范化：同义运算符替换
     * @param operator
     * @return
     */
    public static String getNormalizedOp(String operator) {
        switch (operator){
            case "!>":
                return "<=";
            case "!<":
                return ">=";
            case "<>":
                return "!=";
            default:
                return operator;
        }
    }

    /**
     * 设置not：not后接比较符时，改operator；其他情况，设置not为true
     * @param c
     */
    public static void setNot(Condition c){
        switch (c.operator){
            case ">":
                c.operator = "<=";
                break;
            case ">=":
                c.operator = "<";
            case "<":
                c.operator = ">=";
                break;
            case "<=":
                c.operator = ">";
                break;
            case "=":
                c.operator = "!=";
                break;
            case "!=":
                c.operator = "=";
                break;
            default:
                c.not = true;
        }
    }

//    private static void addCommutativeCond(List<Condition> conditions, String operator, String left, String right) {
//        for(Condition con: conditions) {
//            if (con instanceof CommutativeCond) {
//                CommutativeCond c = (CommutativeCond) con;
//                if (c.operator.equals(operator)){
//                    if (c.operands.contains(left)) {
//                        c.operands.add(right);
//                        return;
//                    } else if (c.operands.contains(right)) {
//                        c.operands.add(left);
//                        return;
//                    }
//                }
//            }
//        }
//        CommutativeCond c = new CommutativeCond(operator);
//        c.operands.add(left);
//        c.operands.add(right);
//        conditions.add(c);
//    }
//
//    @Override
//    protected Condition clone() throws CloneNotSupportedException {
//        return new Condition(value);
//    }
//
//    @Override
//    public int hashCode() {
//        return value.hashCode();
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (obj instanceof CommutativeCond || obj instanceof UncommutativeCond || obj instanceof ExistCond){
//            return false;
//        }
//        return value.equals(((Condition)obj).value);
//    }

    public static void main(String[] args) {
        System.out.println(SQLBinaryOperator.BooleanAnd);
        System.out.println(SQLBinaryOperator.BooleanAnd.name);
    }
}
