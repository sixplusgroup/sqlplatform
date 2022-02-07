package org.example.edit;

import javafx.util.Pair;
import org.example.CalculateScore;
import org.example.node.expr.Expr;
import org.example.node.select.PlainSelect;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shenyichen
 * @date 2021/12/10
 **/
public class GroupByEdit implements Edit {

    @Override
    public List<Pair<PlainSelect, Float>> add(PlainSelect instr, PlainSelect stu) {
        List<Pair<PlainSelect,Float>> res = new ArrayList<>();
        // items
        List<Expr> items_clone = new ArrayList<>(stu.groupBy.items);
        for (Expr e: instr.groupBy.items) {
            Expr tmp = Expr.isIn(e,items_clone);
            if (tmp == null) {
                PlainSelect edited = stu.clone();
                edited.groupBy.items.add(e.clone());
                res.add(new Pair<>(edited, e.score()));
            } else {
                items_clone.remove(tmp);
            }
        }
        // having
        if (stu.groupBy.having == null && instr.groupBy.having != null) {
            PlainSelect edited = stu.clone();
            edited.groupBy.having = instr.groupBy.having.clone();
            res.add(new Pair<>(edited, instr.groupBy.having.score()));
        }
        return res;
    }

    @Override
    public List<Pair<PlainSelect, Float>> remove(PlainSelect instr, PlainSelect stu) {
        List<Pair<PlainSelect,Float>> res = new ArrayList<>();
        // items
        List<Expr> items_clone = new ArrayList<>(stu.groupBy.items);
        for (Expr e: instr.groupBy.items) {
            Expr tmp = Expr.isIn(e,items_clone);
            if (tmp != null) {
                items_clone.remove(tmp);
            }
        }
        for (Expr e: items_clone) {
            PlainSelect edited = stu.clone();
            edited.groupBy.items.remove(e);
            res.add(new Pair<>(edited, e.score() * CostConfig.delete_cost_rate));
        }
        // having
        if (instr.groupBy.having == null && stu.groupBy.having != null) {
            PlainSelect edited = stu.clone();
            edited.groupBy.having = null;
            res.add(new Pair<>(edited, stu.groupBy.having.score() * CostConfig.delete_cost_rate));
        }
        return res;
    }

    @Override
    public List<Pair<PlainSelect, Float>> edit(PlainSelect instr, PlainSelect stu) {
        List<Pair<PlainSelect,Float>> res = new ArrayList<>();
        // items
        boolean sameItems = true;
        List<Expr> items_clone = new ArrayList<>(stu.groupBy.items);
        for (Expr e: instr.groupBy.items) {
            Expr tmp = Expr.isIn(e,items_clone);
            if (tmp != null) {
                items_clone.remove(tmp);
                if (!(e.equals(tmp))) {
                    sameItems = false;
                    PlainSelect edited = stu.clone();
                    edited.groupBy.items.remove(tmp);
                    edited.groupBy.items.add(e);
                    res.add(new Pair<>(edited, e.score() - e.score(tmp)));
                }
            }
        }
        sameItems &= (items_clone.size() == 0);
        // items 都一样，可以调整顺序
        if (sameItems) {
            float cost = 0;
            int idx = -1;
            items_clone = new ArrayList<>(stu.groupBy.items);
            for (Expr e: instr.groupBy.items) {
                Expr tmp = Expr.isIn(e,items_clone);
                if (tmp != null) {
                    items_clone.remove(tmp);
                    int curIdx = stu.groupBy.items.indexOf(tmp);
                    if (curIdx < idx)
                        cost += CostConfig.sequence_penalty;
                    idx = curIdx;
                }
            }
            PlainSelect edited = stu.clone();
            edited.groupBy.items = instr.groupBy.items.stream()
                    .map(Expr::clone)
                    .collect(Collectors.toList());
            res.add(new Pair<>(edited, cost));
        }
        // having
        if (instr.groupBy.having != null && stu.groupBy.having != null) {
            int distance = CalculateScore.editDistance(instr.groupBy.having.toString(), stu.groupBy.having.toString());
            PlainSelect edited = stu.clone();
            edited.groupBy.having = instr.groupBy.having.clone();
            res.add(new Pair<>(edited, (float) (distance / CostConfig.other_digits)));
        }
        return res;
    }
}
