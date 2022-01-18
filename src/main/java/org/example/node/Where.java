package org.example.node;


import org.example.node.condition.Condition;
import org.example.node.condition.Exist;
import org.example.node.expr.Expr;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shenyichen
 * @date 2021/12/9
 **/
public class Where implements Expr {
    public List<Condition> conds;
    public List<Select> subqueries;
    public List<Condition> all;

    public Where(List<Condition> all){
        this.all = all;
        conds = new ArrayList<>();
        subqueries = new ArrayList<>();
        for (Condition c: all){
            if (c instanceof Exist){
                subqueries.add(((Exist) c).subQuery);
            }else {
                conds.add(c);
            }
        }
    }

    @Override
    protected Where clone() throws CloneNotSupportedException {
        List<Condition> all = new ArrayList<>();
        for (Condition c: this.all){
//            all.add(c.clone());
        }
        return new Where(all);
    }
}
