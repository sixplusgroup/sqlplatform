package org.example.node.expr;

import org.example.CalculateScore;
import org.example.edit.CostConfig;

/**
 * @author shenyichen
 * @date 2022/1/17
 **/
public class OtherExpr extends Expr {
    public String value;

    public OtherExpr(String value, String originStr) {
        super(originStr);
        this.value = value.toLowerCase();
    }

    @Override
    public float score() {
        int times = value.length() / CostConfig.other_digits;
        return CostConfig.other * times;
    }

    @Override
    public float score(Expr e) {
        // case 1: this equals e
        if (e instanceof OtherExpr) {
            return score() *
                    (equals(e) ? 1 : (1 -
                            1.0f * CalculateScore.editDistance(e.toString(),this.toString())
                            / Math.max(e.toString().length(),this.toString().length())));
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
        return new OtherExpr(value, originStr);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof OtherExpr))
            return false;
        int d = CalculateScore.editDistance(((OtherExpr) obj).value, value);
        if (d <= 0.4 * value.length())
            return true;
        return false;
    }

    @Override
    public String toString() {
        return value;
    }
}
