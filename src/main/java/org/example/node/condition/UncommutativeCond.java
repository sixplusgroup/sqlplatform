package org.example.node.condition;

import org.example.edit.CostConfig;
import org.example.node.expr.Expr;

/**
 * @author shenyichen
 * @date 2021/12/8
 **/
public class UncommutativeCond extends AtomCond {
    public String operator;
    public Expr left;
    public Expr right;

    public UncommutativeCond(String operator, Expr left, Expr right){
        super();
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    public UncommutativeCond(boolean not, String operator, Expr left, Expr right){
        this.not = not;
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    @Override
    public Condition rearrange() {
        return this;
    }

    @Override
    public float score() {
        float score = (not ? CostConfig.not : 0) + CostConfig.math_operator;
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
            if (not) {
                if (uc.not)
                    score += CostConfig.not;
            } else {
                if (uc.not)
                    score -= CostConfig.not;
            }
            score += operator.equals(uc.operator) ? CostConfig.math_operator : 0;
            score += left.score(uc.left) + right.score(uc.right);
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
        return new UncommutativeCond(not,operator,left.clone(),right.clone());
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
        return c.not==not && c.operator.equals(operator) && c.left.equals(left) && c.right.equals(right);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (not)
            sb.append("not ");
        sb.append(operator).append("(");
        sb.append(left.toString()).append(",");
        sb.append(right.toString()).append(")");
        return sb.toString();
    }

}
