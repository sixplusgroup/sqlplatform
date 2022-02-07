package org.example.edit;

import javafx.util.Pair;
import org.example.node.expr.Expr;
import org.example.node.select.PlainSelect;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shenyichen
 * @date 2021/12/10
 **/
public class SelectionEdit implements Edit {
    @Override
    public List<Pair<PlainSelect, Float>> add(PlainSelect instr, PlainSelect stu) {
        List<Pair<PlainSelect,Float>> res = new ArrayList<>();
        List<Expr> stu_clone = new ArrayList<>(stu.selections);
        for (Expr item: instr.selections) {
            Expr match = Expr.isIn(item, stu_clone);
            if (match == null) {
                PlainSelect edited = stu.clone();
                edited.selections.add(item);
                res.add(new Pair<>(edited, item.score()));
            } else {
                stu_clone.remove(match);
            }
        }
        return res;
    }

    @Override
    public List<Pair<PlainSelect, Float>> remove(PlainSelect instr, PlainSelect stu) {
        List<Pair<PlainSelect,Float>> res = new ArrayList<>();
        List<Expr> stu_clone = new ArrayList<>(stu.selections);
        for (Expr item: instr.selections) {
            Expr match = Expr.isIn(item, stu_clone);
            if (match != null) {
                stu_clone.remove(match);
            }
        }
        for (Expr item: stu_clone) {
            PlainSelect edited = stu.clone();
            edited.selections.remove(item);
            res.add(new Pair<>(edited, item.score() * CostConfig.delete_cost_rate));
        }
        return res;
    }

    @Override
    public List<Pair<PlainSelect, Float>> edit(PlainSelect instr, PlainSelect stu) {
        List<Pair<PlainSelect,Float>> res = new ArrayList <>();
        List<Expr> stu_clone = new ArrayList<>(stu.selections);
        for (Expr item: instr.selections) {
            Expr match = Expr.isIn(item, stu_clone);
            if (match != null) {
                stu_clone.remove(match);
                if (!(item.equals(match))) {
                    PlainSelect edited = stu.clone();
                    edited.selections.add(item);
                    edited.selections.remove(match);
                    res.add(new Pair<>(edited, item.score() - item.score(match)));
                }
            }
        }
        return res;
    }
}
