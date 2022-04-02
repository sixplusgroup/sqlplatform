package org.example.node.condition;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.expr.*;
import com.alibaba.druid.sql.ast.statement.SQLSelectQuery;
import com.alibaba.druid.sql.ast.statement.SQLTableSource;
import org.example.BuildAST;
import org.example.CalculateScore;
import org.example.Env;
import org.example.node.select.PlainSelect;
import org.example.node.select.Select;
import org.example.node.select.SetOpSelect;
import org.example.node.expr.Expr;
import org.example.node.expr.ListExpr;
import org.example.util.ErrorLogger;

import java.util.*;

/**
 * @author shenyichen
 * @date 2021/12/8
 **/
public abstract class Condition {
    public CompoundCond father;
    public String operator;
    protected boolean not;

    public Condition(){
        not = false;
        father = null;
    }

    /**
     * SQLExpr转Condition
     * @param expr
     * @return
     */
    public static Condition build(SQLExpr expr, Env env, HashMap<SQLTableSource, String> tableMapping){
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
                subConds.add(build(left, env, tableMapping));
                subConds.add(build(right, env, tableMapping));
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
                    Expr expr_some = Expr.build(left, tableMapping);
                    Select subQ = BuildAST.buildSelect(selectQuery,env);
                    toExist(expr_some,subQ,op);
                    return new Exist(false,subQ);
                }
                // all转not exist
                else if (left instanceof SQLAllExpr || left instanceof SQLQueryExpr
                        || right instanceof SQLAllExpr || right instanceof SQLQueryExpr){
                    op = getComplementaryOp(op);
                    if (left instanceof SQLAllExpr || left instanceof SQLQueryExpr){
                        SQLExpr tmp = left;
                        left = right;
                        right = tmp;
                        op = getOppositeOp(op);
                    }
                    Expr expr_all = Expr.build(left, tableMapping);
                    SQLSelectQuery selectQuery;
                    if (right instanceof SQLAllExpr) {
                        selectQuery = ((SQLAllExpr)right).subQuery.getQuery();
                    }
                    else {
                        selectQuery = ((SQLQueryExpr)right).subQuery.getQuery();
                    }
                    Select subQ = BuildAST.buildSelect(selectQuery,env);
                    toExist(expr_all,subQ,op);
                    return new Exist(true,subQ);
                }
                // 普通运算符的情况
                else if (isCommutative(op)){
                    return new CommutativeCond(op,
                            Arrays.asList(Expr.build(left, tableMapping), Expr.build(right, tableMapping)));
                }
                else {
                    return new UncommutativeCond(op, Expr.build(left, tableMapping), Expr.build(right, tableMapping));
                }
            }
        }
        // not
        else if (expr instanceof SQLNotExpr) {
            Condition c = build(((SQLNotExpr) expr).expr, env, tableMapping);
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
            Expr expr_in = Expr.build(inExpr.getExpr(), tableMapping);
            Select subQ = BuildAST.buildSelect(inExpr.subQuery.getQuery(),env);
            toExist(expr_in, subQ, "=");
            return new Exist(inExpr.isNot(),subQ);
        }
        // between 转 CompoundCond
        else if (expr instanceof SQLBetweenExpr) {
            SQLBetweenExpr betweenExpr = (SQLBetweenExpr) expr;
            Expr testExpr = Expr.build(betweenExpr.testExpr, tableMapping);
            Expr beginExpr = Expr.build(betweenExpr.beginExpr, tableMapping);
            Expr endExpr = Expr.build(betweenExpr.endExpr, tableMapping);
            if (betweenExpr.isNot()) {
                List<Condition> subConds = new ArrayList<>();
                subConds.add(new UncommutativeCond("<",testExpr,beginExpr));
                subConds.add(new UncommutativeCond(">",testExpr,endExpr));
                return new CompoundCond("OR", subConds);
            }else {
                List<Condition> subConds = new ArrayList<>();
                subConds.add(new UncommutativeCond(">=",testExpr,beginExpr));
                subConds.add(new UncommutativeCond("<=",testExpr,endExpr));
                return new CompoundCond("AND", subConds);
            }
        }
        // 其他情况
        else {
            ErrorLogger.logSevere("Condition type not recognized: " + expr.toString());
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
            Condition c;
            List<Expr> selections = ((PlainSelect) subQ).selections;
            if (selections == null || selections.size() == 0) {
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
            ((PlainSelect) subQ).where = ((PlainSelect) subQ).where.rearrange();
        }
    }

    public abstract float score();

    public abstract float score(Condition c);

    /**
     * 在 list 本身或子 Condition 里有 similar 的（用于计算分数和 edit 步骤）
     */
    public static Condition isIn(Condition c, List<Condition> l) {
        Condition res = null;
        String s = c.toString();
        int distance = s.length();
        for (Condition item: l) {
            Condition match = findEqual(c,item);
            if (match != null) {
                int d = CalculateScore.editDistance(s,match.toString());
                if (d < distance && d < 0.5 * s.length()) {
                    distance = d;
                    res = item;
                }
            }
        }
        return res;
    }

    /**
     * 在 list 本身里有一样的，用于 flateen instr或stu步骤
     * @param c
     * @param l
     * @return
     */
    public static boolean isDirectlyIn(Condition c, List<Condition> l) {
        for (Condition item: l) {
            if (c.equals(item))
                return true;
        }
        return false;
    }

    /**
     * 在 instr 的树结构中找到与 stu 对等的节点
     * @param instr
     * @param stu
     * @return
     */
    public static Condition findEqual(Condition instr, Condition stu) {
        Condition p = instr;
        int distance = CalculateScore.editDistance(p.toString(), stu.toString());
        while (p instanceof CompoundCond) {
            CompoundCond cc = (CompoundCond) p;
            List<Condition> subConds = new ArrayList<>(cc.getSubConds());
            subConds.sort(Comparator.comparingInt(o -> CalculateScore.editDistance(o.toString(), stu.toString())));
            Condition tmp = subConds.get(0);
            int d = CalculateScore.editDistance(tmp.toString(), stu.toString());
            if (d < distance) {
                distance = d;
                p = tmp;
            } else {
                break;
            }
        }
        int len = stu.toString().length();
        if (distance > len / 2)
            return null;
        return p;
    }

    /**
     * 在 stu 里找到对应的 c
     * @param c
     * @param stu
     * @return
     */
    public static Condition find(Condition c, Condition stu) {
        Condition p = stu;
        if (p.equals(c))
            return p;
        while (p instanceof CompoundCond) {
            CompoundCond cc = (CompoundCond) p;
            List<Condition> subConds = new ArrayList<>(cc.getSubConds());
            subConds.sort(Comparator.comparingInt(o -> CalculateScore.editDistance(o.toString(), c.toString())));
            Condition tmp = subConds.get(0);
            if (tmp.equals(c)) {
                return tmp;
            }
            p = tmp;
        }
        return null;
    }

    /**
     * 在列表中找到最相似的
     * @param c
     * @param l
     * @return
     */
    public static Condition findMostSimalrInList(Condition c, List<Condition> l) {
        List<Condition> subConds = new ArrayList<>(l);
        subConds.sort(Comparator.comparingInt(o -> CalculateScore.editDistance(o.toString(), c.toString())));
        return subConds.get(0);
    }

    /**
     * 两个 Condition 是否相似
     * @param a
     * @param b
     * @return
     */
    public static boolean isSimilar(Condition a, Condition b) {
        String a_s = a.toString();
        String b_s = b.toString();
        int d = CalculateScore.editDistance(a_s, b_s);
        if (d < a_s.length() / 3 && d < b_s.length() / 3)
            return true;
        return false;
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
                for (Condition cdt:((CompoundCond) c).getSubConds()){
                    setNot(cdt);
                }
            }
        }
        else if (c instanceof AtomCond){
            c.operator = getComplementaryOp(c.operator);
        }
        else {
            c.not = !c.not;
        }
    }

    /**
     * 用于对operator取反
     * @param op
     * @return
     */
    public static String getComplementaryOp(String op) {
        switch (op){
            case ">":
                return "<=";
            case ">=":
                return "<";
            case "<":
                return ">=";
            case "<=":
                return ">";
            case "=":
                return "!=";
            case "!=":
                return "=";
            case "==":
                return "!==";
            case "===":
                return "!===";
            case "!==":
                return "==";
            case "!===":
                return "===";
            default:
                return op;
        }
    }

    /**
     * 用于交换left, right时
     * @param op
     * @return
     */
    public static String getOppositeOp(String op){
        if (isCommutative(op))
            return op;
        switch (op){
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
                return op;
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

    public boolean getNot() {
        return not;
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
