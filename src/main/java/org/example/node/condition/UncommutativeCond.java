package org.example.node.condition;

import org.example.edit.CostConfig;
import org.example.node.expr.Expr;

/**
 * @author shenyichen
 * @date 2021/12/8
 **/
public class UncommutativeCond extends AtomCond {
    public Expr left;
    public Expr right;

    public UncommutativeCond(String operator, Expr left, Expr right, String originStr, String opOriginStr) {
        super(originStr);
        this.operator = operator;
        this.opOriginStr = opOriginStr;
        this.left = left;
        this.right = right;
    }

    public UncommutativeCond(String operator, Expr left, Expr right, String originStr) {
        this(operator, left, right, originStr, originStr);
    }

    @Override
    public Condition rearrange() {
        return this;
    }

    @Override
    public float score() {
        float score = CostConfig.math_operator;
        score += left.score();
        score += right.score();
        return score;
    }

    @Override
    public float score(Condition c) {
        if (c == null)
            return 0;
        float score = 0;
        if (c instanceof UncommutativeCond) {
            UncommutativeCond uc = (UncommutativeCond) c;
            score += operator.equals(uc.operator) ? CostConfig.math_operator : 0;
            score += left.score(uc.left) + right.score(uc.right);
            float tmpScore = 0;
            tmpScore += operator.equals(Condition.getOppositeOp(uc.operator)) ? CostConfig.math_operator : 0;
            tmpScore += left.score(uc.right) + right.score(uc.left);
            score = Math.max(score, tmpScore);
        }
        else if (c instanceof CompoundCond) {
            CompoundCond cc = (CompoundCond) c;
            Condition match = Condition.isIn(this,cc.getSubConds());
            if (match != null) {
                score = score(match) - (cc.score() - match.score()) * CostConfig.delete_cost_rate;
            }
        }
        return score;
    }

    @Override
    public UncommutativeCond clone() {
        return new UncommutativeCond(operator, left.clone(), right.clone(), originStr, opOriginStr);
    }

    @Override
    public int hashCode() {
        return operator.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof UncommutativeCond))
            return false;
        UncommutativeCond c = (UncommutativeCond) obj;
        boolean equal = c.operator.equals(operator) && c.left.equals(left) && c.right.equals(right);
        if (!equal && operator.equals(Condition.getOppositeOp(c.operator))) {
            equal = c.left.equals(right) && c.right.equals(left);
        }
        return equal;
    }

    @Override
    public String toString() {
        return left.toString() + " " + operator + " " + right.toString();
    }

}
