package org.example.node.condition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author shenyichen
 * @date 2022/1/17
 **/
public class CompoundCond extends Condition {

    public List<Condition> subConds;

    public CompoundCond() {
        super();
    }

    public CompoundCond(String operator, List<Condition> conditions) {
        super();
        this.operator = operator;
        this.subConds = new ArrayList<>();
        this.subConds.addAll(conditions);
    }

    @Override
    public Condition rearrange() {
        for (Condition c: subConds){
            c.rearrange();
        }
        mergeNot();
        Condition c = merge();
        if (c instanceof CompoundCond){
            ((CompoundCond)c).flatten();
        }
        return null;
    }

    /**
     * 合并not
     */
    public void mergeNot(){
        boolean flag = true;
        for (Condition c: subConds){
            if (c instanceof CompoundCond){
                mergeNot();
            }
            if (!c.not){
                flag = false;
                break;
            }
        }
        if (flag){
            not = !not;
        }
    }

    /**
     * 合并 e.g. (A and B) or (A and C) -> A and (B or c)
     */
    public Condition merge(){
        if (subConds.size()==0)
            return this;
        // 2.1 flag: 是否需要合并
        boolean flag = true;
        String op = subConds.get(0).operator;
        for (Condition c: subConds){
            if (c instanceof CompoundCond && c.not==not && c.operator.equals(op)) {
            }
            else{
                flag = false;
                break;
            }
        }
        if (flag){
            // 2.2 提取合并内容
            List<Condition> sameConds = ((CompoundCond)subConds.get(0)).subConds;
            for (int i=1;i<subConds.size();i++){
                List<Condition> tmpList =((CompoundCond)subConds.get(i)).subConds;
                sameConds.removeIf(c -> !isIn(c, tmpList));
            }
            // 2.3 合并
            Condition sameCond = null;
            if (sameConds.size()==0){
                flag = false;
            } else if (sameConds.size()==1){
                sameCond = sameConds.get(0);
            } else {
                sameCond = new CompoundCond(op,sameConds);
            }
            if (flag){
                CompoundCond differentCond = new CompoundCond(operator,new ArrayList<>());
                for (Condition tmp: subConds){
                    ((CompoundCond)tmp).subConds.removeIf(c -> isIn(c,sameConds));
                    Condition toAdd;
                    if (((CompoundCond)tmp).subConds.size()==0){
                        if (op.equals("AND") && operator.equals("AND")
                        || (op.equals("OR") && operator.equals("OR"))){
                        } else if ((op.equals("AND") && operator.equals("OR"))
                        || (op.equals("OR") && operator.equals("AND"))){
                            return sameCond;
                        }
                    } else if (((CompoundCond)tmp).subConds.size()==1){
                        differentCond.add(((CompoundCond)tmp).subConds.get(0));
                    } else {
                        differentCond.add(tmp);
                    }
                }
                return new CompoundCond(op, Arrays.asList(sameCond,differentCond));
            }
        }
        return this;
    }

    /**
     * 展平：e.g. A and B and C 按 and 展平
     */
    public void flatten(){
        for (Condition tmp: subConds){
            if (tmp instanceof CompoundCond){
                ((CompoundCond) tmp).flatten();
                if (not==tmp.not && tmp.operator.equals(operator)){
                    subConds.addAll(((CompoundCond) tmp).subConds);
                    subConds.remove(tmp);
                }
            }
        }
    }

    private boolean isIn(Condition c,List<Condition> l){
        boolean flag = false;
        for (Condition tmp: l){
            if (c.equals(tmp)){
                flag = true;
                break;
            }
        }
        return flag;
    }

    public void add(Condition c){
        subConds.add(c);
    }

}
