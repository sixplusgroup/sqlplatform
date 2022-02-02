package org.example.node.expr;

import org.example.edit.CostConfig;

/**
 * @author shenyichen
 * @date 2022/1/17
 **/
public class ConstantExpr extends Expr {
    public String value;

    public ConstantExpr(){}

    public ConstantExpr(String value) {
        this.value = value;
    }

    @Override
    public float score() {
        return CostConfig.constant;
    }

    @Override
    public float score(Expr e) {
        // case 1: this equals e
        if (e instanceof ConstantExpr) {
            return equals(e) ? score() : 0;
        } else if (e instanceof FuncExpr) {
            // case 2: e includes this
            FuncExpr fe = (FuncExpr) e;
            Expr match = Expr.isIn(this,fe.parameters);
            if (match != null) {
                return score(match) - (fe.score() - match.score());
            }
        }
        return -e.score();
    }

    @Override
    public Expr clone() {
        return new ConstantExpr(value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ConstantExpr))
            return false;
        return ((ConstantExpr) obj).value.equals(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
