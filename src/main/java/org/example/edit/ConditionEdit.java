package org.example.edit;

import javafx.util.Pair;
import org.example.node.CommutativeCond;
import org.example.node.Condition;
import org.example.node.PlainSelect;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shenyichen
 * @date 2021/12/10
 **/
public class ConditionEdit implements Edit {
    @Override
    public List<Pair<PlainSelect, Float>> add(PlainSelect instr, PlainSelect stu) throws CloneNotSupportedException {
        List<Pair<PlainSelect,Float>> res = new ArrayList<Pair<PlainSelect,Float>>();

        // from
        for(Condition c:instr.from.joinConditions)
        {
            if(!stu.from.joinConditions.contains(c))
            {
                PlainSelect edited = stu.clone();
                edited.from.joinConditions.add(c);
                res.add(new Pair<>(edited,EditCostConfig.conditions));
            }
        }

        // where
        for(Condition c:instr.where.conds)
        {
            if(!stu.where.conds.contains(c))
            {
                PlainSelect edited = stu.clone();
                edited.where.conds.add(c);
                res.add(new Pair<>(edited,EditCostConfig.conditions));
            }
        }

        return res;
    }

    @Override
    public List<Pair<PlainSelect, Float>> remove(PlainSelect instr, PlainSelect stu) throws CloneNotSupportedException {
        List<Pair<PlainSelect,Float>> res = new ArrayList<>();

        // from
        for(Condition c:stu.from.joinConditions)
        {
            if(!instr.from.joinConditions.contains(c))
            {
                PlainSelect edited = stu.clone();
                edited.from.joinConditions.remove(c);
                res.add(new Pair<>(edited,EditCostConfig.conditions/2));
            }
        }

        // where
        for(Condition c:stu.where.conds)
        {
            if(!instr.where.conds.contains(c))
            {
                PlainSelect edited = stu.clone();
                edited.where.conds.remove(c);
                res.add(new Pair<>(edited,EditCostConfig.conditions/2));
            }
        }

        return res;
    }

    @Override
    public List<Pair<PlainSelect, Float>> edit(PlainSelect instr, PlainSelect stu) throws CloneNotSupportedException {
        List<Pair<PlainSelect,Float>> res = new ArrayList <>();

        // from
        PlainSelect stu_not_matched_from = stu.clone();
        PlainSelect stu_matched_from = stu.clone();
        PlainSelect ins_not_matched_from = instr.clone();
        for(Condition s:instr.from.joinConditions)
        {
            if(stu.from.joinConditions.contains(s))
            {
                ins_not_matched_from.from.joinConditions.remove(s);
            }
        }
        for(Condition s:stu.from.joinConditions)
        {
            if(instr.from.joinConditions.contains(s))
            {
                stu_not_matched_from.from.joinConditions.remove(s);
            }
            else
            {
                stu_matched_from.from.joinConditions.remove(s);
            }
        }
        for(Condition st:stu_not_matched_from.from.joinConditions)
        {
            for(Condition t: ins_not_matched_from.from.joinConditions)
            {
                PlainSelect edited = stu.clone();
                edited.from.joinConditions.remove(st);
                edited.from.joinConditions.add(t);
                res.add(new Pair<>(edited,EditCostConfig.conditions));
            }
        }
        // from: commutative的edit
        for (Condition st: stu.from.joinConditions){
            for (Condition t: instr.from.joinConditions){
                if (!(st instanceof CommutativeCond))
                    continue;
                if (!(t instanceof CommutativeCond))
                    continue;
                CommutativeCond st1 = (CommutativeCond) st;
                int cost = st1.costOfEqualCond((CommutativeCond)t);
                if (cost!=0){
                    PlainSelect edited = stu.clone();
                    edited.from.joinConditions.remove(st);
                    edited.from.joinConditions.add(t);
                    res.add(new Pair<>(edited, cost*1.0f));
                }
            }
        }

        // where
        PlainSelect stu_not_matched = stu.clone();
        PlainSelect stu_matched = stu.clone();
        PlainSelect ins_not_matched = instr.clone();
        for(Condition s:instr.where.conds)
        {
            if(stu.where.conds.contains(s))
            {
                ins_not_matched.where.conds.remove(s);
            }
        }
        for(Condition s:stu.where.conds)
        {
            if(instr.where.conds.contains(s))
            {
                stu_not_matched.where.conds.remove(s);
            }
            else
            {
                stu_matched.where.conds.remove(s);
            }
        }
        for(Condition st:stu_not_matched.where.conds)
        {
            for(Condition t: ins_not_matched.where.conds)
            {
                PlainSelect edited = stu.clone();
                edited.where.conds.remove(st);
                edited.where.conds.add(t);
                res.add(new Pair<>(edited,EditCostConfig.conditions));
            }
        }
        // where: commutative的edit
        for (Condition st: stu.where.conds){
            for (Condition t: instr.where.conds){
                if (!(st instanceof CommutativeCond))
                    continue;
                if (!(t instanceof CommutativeCond))
                    continue;
                CommutativeCond st1 = (CommutativeCond) st;
                int cost = st1.costOfEqualCond((CommutativeCond)t);
                if (cost!=0){
                    PlainSelect edited = stu.clone();
                    edited.where.conds.remove(st);
                    edited.where.conds.add(t);
                    res.add(new Pair<>(edited, cost*1.0f));
                }
            }
        }

        return res;
    }
}
