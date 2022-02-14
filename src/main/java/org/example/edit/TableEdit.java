package org.example.edit;

import javafx.util.Pair;
import org.example.CalculateScore;
import org.example.SingleEdit;
import org.example.enums.SetOp;
import org.example.node.expr.Expr;
import org.example.node.select.PlainSelect;
import org.example.node.select.Select;
import org.example.node.select.SetOpSelect;
import org.example.node.table.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shenyichen
 * @date 2021/12/10
 **/
public class TableEdit implements Edit {
    @Override
    public List<Pair<PlainSelect, Float>> add(PlainSelect instr, PlainSelect stu) {
        List<Pair<PlainSelect,Float>> res = new ArrayList<>();
        List<Table> stu_clone = new ArrayList<>(stu.from.tables);
        for (Table item: instr.from.tables) {
            Table match = Table.isIn(item, stu_clone);
            if (match == null) {
                PlainSelect edited = stu.clone();
                edited.from.tables.add(item);
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
        List<Table> stu_clone = new ArrayList<>(stu.from.tables);
        for (Table item: instr.from.tables) {
            Table match = Table.isIn(item, stu_clone);
            if (match != null) {
                stu_clone.remove(match);
            }
        }
        for (Table item: stu_clone) {
            PlainSelect edited = stu.clone();
            edited.from.tables.remove(item);
            res.add(new Pair<>(edited, item.score() * CostConfig.delete_cost_rate));
        }
        return res;
    }

    @Override
    public List<Pair<PlainSelect, Float>> edit(PlainSelect instr, PlainSelect stu) {
        List<Pair<PlainSelect,Float>> res = new ArrayList <>();
        List<Table> stu_clone = new ArrayList<>(stu.from.tables);
        for (Table item: instr.from.tables) {
            Table match = Table.isIn(item, stu_clone);
            if (match != null) {
                stu_clone.remove(match);
                if (!(item.equals(match))) {
                    float cost;
                    // subQuery
                    if (item instanceof Select && match instanceof Select) {
                        Select item_s = (Select) item;
                        Select match_s = (Select) match;
                        float totalScore = CalculateScore.totalScore(item_s);
                        cost = totalScore - CalculateScore.editScore(item_s, match_s, totalScore);
                    }
                    // 普通table
                    else {
                        cost = item.score() - item.score(match);
                    }
                    PlainSelect edited = stu.clone();
                    edited.from.tables.add(item);
                    edited.from.tables.remove(match);
                    res.add(new Pair<>(edited, cost));
                }
            }
        }
        return res;
    }
}
