package org.example.node;

import org.example.node.expr.Expr;

/**
 * @author shenyichen
 * @date 2021/12/8
 **/
public class Table implements Expr {
    public String name;

    public Table(){}

    public Table(String name) {
        this.name = name;
    }

    @Override
    protected Table clone() throws CloneNotSupportedException {
        return new Table(this.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object t) {
        if (t instanceof Table){
            return name.equals(((Table) t).name);
        }
        return false;
    }
}
