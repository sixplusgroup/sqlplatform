package org.example.node.condition;

import com.alibaba.druid.sql.ast.SQLExpr;
import org.example.node.expr.Expr;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shenyichen
 * @date 2021/12/8
 **/
public class CommutativeCond extends AtomCond {
    public List<Expr> operands;

    public CommutativeCond(String operator, Expr left, Expr right){
        this.operator = operator;
        operands = new ArrayList<>();
        operands.add(left);
        operands.add(right);
    }

    @Override
    public void flatten() {

    }

//    @Override
//    protected CommutativeCond clone() throws CloneNotSupportedException {
//        CommutativeCond cond = new CommutativeCond(operator);
//        for (String s: operands){
//            cond.operands.add(s);
//        }
//        return cond;
//    }
//
//    @Override
//    public int hashCode() {
//        int hashCode = 1;
//        hashCode = 31 * hashCode + (operator==null?0:operator.hashCode());
//        return hashCode;
//    }
//
//    /**
//     * 被contains方法调用，operands只判断是否有重合
//     * @param obj
//     * @return
//     */
//    @Override
//    public boolean equals(Object obj) {
//        if (obj instanceof CommutativeCond){
//            CommutativeCond cond = (CommutativeCond) obj;
//            if (!(cond.operator.equals(operator)))
//                return false;
//            for (String s1: operands){
//                for (String s2: cond.operands){
//                    if (s1.equals(s2))
//                        return true;
//                }
//            }
//            return false;
//        }
//        return false;
//    }
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
