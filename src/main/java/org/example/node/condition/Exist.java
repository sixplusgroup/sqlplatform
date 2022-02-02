package org.example.node.condition;

import org.example.node.select.Select;

/**
 * @author shenyichen
 * @date 2021/12/9
 **/
public class Exist extends Condition {
    public Select subQuery;

    public Exist(){

    }

    public Exist(boolean not, Select subQuery) {
        this.not = not;
        this.subQuery = subQuery;
        Select.subQueryProcess(this.subQuery);
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
        return new Exist(not,subQuery.clone());
    }

    @Override
    public int hashCode() {
        return subQuery.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Exist))
            return false;
        Exist e = (Exist) obj;
        return e.not==not && e.subQuery.equals(subQuery);
    }

    @Override
    public String toString() {
        return null;
    }

}
