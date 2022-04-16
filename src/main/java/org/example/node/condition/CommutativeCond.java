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

    public CommutativeCond(String operator, List<Expr> operands, String originStr, String opOriginStr) {
        super(originStr);
        this.operator = operator;
        this.opOriginStr = opOriginStr;
        this.operands = new ArrayList<>();
        if (operands != null){
            this.operands.addAll(operands);
        }
    }

    public CommutativeCond(String operator, List<Expr> operands, String originStr) {
        this(operator, operands, originStr, originStr);
    }

    @Override
    public String getOriginStr() {
        if (originStr != null)
            return originStr;
        StringBuilder sb = new StringBuilder();
        List<String> operands_s = operands.stream()
                .map(e->e.originStr)
                .collect(Collectors.toList());
        Collections.sort(operands_s);
        List<String> conds = new ArrayList<>();
        for (int i=1;i<operands.size();i++) {
            conds.add(operands_s.get(0) + " " + operator + " " + operands_s.get(i));
        }
        sb.append(String.join(" AND ", conds));
        return sb.toString();
    }

    @Override
    public Condition rearrange() {
        return this;
    }

    @Override
    public float score() {
        float score = CostConfig.math_operator;
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
        return new CommutativeCond(operator, exprs, getOriginStr(), opOriginStr);
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
        if (!(c.operator.equals(operator)))
            return false;
        if (c.operands.size()!=operands.size())
            return false;
        for (Expr tmp: operands) {
            if (!Expr.isDirectlyStrictlyIn(tmp,c.operands))
                return false;
        }
        for (Expr tmp: c.operands) {
            if (!Expr.isDirectlyStrictlyIn(tmp,operands))
                return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        List<String> operands_s = operands.stream()
                .map(Expr::toString)
                .collect(Collectors.toList());
        Collections.sort(operands_s);
        List<String> conds = new ArrayList<>();
        for (int i=1;i<operands.size();i++) {
            conds.add(operands_s.get(0) + " " + operator + " " + operands_s.get(i));
        }
        sb.append(String.join(" AND ", conds));
        return sb.toString();
    }

}
