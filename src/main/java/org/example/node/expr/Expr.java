package org.example.node.expr;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.expr.*;
import org.example.CalculateScore;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shenyichen
 * @date 2021/12/7
 **/
public abstract class Expr {
    public static Expr build(SQLExpr expr){
        if (expr == null)
            return null;
        if (expr instanceof SQLAggregateExpr) {
            SQLAggregateExpr aggrExpr = (SQLAggregateExpr) expr;
            return new FuncExpr(aggrExpr.getMethodName(),aggrExpr.getArguments());
        } else if (expr instanceof SQLMethodInvokeExpr) {
            SQLMethodInvokeExpr methodExpr = (SQLMethodInvokeExpr) expr;
            return new FuncExpr(methodExpr.getMethodName(),methodExpr.getArguments());
        } else if (expr instanceof SQLPropertyExpr) {
            SQLPropertyExpr propExpr = (SQLPropertyExpr) expr;
            return new PropertyExpr(propExpr.getOwner().toString(),propExpr.getName());
        } else if (expr instanceof SQLIdentifierExpr) {
            // TODO 后续统一改为表名+属性之后，删这个情况
            SQLIdentifierExpr idExpr = (SQLIdentifierExpr) expr;
            return new PropertyExpr("null",idExpr.getName());
        } else if (expr instanceof SQLNullExpr) {
            return new ConstantExpr("NULL");
        } else if (expr instanceof SQLIntegerExpr) {
            return new ConstantExpr(((SQLIntegerExpr) expr).getValue().toString());
        } else if (expr instanceof SQLNumberExpr) {
            return new ConstantExpr(((SQLNumberExpr) expr).getValue().toString());
        } else if (expr instanceof SQLListExpr) {
            return new ListExpr(((SQLListExpr) expr).getItems()
            .stream()
            .map(Expr::build)
            .collect(Collectors.toList()));
        } else {
            return new OtherExpr(expr.toString());
        }
    }

    public abstract float score();

    public abstract float score(Expr e);

    public static boolean isStrictlyIn(Expr e, List<Expr> l){
        boolean flag = false;
        for (Expr tmp: l){
            if (e.equals(tmp)){
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * 在 list 里有 similar 的（用于计算分数和 edit 步骤）
     */
    public static Expr isIn(Expr e, List<Expr> l) {
        Expr res = null;
        float score = 0.0f;
        String s = e.toString();
        for (Expr item: l) {
            String tmp = item.toString();
            int lcs = CalculateScore.lcs(s,tmp);
            if (lcs > score && lcs > 0.5) {
                score = lcs;
                res = item;
            }
        }
        return res;
    }

    /**
     * 被 contains 方法调用
     * @return
     */
    @Override
    public abstract Expr clone();

    @Override
    public abstract int hashCode();

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract String toString();
}
