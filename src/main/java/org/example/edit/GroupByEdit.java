package org.example.edit;

import javafx.util.Pair;
import org.example.CalculateScore;
import org.example.Env;
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
    public List<Pair<PlainSelect, Float>> add(PlainSelect instr, PlainSelect stu, Env env) {
        List<Pair<PlainSelect,Float>> res = new ArrayList<>();
        // items
        List<Expr> instr_clone = new ArrayList<>(instr.groupBy.items);
        Pair<List<Expr>, List<Expr>> matches = Expr.getMatches(instr.groupBy.items, stu.groupBy.items);
        instr_clone.removeAll(matches.getKey());
        for (Expr e: instr_clone) {
            PlainSelect edited = stu.clone();
            edited.groupBy.items.add(e.clone());
            res.add(new Pair<>(edited, e.score()));
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
    public List<Pair<PlainSelect, Float>> remove(PlainSelect instr, PlainSelect stu, Env env) {
        List<Pair<PlainSelect,Float>> res = new ArrayList<>();
        // items
        List<Expr> stu_clone = new ArrayList<>(stu.groupBy.items);
        Pair<List<Expr>, List<Expr>> matches = Expr.getMatches(instr.groupBy.items, stu.groupBy.items);
        stu_clone.removeAll(matches.getValue());
        for (Expr e: stu_clone) {
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
    public List<Pair<PlainSelect, Float>> edit(PlainSelect instr, PlainSelect stu, Env env) {
        List<Pair<PlainSelect,Float>> res = new ArrayList<>();
        // items
        boolean sameItems = true;
        Pair<List<Expr>, List<Expr>> matches = Expr.getMatches(instr.groupBy.items, stu.groupBy.items);
        List<Expr> match_instr = matches.getKey();
        List<Expr> match_stu = matches.getValue();
        for (int i=0; i<match_instr.size(); i++) {
            Expr item = match_instr.get(i);
            Expr match = match_stu.get(i);
            if (!(match.equals(item))) {
                sameItems = false;
                PlainSelect edited = stu.clone();
                edited.groupBy.items.remove(match);
                edited.groupBy.items.add(item);
                res.add(new Pair<>(edited, item.score() - item.score(match)));
            }
        }
        sameItems &= (instr.groupBy.items.size() == match_instr.size());
        sameItems &= (stu.groupBy.items.size() == match_instr.size());
        // items 都一样，可以调整顺序
        if (sameItems) {
            float cost = 0;
            int idx = -1;
            List<Expr> items_clone = new ArrayList<>(stu.groupBy.items);
            for (Expr e: instr.groupBy.items) {
                items_clone.remove(e);
                int curIdx = stu.groupBy.items.indexOf(e);
                if (curIdx < idx)
                    cost += CostConfig.sequence_penalty;
                idx = curIdx;
            }
            if (cost != 0) {
                PlainSelect edited = stu.clone();
                edited.groupBy.items = instr.groupBy.items.stream()
                        .map(Expr::clone)
                        .collect(Collectors.toList());
                res.add(new Pair<>(edited, cost));
            }
        }
        // having
        if (instr.groupBy.having != null && stu.groupBy.having != null) {
            int distance = CalculateScore.editDistance(instr.groupBy.having.toString(), stu.groupBy.having.toString());
            if (distance > 0) {
                PlainSelect edited = stu.clone();
                edited.groupBy.having = instr.groupBy.having.clone();
                res.add(new Pair<>(edited, (float) (distance * 1.0f / CostConfig.other_digits)));
            }
        }
        return res;
    }
}
