package org.example.edit;

import javafx.util.Pair;
import org.example.Env;
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
    public List<Pair<PlainSelect, Float>> add(PlainSelect instr, PlainSelect stu, Env env) {
        List<Pair<PlainSelect,Float>> res = new ArrayList<>();
        List<Expr> instr_clone = new ArrayList<>(instr.selections);
        Pair<List<Expr>, List<Expr>> matches = Expr.getMatches(instr.selections, stu.selections);
        instr_clone.removeAll(matches.getKey());
        for (Expr e: instr_clone) {
            PlainSelect edited = stu.clone();
            edited.selections.add(e);
            res.add(new Pair<>(edited, e.score()));
        }
        return res;
    }

    @Override
    public List<Pair<PlainSelect, Float>> remove(PlainSelect instr, PlainSelect stu, Env env) {
        List<Pair<PlainSelect,Float>> res = new ArrayList<>();
        List<Expr> stu_clone = new ArrayList<>(stu.selections);
        Pair<List<Expr>, List<Expr>> matches = Expr.getMatches(instr.selections, stu.selections);
        stu_clone.removeAll(matches.getValue());
        for (Expr e: stu_clone) {
            PlainSelect edited = stu.clone();
            edited.selections.remove(e);
            res.add(new Pair<>(edited, e.score() * CostConfig.delete_cost_rate));
        }
        return res;
    }

    @Override
    public List<Pair<PlainSelect, Float>> edit(PlainSelect instr, PlainSelect stu, Env env) {
        List<Pair<PlainSelect,Float>> res = new ArrayList <>();
        Pair<List<Expr>, List<Expr>> matches = Expr.getMatches(instr.selections, stu.selections);
        List<Expr> match_instr = matches.getKey();
        List<Expr> match_stu = matches.getValue();
        for (int i=0; i<match_instr.size(); i++) {
            Expr item = match_instr.get(i);
            Expr match = match_stu.get(i);
            if (!(match.equals(item))) {
                PlainSelect edited = stu.clone();
                edited.selections.add(item);
                edited.selections.remove(match);
                res.add(new Pair<>(edited, item.score() - item.score(match)));
            }
        }
        return res;
    }

    @Override
    public List<String> hintAdd(PlainSelect now, PlainSelect prev, Env env) {
        List<String> res = new ArrayList<>();
        List<Expr> instr_clone = new ArrayList<>(now.selections);
        Pair<List<Expr>, List<Expr>> matches = Expr.getMatches(now.selections, prev.selections);
        instr_clone.removeAll(matches.getKey());
        if (instr_clone.size() > 0) {
            res.add("请尝试在selections中增加内容");
        }
        return res;
    }

    @Override
    public List<String> hintRemove(PlainSelect now, PlainSelect prev, Env env) {
        List<String> res = new ArrayList<>();
        List<Expr> stu_clone = new ArrayList<>(prev.selections);
        Pair<List<Expr>, List<Expr>> matches = Expr.getMatches(now.selections, prev.selections);
        stu_clone.removeAll(matches.getValue());
        for (Expr e: stu_clone) {
            res.add("请尝试在selections中删除" + e.toString());
        }
        return res;
    }

    @Override
    public List<String> hintEdit(PlainSelect now, PlainSelect prev, Env env) {
        List<String> res = new ArrayList<>();
        Pair<List<Expr>, List<Expr>> matches = Expr.getMatches(now.selections, prev.selections);
        List<Expr> match_instr = matches.getKey();
        List<Expr> match_stu = matches.getValue();
        for (int i=0; i<match_instr.size(); i++) {
            Expr item = match_instr.get(i);
            Expr match = match_stu.get(i);
            if (!(match.equals(item))) {
                res.add("请尝试在selections中修改" + match.toString());
            }
        }
        return res;
    }
}
