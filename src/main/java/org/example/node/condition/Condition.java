package org.example.node.condition;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.expr.*;
import com.alibaba.druid.sql.ast.statement.SQLSelectQuery;
import org.example.BuildAST;
import org.example.CalculateScore;
import org.example.Env;
import org.example.node.select.PlainSelect;
import org.example.node.select.Select;
import org.example.node.select.SetOpSelect;
import org.example.node.expr.Expr;
import org.example.node.expr.ListExpr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author shenyichen
 * @date 2021/12/8
 **/
public abstract class Condition {
    private static Logger logger = Logger.getLogger(Condition.class.getName());
    public String operator;
    public boolean not;

    public Condition(){
        this.not = false;
    }

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
                List<Condition> subConds = new ArrayList<>();
                subConds.add(build(left,env));
                subConds.add(build(right,env));
                return new CompoundCond(operator.name,subConds);
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
                    toExist(expr_some,subQ,op);
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
                    toExist(expr_all,subQ,op);
                    return new Exist(true,subQ);
                }
                // 普通运算符的情况
                else if (isCommutative(op)){
                    return new CommutativeCond(op,Arrays.asList(Expr.build(left), Expr.build(right)));
                }
                else {
                    return new UncommutativeCond(op, Expr.build(left), Expr.build(right));
                }
            }
        }
        // not
        else if (expr instanceof SQLNotExpr) {
            Condition c = build(((SQLNotExpr) expr).expr,env);
            setNot(c);
            return c;
        }
        // exist
        else if (expr instanceof SQLExistsExpr) {
            SQLExistsExpr existsExpr = (SQLExistsExpr) expr;
            Select subQ = BuildAST.buildSelect(existsExpr.subQuery.getQuery(),env);
            return new Exist(existsExpr.not, subQ);
        }
        // in转exist
        else if (expr instanceof SQLInSubQueryExpr) {
            SQLInSubQueryExpr inExpr = (SQLInSubQueryExpr) expr;
            Expr expr_in = Expr.build(inExpr.getExpr());
            Select subQ = BuildAST.buildSelect(inExpr.subQuery.getQuery(),env);
            toExist(expr_in, subQ, "=");
            return new Exist(inExpr.isNot(),subQ);
        }
        // between转CompoundCond
        else if (expr instanceof SQLBetweenExpr) {
            SQLBetweenExpr betweenExpr = (SQLBetweenExpr) expr;
            Expr testExpr = Expr.build(betweenExpr.testExpr);
            Expr beginExpr = Expr.build(betweenExpr.beginExpr);
            Expr endExpr = Expr.build(betweenExpr.endExpr);
            if (betweenExpr.isNot()){
                List<Condition> subConds = new ArrayList<>();
                subConds.add(new UncommutativeCond("<",testExpr,beginExpr));
                subConds.add(new UncommutativeCond(">",testExpr,endExpr));
                return new CompoundCond("OR",subConds);
            }else {
                List<Condition> subConds = new ArrayList<>();
                subConds.add(new UncommutativeCond(">=",testExpr,beginExpr));
                subConds.add(new UncommutativeCond("<=",testExpr,endExpr));
                return new CompoundCond("AND",subConds);
            }
        }
        // 其他情况
        else {
            logger.log(Level.WARNING,"Condition type not recognized: "+expr.toString());
            return new OtherCond(expr.toString());
        }
    }

    /**
     * 展平：e.g. A and B and C 按 and 展平
     * 下放not: e.g. not between的情况
     * 运算符规则化：>转<
     */
    public abstract Condition rearrange();

    public static void toExist(Expr expr, Select subQ, String operator){
        if (subQ instanceof SetOpSelect){
            toExist(expr,((SetOpSelect) subQ).left,operator);
            toExist(expr,((SetOpSelect) subQ).right,operator);
        } else if (subQ instanceof PlainSelect) {
            Condition c = null;
            List<Expr> selections = ((PlainSelect) subQ).selections;
            if (selections==null || selections.size()==0) {
                return;
            }
            if (expr instanceof ListExpr) {
                List<Expr> exprs = ((ListExpr) expr).exprs;
                int size = Math.min(exprs.size(),selections.size());
                CompoundCond cc = new CompoundCond("AND",new ArrayList<>());
                for (int i=0;i<size;i++) {
                    if (isCommutative(operator)){
                        cc.add(new CommutativeCond(operator,Arrays.asList(exprs.get(i),selections.get(i))));
                    }else {
                        cc.add(new UncommutativeCond(operator,exprs.get(i),selections.get(i)));
                    }
                }
                c = cc;
            } else {
                if (isCommutative(operator)){
                    c = new CommutativeCond(operator,Arrays.asList(expr,selections.get(0)));
                }else {
                    c = new UncommutativeCond(operator,expr,selections.get(0));
                }
            }
            ((PlainSelect) subQ).where = new CompoundCond("AND",Arrays.asList(((PlainSelect) subQ).where,c));
        }
    }

    public abstract float score();

    public abstract float score(Condition c);

    /**
     * 在 list 里有 similar 的（用于计算分数和 edit 步骤）
     */
    public static Condition isIn(Condition c, List<Condition> conditions) {
        Condition res = null;
        int score = 0;
        List<Condition> l = new ArrayList<>(conditions);
        l.sort(Comparator.comparingInt(o -> o.toString().length()));
        String s = c.toString();
        for (Condition item: l) {
            String tmp = item.toString();
            int lcs = CalculateScore.lcs(s,tmp);
            if (lcs > score) {
                score = lcs;
                res = item;
            }
        }
        return res;
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
     * 设置not
     * @param c
     */
    public static void setNot(Condition c){
        if (c instanceof CompoundCond){
            c.not = !c.not;
            if (c.not){
                boolean flag = true;
                for (Condition cdt:((CompoundCond) c).subConds){
                    if (!(cdt instanceof AtomCond))
                        flag = false;
                }
                // 如果组成复合condition的都是AtomCondition，not下放
                if (flag){
                    c.not = false;
                    switch (c.operator){
                        case "AND":
                            c.operator = "OR";
                            break;
                        case "OR":
                            c.operator = "AND";
                            break;
                        default:
                            break;
                    }
                    for (Condition cdt:((CompoundCond) c).subConds){
                        setNot(cdt);
                    }
                }
            }
        }
        else if (c instanceof AtomCond){
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
        else {
            c.not = !c.not;
        }
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

    @Override
    public abstract Condition clone();

    @Override
    public abstract int hashCode();

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract String toString();

    public static void main(String[] args) {
        System.out.println(SQLBinaryOperator.BooleanAnd);
        System.out.println(SQLBinaryOperator.BooleanAnd.name);
    }

}
