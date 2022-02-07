package org.example.node.condition;

import org.example.edit.CostConfig;
import org.example.node.expr.Expr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shenyichen
 * @date 2021/12/8
 **/
public class CommutativeCond extends AtomCond {
    public List<Expr> operands;

    public CommutativeCond(String operator, List<Expr> operands){
        super();
        this.operator = operator;
        this.operands = new ArrayList<>();
        if (operands!=null){
            this.operands.addAll(operands);
        }
    }

    public CommutativeCond(boolean not, String operator, List<Expr> operands){
        this.not = not;
        this.operator = operator;
        this.operands = new ArrayList<>();
        if (operands!=null){
            this.operands.addAll(operands);
        }
    }

    @Override
    public Condition rearrange() {
        return this;
    }

    @Override
    public float score() {
        float score = (not ? CostConfig.not : 0) + CostConfig.math_operator;
        for (Expr e: operands) {
            score += e.score();
        }
        return score;
    }

    @Override
    public float score(Condition c) {
        if (c == null)
            return 0;
        float score = 0;
        if (c instanceof CommutativeCond) {
            CommutativeCond cc = (CommutativeCond) c;
            if (not) {
                if (cc.not)
                    score += CostConfig.not;
            } else {
                if (cc.not)
                    score -= CostConfig.not;
            }
            score += operator.equals(cc.operator) ? CostConfig.math_operator : 0;
            score += Expr.listScore(operands, cc.operands);
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
    public CommutativeCond clone() {
        List<Expr> exprs = operands.stream()
                .map(Expr::clone)
                .collect(Collectors.toList());
        return new CommutativeCond(not,operator,exprs);
    }

    @Override
    public int hashCode() {
        return operator.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof CommutativeCond))
            return false;
        CommutativeCond c = (CommutativeCond) obj;
        if (c.not!=not)
            return false;
        if (!(c.operator.equals(operator)))
            return false;
        if (c.operands.size()!=operands.size())
            return false;
        for (Expr tmp: operands) {
            if (!Expr.isStrictlyIn(tmp,c.operands))
                return false;
        }
        for (Expr tmp: c.operands) {
            if (!Expr.isStrictlyIn(tmp,operands))
                return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (not)
            sb.append("not ");
        sb.append(operator).append("(");
        List<String> operands_s = operands.stream()
                .map(Expr::toString)
                .collect(Collectors.toList());
        Collections.sort(operands_s);
        sb.append(String.join(",",operands_s));
        sb.append(")");
        return sb.toString();
    }

}
