package org.example.node.expr;

import org.example.edit.CostConfig;

/**
 * @author shenyichen
 * @date 2022/2/14
 **/
public class AtomExpr extends Expr {
    public String value;
    public boolean substituted;

    public AtomExpr() {
        substituted = false;
    }

    public AtomExpr(String value) {
        this();
        this.value = value;
    }

    @Override
    public float score() {
        return CostConfig.property;
    }

    @Override
    public float score(Expr e) {
        if (e instanceof AtomExpr) {
            return equals(e) ? score() : 0;
        }
        else if (e instanceof PropertyExpr) {
            return equals(((PropertyExpr) e).attribute) ? score() : 0;
        }
        return 0;
    }

    @Override
    public Expr clone() {
        return new AtomExpr(value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AtomExpr)
            return value.equals(((AtomExpr) obj).value);
        else if (obj instanceof PropertyExpr)
            return value.equals(((PropertyExpr) obj).attribute.value);
        return false;
    }

    @Override
    public String toString() {
        return value;
    }
}
