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
        return 0;
    }

    @Override
    public float score(Condition c) {
        return 0;
    }

    @Override
    public Condition clone() {
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
        return null;
    }

}
