package org.example.edit;

import javafx.util.Pair;
import org.example.node.select.PlainSelect;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shenyichen
 * @date 2021/12/10
 **/
public class GroupByEdit implements Edit {

    @Override
    public List<Pair<PlainSelect, Float>> add(PlainSelect instr, PlainSelect stu) {
        return null;
    }

    @Override
    public List<Pair<PlainSelect, Float>> remove(PlainSelect instr, PlainSelect stu) {
        List<Pair<PlainSelect,Float>> a = new ArrayList<Pair<PlainSelect,Float>>();
//        for(GroupByItem t:stu.groupBy.items)
//        {
//            List<String> list = instr.groupBy.items
//            if(!.contains(t))
//            {
//                QueryStructure temp = (QueryStructure)Utilities.copy(student);
//                temp.getLstGroupByNodes().remove(t);
//                Pair<QueryStructure,Float> tempCost= new Pair<QueryStructure,Float> ();
//                tempCost.setFirst(temp);
//                tempCost.setSecond((float) EditWeightConfig.groupby);
//                a.add(tempCost);
//            }
//        }

        return a;
    }

    @Override
    public List<Pair<PlainSelect, Float>> edit(PlainSelect instr, PlainSelect stu) {
        return null;
    }
}
