package org.example.node.condition;

import org.example.node.expr.Expr;

import java.util.ArrayList;
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
        return 0;
    }

    @Override
    public float score(Condition c) {
        return 0;
    }

    @Override
    public Condition clone() {
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
        return true;
    }

    @Override
    public String toString() {
        return null;
    }

//
//    /**
//     * 计算equal的两个cond之间的编辑距离，用operands的差集算
//     * @param cond
//     * @return
//     */
//    public int costOfEqualCond(CommutativeCond cond){
//        int plus = 0;
//        int minus = 0;
//        for (String s1: operands){
//            if (!cond.operands.contains(s1))
//                plus += 1;
//        }
//        for (String s1: cond.operands){
//            if (!operands.contains(s1))
//                minus += 1;
//        }
//        return Math.abs(plus-minus);
//    }

}
