package org.example.node.expr;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.statement.SQLTableSource;
import org.example.edit.CostConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author shenyichen
 * @date 2022/4/3
 **/
public class ArithmeticExpr extends Expr {
    public String operator;
    public List<Expr> operands;

    public ArithmeticExpr(String operator, List<SQLExpr> exprs, HashMap<SQLTableSource, String> tableMapping) {
        this.operator = operator;
        operands = new ArrayList<>();
        for (SQLExpr expr: exprs) {
            operands.add(Expr.build(expr, tableMapping));
        }
    }

    @Override
    public float score() {
        float res = CostConfig.math_operator;
        for (Expr e: operands) {
            res += e.score();
        }
        return res;
    }

    @Override
    public float score(Expr e) {
        return 0;
    }

    @Override
    public Expr clone() {
        return null;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }

    @Override
    public String toString() {
        return null;
    }
}
