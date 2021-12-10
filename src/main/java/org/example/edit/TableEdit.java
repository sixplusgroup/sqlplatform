package org.example.edit;

import javafx.util.Pair;
import org.example.node.PlainSelect;
import org.example.node.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shenyichen
 * @date 2021/12/10
 **/
public class TableEdit implements Edit {
    @Override
    public List<Pair<PlainSelect, Float>> add(PlainSelect instr, PlainSelect stu) throws CloneNotSupportedException {
        List<Pair<PlainSelect,Float>> res = new ArrayList<Pair<PlainSelect,Float>>();
        for(Table t:instr.from.tables)
        {
            if(!stu.from.tables.contains(t))
            {
                PlainSelect edited = stu.clone();
                edited.from.tables.add(t);
                res.add(new Pair<>(edited,EditCostConfig.tables));
            }
        }
        return res;
    }

    @Override
    public List<Pair<PlainSelect, Float>> remove(PlainSelect instr, PlainSelect stu) throws CloneNotSupportedException {
        List<Pair<PlainSelect,Float>> res = new ArrayList<>();
        for(Table s:stu.from.tables)
        {
            if(!instr.from.tables.contains(s))
            {
                PlainSelect edited = stu.clone();
                edited.from.tables.remove(s);
                res.add(new Pair<>(edited,EditCostConfig.tables/2));
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
        for(Table s:instr.from.tables)
        {
            if(stu.from.tables.contains(s))
            {
                ins_not_matched.from.tables.remove(s);
            }
        }
        for(Table s:stu.from.tables)
        {
            if(instr.from.tables.contains(s))
            {
                stu_not_matched.from.tables.remove(s);
            }
            else
            {
                stu_matched.from.tables.remove(s);
            }
        }
        for(Table st:stu_not_matched.from.tables)
        {
            for(Table t: ins_not_matched.from.tables)
            {
                PlainSelect edited = stu.clone();
                edited.from.tables.remove(st);
                edited.from.tables.add(t);
                res.add(new Pair<>(edited,EditCostConfig.tables));
            }
        }
        return res;
    }
}
