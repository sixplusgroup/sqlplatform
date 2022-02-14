package org.example.node.table;

import org.example.edit.CostConfig;
import org.example.node.expr.AtomExpr;

/**
 * @author shenyichen
 * @date 2022/1/26
 **/
public class PlainTable extends Table {
    public AtomExpr name;

    public PlainTable(){}

    public PlainTable(String name) {
        this.name = new AtomExpr(name);
    }

    public PlainTable(AtomExpr name) {
        this.name = name;
    }

    @Override
    public float score() {
        return CostConfig.table;
    }

    @Override
    public float score(Table t) {
        if (t instanceof PlainTable) {
            return equals(t) ? score() : 0;
        }
        return 0;
    }

    @Override
    public Table clone() {
        return new PlainTable(name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object t) {
        if (t instanceof PlainTable){
            return name.equals(((PlainTable) t).name);
        }
        return false;
    }

    @Override
    public String toString() {
        return name.toString();
    }
}
