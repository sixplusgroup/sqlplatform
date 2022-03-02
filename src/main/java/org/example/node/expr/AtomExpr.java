package org.example.node.expr;

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
        return 1;
    }

    @Override
    public float score(Expr e) {
        return equals(e) ? score() : 0;
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
        return false;
    }

    @Override
    public String toString() {
        return value;
    }
}