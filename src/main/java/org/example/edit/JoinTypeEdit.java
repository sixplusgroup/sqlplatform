package org.example.edit;

import javafx.util.Pair;
import org.example.node.select.PlainSelect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shenyichen
 * @date 2022/2/4
 **/
public class JoinTypeEdit {

    public Pair<PlainSelect, Float> typeEdit(PlainSelect instr, PlainSelect stu) {
        HashMap<String, Integer> types_clone = new HashMap<>(stu.from.joinTypes);
        for (Map.Entry<String, Integer> item: instr.from.joinTypes.entrySet()) {
            String key = item.getKey();
            if (types_clone.containsKey(key)) {
                types_clone.put(key, types_clone.get(key) - item.getValue());
            } else {
                types_clone.put(key, -item.getValue());
            }
        }
        int toAdd = 0, toDel = 0;
        for (Integer num: types_clone.values()) {
            if (num > 0) {
                toDel += num;
            } else if (num < 0) {
                toAdd += -num;
            }
        }
        int editNum = Math.min(toAdd, toDel);
        float cost = editNum;
        if (toAdd > editNum) {
            cost += toAdd;
        } else if (toDel > editNum) {
            cost += toDel * CostConfig.delete_cost_rate;
        }
        PlainSelect edited = stu.clone();
        edited.from.joinTypes = instr.from.joinTypes;
        return new Pair<>(edited, cost);
    }
}