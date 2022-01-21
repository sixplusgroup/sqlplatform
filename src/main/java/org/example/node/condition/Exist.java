package org.example.node.condition;

import org.example.node.Select;

/**
 * @author shenyichen
 * @date 2021/12/9
 **/
public class Exist extends Condition {
    public Select subQuery;

    @Override
    public Condition rearrange() {
        return this;
    }

    public Exist(){

    }

    public Exist(boolean not, Select subQuery) {
        this.not = not;
        this.subQuery = subQuery;
    }

//    @Override
//    protected ExistCond clone() throws CloneNotSupportedException {
//        ExistCond cond = new ExistCond();
//        cond.not = not;
//        cond.subQuery = (Select) subQuery.clone();
//        return cond;
//    }
}
