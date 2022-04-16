package org.example.edit;

import javafx.util.Pair;
import org.example.Env;
import org.example.node.expr.Expr;
import org.example.node.orderby.OrderByItem;
import org.example.node.select.PlainSelect;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shenyichen
 * @date 2022/2/6
 **/
public class OrderByEdit implements Edit {
    @Override
    public List<Pair<PlainSelect, Float>> add(PlainSelect instr, PlainSelect stu, Env env) {
        List<Pair<PlainSelect,Float>> res = new ArrayList<>();
        List<OrderByItem> items_clone = new ArrayList<>(stu.orderBy.items);
        for (OrderByItem e: instr.orderBy.items) {
            OrderByItem tmp = OrderByItem.isIn(e,items_clone);
            if (tmp == null) {
                PlainSelect edited = stu.clone();
                edited.orderBy.items.add(e.clone());
                res.add(new Pair<>(edited, e.score()));
            } else {
                items_clone.remove(tmp);
            }
        }
        return res;
    }

    @Override
    public List<Pair<PlainSelect, Float>> remove(PlainSelect instr, PlainSelect stu, Env env) {
        List<Pair<PlainSelect,Float>> res = new ArrayList<>();
        List<OrderByItem> items_clone = new ArrayList<>(stu.orderBy.items);
        for (OrderByItem e: instr.orderBy.items) {
            OrderByItem tmp = OrderByItem.isIn(e,items_clone);
            if (tmp != null) {
                items_clone.remove(tmp);
            }
        }
        for (OrderByItem e: items_clone) {
            PlainSelect edited = stu.clone();
            edited.orderBy.items.remove(e);
            res.add(new Pair<>(edited, e.score() * CostConfig.delete_cost_rate));
        }
        return res;
    }

    @Override
    public List<Pair<PlainSelect, Float>> edit(PlainSelect instr, PlainSelect stu, Env env) {
        List<Pair<PlainSelect,Float>> res = new ArrayList<>();
        boolean sameItems = true;
        List<OrderByItem> items_clone = new ArrayList<>(stu.orderBy.items);
        for (OrderByItem e: instr.orderBy.items) {
            OrderByItem tmp = OrderByItem.isIn(e,items_clone);
            if (tmp != null) {
                items_clone.remove(tmp);
                if (!(e.equals(tmp))) {
                    sameItems = false;
                    PlainSelect edited = stu.clone();
                    edited.orderBy.items.remove(tmp);
                    edited.orderBy.items.add(e);
                    res.add(new Pair<>(edited, e.score() - e.score(tmp)));
                }
            }
        }
        sameItems &= (items_clone.size() == 0);
        // items 都一样，可以调整顺序
        if (sameItems) {
            float cost = 0;
            int idx = -1;
            items_clone = new ArrayList<>(stu.orderBy.items);
            for (OrderByItem e: instr.orderBy.items) {
                OrderByItem tmp = OrderByItem.isIn(e,items_clone);
                if (tmp != null) {
                    items_clone.remove(tmp);
                    int curIdx = stu.orderBy.items.indexOf(tmp);
                    if (curIdx < idx)
                        cost += CostConfig.sequence_penalty;
                    idx = curIdx;
                }
            }
            if (cost != 0) {
                PlainSelect edited = stu.clone();
                edited.orderBy = instr.orderBy.clone();
                res.add(new Pair<>(edited, cost));
            }
        }
        return res;
    }

    @Override
    public List<String> hintAdd(PlainSelect now, PlainSelect prev, Env env) {
        List<String> res = new ArrayList<>();
        List<OrderByItem> items_clone = new ArrayList<>(prev.orderBy.items);
        for (OrderByItem e: now.orderBy.items) {
            OrderByItem tmp = OrderByItem.isIn(e,items_clone);
            if (tmp == null) {
                res.add("请尝试在orderBy语句中增加" + e.toString());
            } else {
                items_clone.remove(tmp);
            }
        }
        return res;
    }

    @Override
    public List<String> hintRemove(PlainSelect now, PlainSelect prev, Env env) {
        List<String> res = new ArrayList<>();
        List<OrderByItem> items_clone = new ArrayList<>(prev.orderBy.items);
        for (OrderByItem e: now.orderBy.items) {
            OrderByItem tmp = OrderByItem.isIn(e,items_clone);
            if (tmp != null) {
                items_clone.remove(tmp);
            }
        }
        for (OrderByItem e: items_clone) {
            res.add("请尝试在orderBy语句中删去" + e.column.originStr);
        }
        return res;
    }

    @Override
    public List<String> hintEdit(PlainSelect now, PlainSelect prev, Env env) throws Exception {
        List<String> res = new ArrayList<>();
        boolean sameItems = true;
        List<OrderByItem> items_clone = new ArrayList<>(prev.orderBy.items);
        for (OrderByItem e: now.orderBy.items) {
            OrderByItem tmp = OrderByItem.isIn(e,items_clone);
            if (tmp != null) {
                items_clone.remove(tmp);
                if (!(e.equals(tmp))) {
                    sameItems = false;
                    res.add("请尝试在orderBy语句中将" + tmp.column.originStr + " " + tmp.order + "改为" + e.toString());
                }
            }
        }
        sameItems &= (items_clone.size() == 0);
        // items 都一样，可以调整顺序
        if (sameItems) {
            float cost = 0;
            int idx = -1;
            items_clone = new ArrayList<>(prev.orderBy.items);
            for (OrderByItem e: now.orderBy.items) {
                OrderByItem tmp = OrderByItem.isIn(e,items_clone);
                if (tmp != null) {
                    items_clone.remove(tmp);
                    int curIdx = prev.orderBy.items.indexOf(tmp);
                    if (curIdx < idx)
                        cost += CostConfig.sequence_penalty;
                    idx = curIdx;
                }
            }
            if (cost != 0) {
                res.add("请尝试调整orderBy语句中各项的顺序");
            }
        }
        return res;
    }
}
