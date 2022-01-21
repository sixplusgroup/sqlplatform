package org.example.node.condition;

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

    @Override
    public Condition rearrange() {
        return this;
    }

//    @Override
//    protected UncommutativeCond clone() throws CloneNotSupportedException {
//        return new UncommutativeCond(operator,left,right);
//    }
//
//    @Override
//    public int hashCode() {
//        int hashCode = 1;
//        hashCode = 31*hashCode + (operator==null?0:operator.hashCode());
//        hashCode = 31*hashCode + (left==null?0:left.hashCode());
//        hashCode = 31*hashCode + (right==null?0:right.hashCode());
//        return hashCode;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (obj instanceof UncommutativeCond){
//            UncommutativeCond cond = (UncommutativeCond) obj;
//            if (!(operator.equals(cond.operator)))
//                return false;
//            if (!(left.equals(cond.left)))
//                return false;
//            if (!(right.equals(cond.right)))
//                return false;
//            return true;
//        }
//        return false;
//    }


}
