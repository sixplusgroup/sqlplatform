package org.example.node.table;

import org.example.edit.CostConfig;
import org.example.node.select.Select;

import java.util.Arrays;

/**
 * @author shenyichen
 * @date 2022/1/26
 **/
public class PlainTable extends Table {
    public String name;

    public PlainTable(){}

    public PlainTable(String name) {
        this.name = name;
    }

    @Override
    public float score() {
        return CostConfig.table;
    }

    @Override
    public float score(Table t) {
        // case 1: this equals t
        if (t instanceof PlainTable) {
            return equals(t) ? score() : 0;
        } else if (t instanceof JoinTable) {
            // case 2: t includes this
            JoinTable jt = (JoinTable) t;
            Table match = Table.isIn(this, Arrays.asList(jt.left, jt.right));
            if (match != null) {
                return score(match) - (jt.score() - match.score());
            }
        }
        return -t.score();
    }

    @Override
    public Table clone() {
        return new PlainTable(this.name);
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
        return name;
    }
}
