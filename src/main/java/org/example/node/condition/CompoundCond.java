package org.example.node.condition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author shenyichen
 * @date 2022/1/17
 **/
public class CompoundCond extends Condition {

    public List<Condition> subConds;

    public CompoundCond() {
        super();
        subConds = new ArrayList<>();
    }

    public CompoundCond(String operator, List<Condition> conditions) {
        super();
        this.operator = operator;
        this.subConds = new ArrayList<>();
        this.subConds.addAll(conditions.stream()
        .filter(Objects::nonNull)
        .collect(Collectors.toList()));
    }

    public CompoundCond(boolean not, String operator, List<Condition> conditions) {
        this.not = not;
        this.operator = operator;
        this.subConds = new ArrayList<>();
        if (conditions!=null){
            this.subConds.addAll(conditions.stream()
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList()));
        }
    }

    @Override
    public Condition rearrange() {
        List<Condition> subConds_new = new ArrayList<>();
        for (Condition c: subConds){
            subConds_new.add(c.rearrange());
        }
        subConds = subConds_new;
        mergeNot();
        Condition c = merge();
        if (c instanceof CompoundCond){
            ((CompoundCond)c).flatten();
        }
        return c;
    }

    @Override
    public float score() {
        return 0;
    }

    @Override
    public float score(Condition c) {
        return 0;
    }

    /**
     * 合并not
     */
    public void mergeNot(){
        boolean flag = true;
        for (Condition c: subConds){
            if (c instanceof CompoundCond){
                ((CompoundCond) c).mergeNot();
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
        List<Condition> subConds_clone = new ArrayList<>(subConds);
        for (Condition tmp: subConds_clone){
            if (tmp instanceof CompoundCond){
                ((CompoundCond) tmp).flatten();
                if (not==tmp.not && tmp.operator.equals(operator)){
                    subConds.addAll(((CompoundCond) tmp).subConds);
                    subConds.remove(tmp);
                }
            }
        }
    }

    public boolean isIn(Condition c,List<Condition> l){
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


    @Override
    public Condition clone() {
        List<Condition> subConds_clone = subConds.stream()
                .map(Condition::clone)
                .collect(Collectors.toList());
        return new CompoundCond(not,operator,subConds_clone);
    }

    @Override
    public int hashCode() {
        return operator.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof CompoundCond))
            return false;
        CompoundCond c = (CompoundCond) obj;
        if (c.not!=not)
            return false;
        if (!(c.operator.equals(operator)))
            return false;
        if (c.subConds.size()!=subConds.size())
            return false;
        for (Condition tmp: subConds) {
            if (!isIn(tmp,c.subConds))
                return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return null;
    }

    public static void main(String[] args) {
        CompoundCond c = new CompoundCond(false,"AND",null);
        Condition c1 = c.clone();
        c.operator = "OR";
        System.out.println(c1.operator);
    }

}
