package org.example;

import javafx.util.Pair;
import org.example.edit.ConditionEdit;
import org.example.edit.SelectionEdit;
import org.example.edit.TableEdit;
import org.example.node.orderby.OrderByItem;
import org.example.node.select.PlainSelect;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shenyichen
 * @date 2021/12/9
 **/
public class SingleEdit {

    public static List<Pair<PlainSelect,Float>> singleEdit(PlainSelect instr, PlainSelect student) throws CloneNotSupportedException {
        List<Pair<PlainSelect,Float>> edits = new ArrayList<>();
        edits.addAll(editDistinct(instr, student));
        edits.addAll(editSelections(instr, student));
        edits.addAll(editTables(instr, student));
        edits.addAll(editConditions(instr, student));
//        edits.addAll(editSubQs(instr, student));
        edits.addAll(editGroupBy(instr, student));
        edits.addAll(editOrderBy(instr, student));
        edits.addAll(editLimit(instr, student));
        for (Pair<PlainSelect,Float> edit: edits){
            Canonicalizer.canonicalize(edit.getKey());
        }
        return edits;
    }

    public static List<Pair<PlainSelect,Float>> editDistinct(PlainSelect instr, PlainSelect student) throws CloneNotSupportedException {
        List<Pair<PlainSelect,Float>> edits = new ArrayList<>();
        if (instr.distinct!=student.distinct){
            PlainSelect edited = student.clone();
            edited.distinct = instr.distinct;
            edits.add(new Pair<>(edited,1.0f));
        }
        return edits;
    }

    public static List<Pair<PlainSelect,Float>> editSelections(PlainSelect instr, PlainSelect student) throws CloneNotSupportedException {
        List<Pair<PlainSelect,Float>> edits = new ArrayList<>();

        SelectionEdit edit = new SelectionEdit();
        edits.addAll(edit.add(instr,student));
        edits.addAll(edit.remove(instr, student));
        edits.addAll(edit.edit(instr, student));

        return edits;
    }

    public static List<Pair<PlainSelect,Float>> editTables(PlainSelect instr, PlainSelect student) throws CloneNotSupportedException {
        List<Pair<PlainSelect,Float>> edits = new ArrayList<>();

        TableEdit tableEdit = new TableEdit();
        edits.addAll(tableEdit.add(instr, student));
        edits.addAll(tableEdit.remove(instr, student));
        edits.addAll(tableEdit.edit(instr, student));

        return edits;
    }

    public static List<Pair<PlainSelect,Float>> editConditions(PlainSelect instr, PlainSelect student) throws CloneNotSupportedException {
        List<Pair<PlainSelect,Float>> edits = new ArrayList<>();

        ConditionEdit conditionEdit = new ConditionEdit();
        edits.addAll(conditionEdit.add(instr, student));
        edits.addAll(conditionEdit.remove(instr, student));
        edits.addAll(conditionEdit.edit(instr, student));

        return edits;
    }

//    public static List<Pair<PlainSelect,Float>> editSubQs(PlainSelect instr, PlainSelect student) throws CloneNotSupportedException {
//        List<Pair<PlainSelect,Float>> edits = new ArrayList<>();
//
//        SubQEdit subQEdit = new SubQEdit();
//        edits.addAll(subQEdit.add(instr, student));
//        edits.addAll(subQEdit.remove(instr, student));
//        edits.addAll(subQEdit.edit(instr, student));
//
//        return edits;
//    }

    public static List<Pair<PlainSelect,Float>> editGroupBy(PlainSelect instr, PlainSelect student) throws CloneNotSupportedException {
        // TODO: 改成xdata那种
        List<Pair<PlainSelect,Float>> edits = new ArrayList<>();
        float cost = CalculateScore.calculateGroupByCost(instr.groupBy,student.groupBy);
        if (cost>0){
            PlainSelect edited = student.clone();
            edited.groupBy = instr.groupBy;
            edits.add(new Pair<>(edited,cost));
        }
        return edits;
    }

    public static List<Pair<PlainSelect,Float>> editOrderBy(PlainSelect instr, PlainSelect student) throws CloneNotSupportedException {
        List<Pair<PlainSelect,Float>> edits = new ArrayList<>();
        float cost = 0.0f;
        for (OrderByItem item: instr.orderBy.items){
            cost += getOrderByItemCost(item,student.orderBy.items);
        }
        if (cost>0){
            PlainSelect edited = student.clone();
            edited.orderBy = instr.orderBy;
            edits.add(new Pair<>(edited,cost));
        }
        return edits;
    }

    public static float getOrderByItemCost(OrderByItem instr, List<OrderByItem> items){
        float cost = 2; // 没找到
        for (OrderByItem item: items){
            if (instr.column.equals(item.column)){
                cost = 0;
                if (!(instr.order.equals(item.order)))
                    cost += 1;
                break;
            }
        }
        return cost;
    }

    public static List<Pair<PlainSelect,Float>> editLimit(PlainSelect instr, PlainSelect student) throws CloneNotSupportedException {
        List<Pair<PlainSelect,Float>> edits = new ArrayList<>();
        PlainSelect edited = student.clone();
        float cost = 0.0f;
        if (instr.limit.rowCount!=student.limit.rowCount){
            edited.limit.rowCount = instr.limit.rowCount;
            cost += 1;
        }
        if (instr.limit.offset!=student.limit.offset){
            edited.limit.offset = instr.limit.offset;
            cost += 1;
        }
        if (cost>0){
            edits.add(new Pair<>(edited,cost));
        }
        return edits;
    }
}
