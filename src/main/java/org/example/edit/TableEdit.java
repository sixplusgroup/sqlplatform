package org.example.edit;

import javafx.util.Pair;
import org.example.CalculateScore;
import org.example.Env;
import org.example.SingleEdit;
import org.example.enums.SetOp;
import org.example.node.expr.Expr;
import org.example.node.select.PlainSelect;
import org.example.node.select.Select;
import org.example.node.select.SetOpSelect;
import org.example.node.table.PlainTable;
import org.example.node.table.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shenyichen
 * @date 2021/12/10
 **/
public class TableEdit implements Edit {
    @Override
    public List<Pair<PlainSelect, Float>> add(PlainSelect instr, PlainSelect stu, Env env) {
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
    public List<Pair<PlainSelect, Float>> remove(PlainSelect instr, PlainSelect stu, Env env) {
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
    public List<Pair<PlainSelect, Float>> edit(PlainSelect instr, PlainSelect stu, Env env) throws Exception {
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
                        cost = totalScore - CalculateScore.editScore(item_s, match_s, totalScore, env);
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

    @Override
    public List<String> hintAdd(PlainSelect now, PlainSelect prev, Env env) {
        List<String> res = new ArrayList<>();
        List<Table> stu_clone = new ArrayList<>(prev.from.tables);
        for (Table item: now.from.tables) {
            Table match = Table.isIn(item, stu_clone);
            if (match == null) {
                if (item instanceof PlainTable)
                    res.add("请尝试在from语句中增加表" + item.toString());
                else if (item instanceof Select)
                    res.add("请尝试在from语句中增加subquery作为表");
                else
                    res.add("请尝试在from语句中增加表");
            } else {
                stu_clone.remove(match);
            }
        }
        return res;
    }

    @Override
    public List<String> hintRemove(PlainSelect now, PlainSelect prev, Env env) {
        List<String> res = new ArrayList<>();
        List<Table> stu_clone = new ArrayList<>(prev.from.tables);
        for (Table item: now.from.tables) {
            Table match = Table.isIn(item, stu_clone);
            if (match != null) {
                stu_clone.remove(match);
            }
        }
        for (Table item: stu_clone) {
            res.add("请尝试在from语句中删除" + item.originStr);
        }
        return res;
    }

    @Override
    public List<String> hintEdit(PlainSelect now, PlainSelect prev, Env env) throws Exception {
        List<String> res = new ArrayList<>();
        List<Table> stu_clone = new ArrayList<>(prev.from.tables);
        for (Table item: now.from.tables) {
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
                        res.addAll(CalculateScore.hintsFromEdits(item_s, match_s, totalScore, env));
                    }
                    // 普通table
                    else {
                        res.add("请修改from语句中的表" + match.originStr);
                    }
                }
            }
        }
        return res;
    }
}
