package org.example.node.expr;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.expr.*;

import java.util.ArrayList;

/**
 * @author shenyichen
 * @date 2021/12/7
 **/
public interface Expr {
    static Expr build(SQLExpr expr){
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
            return new PropertyExpr(null,idExpr.getName());
        } else if (expr instanceof SQLNullExpr) {
            return new ConstantExpr("NULL");
        } else if (expr instanceof SQLIntegerExpr) {
            return new ConstantExpr(((SQLIntegerExpr) expr).getValue().toString());
        } else {
            return new OtherExpr(expr.toString());
        }
    }
}
