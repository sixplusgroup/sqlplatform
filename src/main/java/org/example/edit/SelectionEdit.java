package org.example.edit;

import javafx.util.Pair;
import org.example.node.PlainSelect;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shenyichen
 * @date 2021/12/10
 **/
public class SelectionEdit implements Edit {
    @Override
    public List<Pair<PlainSelect, Float>> add(PlainSelect instr, PlainSelect stu) throws CloneNotSupportedException {
        List<Pair<PlainSelect,Float>> res = new ArrayList<Pair<PlainSelect,Float>>();
        for(String s:instr.selections)
        {
            if(!stu.selections.contains(s))
            {
                PlainSelect edited = stu.clone();
                edited.selections.add(s);
                res.add(new Pair<>(edited,EditCostConfig.selections));
            }
        }
        return res;
    }

    @Override
    public List<Pair<PlainSelect, Float>> remove(PlainSelect instr, PlainSelect stu) throws CloneNotSupportedException {
        List<Pair<PlainSelect,Float>> res = new ArrayList<>();
        for(String s:stu.selections)
        {
            if(!instr.selections.contains(s))
            {
                PlainSelect edited = stu.clone();
                edited.selections.remove(s);
                res.add(new Pair<>(edited,EditCostConfig.selections/2));
            }
        }
        return res;
    }

    @Override
    public List<Pair<PlainSelect, Float>> edit(PlainSelect instr, PlainSelect stu) throws CloneNotSupportedException {
        List<Pair<PlainSelect,Float>> res = new ArrayList <>();
        PlainSelect stu_not_matched = stu.clone();
        PlainSelect stu_matched = stu.clone();
        PlainSelect ins_not_matched = instr.clone();
        for(String s:instr.selections)
        {
            if(stu.selections.contains(s))
            {
                ins_not_matched.selections.remove(s);
            }
        }
        for(String s:stu.selections)
        {
            if(instr.selections.contains(s))
            {
                stu_not_matched.selections.remove(s);
            }
            else
            {
                stu_matched.selections.remove(s);
            }
        }
        for(String st:stu_not_matched.selections)
        {
            for(String t: ins_not_matched.selections)
            {
                PlainSelect edited = stu.clone();
                edited.selections.remove(st);
                edited.selections.add(t);
                res.add(new Pair<>(edited,EditCostConfig.selections));
            }
        }
        return res;
    }
}
