package org.example.node.condition;

import org.example.edit.CostConfig;
import org.example.node.expr.Expr;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author shenyichen
 * @date 2022/1/17
 **/
public class CompoundCond extends Condition {

    private List<Condition> subConds;

    public CompoundCond() {
        super();
        subConds = new ArrayList<>();
    }

    public CompoundCond(String operator, List<Condition> conditions) {
        super();
        this.father = null;
        this.operator = operator;
        this.subConds = new ArrayList<>();
        if (conditions != null){
            this.subConds.addAll(conditions.stream()
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList()));
        }
        for (Condition c: subConds) {
            c.father = this;
        }
    }

    public CompoundCond(boolean not, String operator, List<Condition> conditions) {
        this(operator, conditions);
        this.not = not;
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
            ((CompoundCond)c).flattenEquals();
            ((CompoundCond)c).flattemComparisons();
        }
        return c;
    }

    @Override
    public float score() {
        float score = (not ? CostConfig.not : 0) + CostConfig.logic_operator;
        for (Condition c: subConds) {
            score += c.score();
        }
        return score;
    }

    @Override
    public float score(Condition c) {
        if (c == null)
            return 0;
        // case 1: this includes e
        float score = 0.0f;
        Condition match = Condition.isIn(c,subConds);
        if (match != null) {
            score = match.score(c);
        }
        if (c instanceof CompoundCond) {
            CompoundCond cc = (CompoundCond) c;
            // case 2: this equals e
            float matchScore = 0;
            if (not) {
                if (cc.not)
                    matchScore += CostConfig.not;
            } else {
                if (cc.not)
                    matchScore -= CostConfig.not;
            }
            matchScore += operator.equals(cc.operator) ? CostConfig.logic_operator : 0;
            List<Condition> subConds_clone = new ArrayList<>(cc.subConds);
            for (Condition item: subConds) {
                match = Condition.isIn(item,subConds_clone);
                if (match != null) {
                    matchScore += item.score(match);
                    subConds_clone.remove(item);
                }
            }
            for (Condition item: subConds_clone) {
                matchScore -= item.score() * CostConfig.delete_cost_rate;
            }
            score = Math.max(score, matchScore);
            // case 3: e includes this
            match = Condition.isIn(this,cc.subConds);
            if (match != null) {
                matchScore = score(match) - (cc.score() - match.score()) * CostConfig.delete_cost_rate;
            }
            score = Math.max(score, matchScore);
        }
        return score;
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
                sameConds.removeIf(c -> !isStrictlyIn(c, tmpList));
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
                    ((CompoundCond)tmp).subConds.removeIf(c -> isStrictlyIn(c,sameConds));
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
     * 展平： e.g. A and B and C 按 and 展平
     */
    public void flatten() {
        List<Condition> subConds_clone = new ArrayList<>(subConds);
        for (Condition tmp: subConds_clone) {
            if (tmp instanceof CompoundCond) {
                ((CompoundCond) tmp).flatten();
                if (not==tmp.not && tmp.operator.equals(operator)) {
                    subConds.addAll(((CompoundCond) tmp).subConds);
                    subConds.remove(tmp);
                }
            }
        }
    }

    /**
     *  = 的展平: A=B and B=C 展平为 =(A, B, C)
     *  并查集解法
     */
    public void flattenEquals() {
        List<CommutativeCond> toFlatten = new ArrayList<>();
        List<Condition> subConds_clone = new ArrayList<>(subConds);
        for (Condition c: subConds_clone) {
            if (c instanceof CompoundCond) {
                ((CompoundCond) c).flattenEquals();
            }
            else if (c instanceof CommutativeCond && c.operator.equals("=")) {
                toFlatten.add((CommutativeCond) c);
                subConds.remove(c);
            }
        }
        subConds.addAll(flattenEquals(toFlatten));
    }

    /**
     * 并查集解法
     * @param l
     * @return
     */
    private List<CommutativeCond> flattenEquals(List<CommutativeCond> l) {
        // 内部类 UF
        class QuickFindUF {
            protected int[] id;

            public QuickFindUF(int N) {
                id = new int[N];
                for (int i = 0; i < N; i++) {
                    id[i] = i;
                }
            }

            public boolean connected(int p, int q) {
                return find(p) == find(q);
            }

            public int find(int p) {
                return id[p];
            }

            public void union(int p, int q) {
                int pID = find(p);
                int qID = find(q);
                if (pID == qID) {
                    return;
                }
                for (int i = 0; i < id.length; i++) {
                    if (id[i] == pID) {
                        id[i] = qID;
                    }
                }
            }

            public List<List<Integer>> getConnectives() {
                HashMap<Integer, List<Integer>> map = new HashMap<>();
                for (int i=0; i<id.length; i++) {
                    if (map.containsKey(id[i])) {
                        map.get(id[i]).add(i);
                    } else {
                        List<Integer> l = new ArrayList<>();
                        l.add(i);
                        map.put(id[i], l);
                    }
                }
                return new ArrayList<>(map.values());
            }
        }

        // 方法体
        // 罗列所有 operands
        List<Expr> exprs = new ArrayList<>();
        for (CommutativeCond cond: l) {
            for (Expr e: cond.operands) {
                if (! (exprs.contains(e))) {
                    exprs.add(e);
                }
            }
        }
        // 按 = 进行 union
        QuickFindUF uf = new QuickFindUF(exprs.size());
        for (CommutativeCond cond: l) {
            int p = exprs.indexOf(cond.operands.get(0));
            for (int i=1; i<cond.operands.size(); i++) {
                int q = exprs.indexOf(cond.operands.get(i));
                uf.union(p, q);
            }
        }
        // 按连通树划分得出结果
        List<CommutativeCond> res = new ArrayList<>();
        List<List<Integer>> connectives = uf.getConnectives();
        for (List<Integer> item: connectives) {
            List<Expr> operands = item.stream()
                    .map(exprs::get)
                    .collect(Collectors.toList());
            res.add(new CommutativeCond("=", operands));
        }
        return res;
    }

    /**
     * 处理 A=B and A>C 的规则化问题：
     * 解决：对A>C往上回溯，遇到AND，找里面的= （因为and展平了，or没用）
     */
    public void flattemComparisons() {
        for (Condition c: subConds) {
            if (c instanceof CompoundCond) {
                ((CompoundCond) c).flattemComparisons();
            }
            else if (c instanceof UncommutativeCond && isComparision(c.operator)) {
                UncommutativeCond uc = (UncommutativeCond) c;
                uc.left = findEqualExpr(uc.left, uc);
                uc.right = findEqualExpr(uc.right, uc);
            }
        }
    }

    private Expr findEqualExpr(Expr e, Condition c) {
        Condition p = c;
        while (p.father != null) {
            CompoundCond father = p.father;
            if (father.operator.equals("AND")) {
                for (Condition item: father.subConds) {
                    if (item instanceof CommutativeCond && item.operator.equals("=")
                            && Expr.isStrictlyIn(e, ((CommutativeCond) item).operands)) {
                        List<Expr> operands_clone = new ArrayList<>(((CommutativeCond) item).operands);
                        operands_clone.sort(Comparator.comparing(Expr::toString));
                        return operands_clone.get(0).clone();
                    }
                }
            }
        }
        return e;
    }

    private boolean isComparision(String operator) {
        return operator.equals(">") || operator.equals(">=")
                || operator.equals("<") || operator.equals("<=");
    }

    public boolean isStrictlyIn(Condition c, List<Condition> l) {
        boolean flag = false;
        for (Condition tmp: l) {
            if (c.equals(tmp)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    public void add(Condition c){
        subConds.add(c);
        c.father = this;
    }

    public void remove(Condition c) {
        subConds.remove(c);
    }

    public int size() {
        return subConds.size();
    }

    public Condition get(int i) {
        if (i < 0 || i >= size())
            return null;
        return subConds.get(i);
    }

    public List<Condition> getSubConds() {
        return subConds;
    }


    @Override
    public CompoundCond clone() {
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
            if (!isStrictlyIn(tmp,c.subConds))
                return false;
        }
        for (Condition tmp: c.subConds) {
            if (!isStrictlyIn(tmp,subConds))
                return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (not)
            sb.append("not ");
        sb.append(operator).append("(");
        List<String> subConds_s = subConds.stream()
                .map(Condition::toString)
                .collect(Collectors.toList());
        Collections.sort(subConds_s);
        sb.append(String.join(",",subConds_s));
        sb.append(")");
        return sb.toString();
    }

    public static void main(String[] args) {
        CompoundCond c = new CompoundCond(false,"AND",null);
        Condition c1 = c.clone();
        c.operator = "OR";
        System.out.println(c1.operator);
    }

}
