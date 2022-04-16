package org.example.node.expr;

import org.example.edit.CostConfig;

/**
 * @author shenyichen
 * @date 2022/1/17
 **/
public class ConstantExpr extends Expr {
    public String value;

    public ConstantExpr(String originStr){
        super(originStr);
        this.value = originStr;
    }

    public ConstantExpr(String value, String originStr) {
        this(originStr);
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
            Expr match = Expr.isIn(this,fe.parameters).getKey();
            if (match != null) {
                return score(match) - (fe.score() - match.score()) * CostConfig.delete_cost_rate;
            }
        }
        return 0;
    }

    @Override
    public Expr clone() {
        return new ConstantExpr(value, originStr);
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
