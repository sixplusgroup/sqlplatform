package org.example.edit;

import javafx.util.Pair;
import org.example.CalculateScore;
import org.example.node.condition.*;
import org.example.node.expr.Expr;
import org.example.node.select.PlainSelect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author shenyichen
 * @date 2021/12/10
 **/
public class ConditionEdit {
    PlainSelect instr, stu;

    public ConditionEdit(PlainSelect instr, PlainSelect stu) {
        this.instr = instr;
        this.stu = stu;
    }

    public List<Pair<PlainSelect, Float>> singleEdit() {
        List<Pair<PlainSelect,Float>> res = new ArrayList<>();
        Condition instrC = instr.where;
        Condition stuC = stu.where;
        // 至少一个为 null 的情况
        if (instrC == null) {
            if (stuC != null) {
                PlainSelect edited = stu.clone();
                edited.where = null;
                res.add(new Pair<>(edited, -stuC.score() * CostConfig.delete_cost_rate));
            }
            return res;
        }
        else if (stuC == null) {
            PlainSelect edited = stu.clone();
            edited.where = instrC;
            res.add(new Pair<>(edited, instrC.score()));
            return res;
        }
        // 都不为 null 的情况
        else
            return edits(instrC, stuC);
    }

    private List<Pair<PlainSelect, Float>> edits(Condition instrC, Condition stuC) {
        List<Pair<PlainSelect, Float>> res = new ArrayList<>();
        if (instrC.equals(stuC))
            return res;
        // case 1: 两者都是简单 Condition
        if ((!(instrC instanceof CompoundCond)) && (!(stuC instanceof CompoundCond))) {
            if (instrC instanceof CommutativeCond && stuC instanceof CommutativeCond) {
                res.addAll(editCommutative((CommutativeCond) instrC, (CommutativeCond) stuC));
            }
            else if (instrC instanceof UncommutativeCond && stuC instanceof UncommutativeCond) {
                res.addAll(editUncommutative((UncommutativeCond) instrC, (UncommutativeCond) stuC));
            }
            else if (instrC instanceof CommutativeCond && stuC instanceof UncommutativeCond
                    && canDoUn2Comm((CommutativeCond) instrC, (UncommutativeCond) stuC)) {
                res.add(editUn2Comm((CommutativeCond) instrC, (UncommutativeCond) stuC));
            }
            else if (instrC instanceof UncommutativeCond && stuC instanceof CommutativeCond
                    && canDoComm2Un((UncommutativeCond) instrC, (CommutativeCond) stuC)) {
                res.add(editComm2Un((UncommutativeCond) instrC, (CommutativeCond) stuC));
            }
            else if (instrC instanceof Exist && stuC instanceof Exist) {
                res.addAll(editExist((Exist) instrC, (Exist) stuC));
            }
            else if (instrC instanceof OtherCond && stuC instanceof OtherCond) {
                res.addAll(editOther((OtherCond) instrC, (OtherCond) stuC));
            }
            // 不同类型，add / remove
            else {
                res.add(remove(stuC));
                res.add(add(stuC, instrC));
            }
        }
        // case 2: 至少一个是 CompoundCond
        else {
            // case 2.1: 两者都是 CompoundCond
            if (instrC instanceof CompoundCond && stuC instanceof CompoundCond) {
                Condition match_instr = Condition.findEqual(instrC, stuC);
                Condition match_stu = Condition.findEqual(stuC, instrC);
                // case 1) 不匹配
                if (match_instr == null && match_stu == null) {
                    res.add(remove(stuC));
                    res.add(add(stuC, instrC));
                    return res;
                }
                // 加上 instrC 多出来的部分
                if (match_instr != null) {
                    if (!(match_instr.equals(instrC))) {
                        res.add(addInstrExtra(instrC, stuC, match_instr));
                    }
                }
                // 减去 stuC 多出来的部分
                if (match_stu != null) {
                    if (!(match_stu.equals(stuC))) {
                        res.add(removeStuExtra(stuC, match_stu));
                    }
                }
                // 同级比较
                if (match_instr == null)
                    match_instr = instrC;
                if (match_stu == null)
                    match_stu = stuC;
                // case 2) 同级比较：不都是 CompoundCond，递归
                if (!(match_instr instanceof CompoundCond && match_stu instanceof CompoundCond)) {
                    res.addAll(edits(match_instr, stuC));
                    return res;
                }
                // case 3) 同级比较：都是 CompoundCond
                CompoundCond match_cc = (CompoundCond) match_instr;
                CompoundCond stu_cc = (CompoundCond) match_stu;
                // case 3.1) 可展平
                if (canflatten(match_cc, stu_cc) || canflatten(stu_cc, match_cc)) {
                    res.add(flatten(match_cc, stu_cc));
                }
                // case 3.2) 正常情况
                res.addAll(editCompound(match_cc, stu_cc));
            }
            // case 2.2: 只有 instrC 是 CompoundCond
            else if (instrC instanceof CompoundCond) {
                Condition match = Condition.findEqual(instrC, stuC);
                if (match == null || match instanceof CompoundCond) {
                    res.add(remove(stuC));
                    res.add(add(stuC, instrC));
                }
                else {
                    // 加上 instr 多出来的部分
                    res.add(addInstrExtra(instrC, stuC, match));
                    // 同级比较
                    res.addAll(edits(match, stuC));
                }
            }
            // case 2.3: 只有 stuC 是 CompoundCond
            else {
                Condition match = Condition.findEqual(stuC, instrC);
                if (match == null || match instanceof CompoundCond) {
                    res.add(remove(stuC));
                    res.add(add(stuC, instrC));
                }
                else {
                    // 减去 stuC 多出来的部分
                    res.add(removeStuExtra(stuC, match));
                    // 同级比较
                    res.addAll(edits(instrC, match));
                }
            }
        }
        return res;
    }

    private List<Pair<PlainSelect, Float>> editCompound(CompoundCond instrC, CompoundCond stuC) {
        List<Pair<PlainSelect, Float>> res = new ArrayList<>(editNormal(instrC, stuC));
        List<Condition> stuC_clone = new ArrayList<>(stuC.getSubConds());
        for (Condition item: instrC.getSubConds()) {
            Condition match = Condition.isIn(item,stuC_clone);
            // 匹配上了，edit
            if (match != null) {
                res.addAll(edits(item, match));
                stuC_clone.remove(match);
            }
            // add
            else {
                PlainSelect edited = stu.clone();
                Condition stuC_edited = Condition.find(stuC, edited.where);
                assert stuC_edited != null;
                CompoundCond stu_edited_cc = (CompoundCond) stuC_edited;
                stu_edited_cc.add(item.clone());
                res.add(new Pair<>(edited, item.score()));
            }
        }
        // remove
        for (Condition item: stuC_clone) {
            PlainSelect edited = stu.clone();
            Condition stuC_edited = Condition.find(stuC, edited.where);
            assert stuC_edited != null;
            CompoundCond stu_edited_cc = (CompoundCond) stuC_edited;
            stu_edited_cc.remove(item);
            res.add(new Pair<>(edited, item.score() * CostConfig.delete_cost_rate));
        }
        return res;
    }

    private List<Pair<PlainSelect, Float>> editCommutative(CommutativeCond instrC, CommutativeCond stuC) {
        List<Pair<PlainSelect, Float>> res = new ArrayList<>(editNormal(instrC, stuC));
        List<Expr> instrC_clone = new ArrayList<>(instrC.operands);
        List<Expr> stuC_clone = new ArrayList<>(stuC.operands);
        Pair<List<Expr>, List<Expr>> matches = Expr.getMatches(instrC.operands, stuC.operands);
        List<Expr> match_instr = matches.getKey();
        List<Expr> match_stu = matches.getValue();
        // 匹配上的，edit
        for (int i=0; i<match_instr.size(); i++) {
            Expr item = match_instr.get(i);
            Expr match = match_stu.get(i);
            instrC_clone.remove(item);
            stuC_clone.remove(match);
            if (!(match.equals(item))) {
                PlainSelect edited = stu.clone();
                Condition stuC_edited = Condition.find(stuC, edited.where);
                assert stuC_edited != null;
                CommutativeCond stu_edited_cc = (CommutativeCond) stuC_edited;
                stu_edited_cc.operands.remove(match);
                stu_edited_cc.operands.add(item.clone());
                res.add(new Pair<>(edited, item.score() - item.score(match)));
            }
        }
        // add
        for (Expr item: instrC_clone) {
            PlainSelect edited = stu.clone();
            Condition stuC_edited = Condition.find(stuC, edited.where);
            assert stuC_edited != null;
            CommutativeCond stu_edited_cc = (CommutativeCond) stuC_edited;
            stu_edited_cc.operands.add(item.clone());
            res.add(new Pair<>(edited, item.score()));
        }
        // remove
        for (Expr item: stuC_clone) {
            PlainSelect edited = stu.clone();
            Condition stuC_edited = Condition.find(stuC, edited.where);
            assert stuC_edited != null;
            CommutativeCond stu_edited_cc = (CommutativeCond) stuC_edited;
            stu_edited_cc.operands.remove(item);
            res.add(new Pair<>(edited, item.score() * CostConfig.delete_cost_rate));
        }
        return res;
    }

    private List<Pair<PlainSelect, Float>> editUncommutative(UncommutativeCond instrC, UncommutativeCond stuC) {
        List<Pair<PlainSelect, Float>> res = new ArrayList<>(editNormal(instrC, stuC));
        if (!(instrC.left.equals(stuC.left))) {
            PlainSelect edited = stu.clone();
            Condition stuC_edited = Condition.find(stuC, edited.where);
            assert stuC_edited != null;
            UncommutativeCond stu_edited_uc = (UncommutativeCond) stuC_edited;
            stu_edited_uc.left = instrC.left.clone();
            res.add(new Pair<>(edited, instrC.left.score() - instrC.left.score(stuC.left)));
        }
        if (!(instrC.right.equals(stuC.right))) {
            PlainSelect edited = stu.clone();
            Condition stuC_edited = Condition.find(stuC, edited.where);
            assert stuC_edited != null;
            UncommutativeCond stu_edited_uc = (UncommutativeCond) stuC_edited;
            stu_edited_uc.right = instrC.right.clone();
            res.add(new Pair<>(edited, instrC.right.score() - instrC.right.score(stuC.right)));
        }
        return res;
    }

    private boolean canDoUn2Comm(CommutativeCond instrC, UncommutativeCond stuC) {
        return instrC.getNot() == stuC.getNot()
                && Expr.isStrictlyIn(stuC.left, instrC.operands)
                && Expr.isStrictlyIn(stuC.right, instrC.operands);
    }

    private Pair<PlainSelect, Float> editUn2Comm(CommutativeCond instrC, UncommutativeCond stuC) {
        CommutativeCond new_stu = new CommutativeCond(instrC.operator,
                Arrays.asList(stuC.left.clone(), stuC.right.clone()));
        PlainSelect edited = stu.clone();
        Condition stuC_edited = Condition.find(stuC, edited.where);
        assert stuC_edited != null;
        if (stuC_edited.father == null) {
            edited.where = new_stu;
        } else {
            CompoundCond father = stuC_edited.father;
            father.remove(stuC_edited);
            father.add(new_stu);
        }
        return new Pair<>(edited, CostConfig.math_operator);
    }

    private boolean canDoComm2Un(UncommutativeCond instrC, CommutativeCond stuC) {
        if (instrC.getNot() != stuC.getNot())
            return false;
        if (stuC.operands.size() != 2)
            return false;
        return (instrC.left.equals(stuC.operands.get(0)) && instrC.right.equals(stuC.operands.get(1)))
                || (instrC.left.equals(stuC.operands.get(1)) && instrC.right.equals(stuC.operands.get(0)));
    }

    private Pair<PlainSelect, Float> editComm2Un(UncommutativeCond instrC, CommutativeCond stuC) {
        UncommutativeCond new_stu = instrC.clone();
        PlainSelect edited = stu.clone();
        Condition stuC_edited = Condition.find(stuC, edited.where);
        assert stuC_edited != null;
        if (stuC_edited.father == null) {
            edited.where = new_stu;
        } else {
            CompoundCond father = stuC_edited.father;
            father.remove(stuC_edited);
            father.add(new_stu);
        }
        return new Pair<>(edited, CostConfig.math_operator);
    }

    private List<Pair<PlainSelect, Float>> editExist(Exist instrC, Exist stuC) {
        List<Pair<PlainSelect, Float>> res = new ArrayList<>(editNormal(instrC, stuC));
        float totalScore = CalculateScore.totalScore(instrC.subQuery);
        float cost = totalScore - CalculateScore.editScore(instrC.subQuery, stuC.subQuery, totalScore);
        PlainSelect edited = stu.clone();
        Condition stuC_edited = Condition.find(stuC, edited.where);
        assert stuC_edited != null;
        Exist stuC_edited_e = (Exist) stuC_edited;
        stuC_edited_e.subQuery = instrC.subQuery.clone();
        res.add(new Pair<>(edited, cost));
        return res;
    }

    private List<Pair<PlainSelect, Float>> editOther(OtherCond instrC, OtherCond stuC) {
        List<Pair<PlainSelect, Float>> res = new ArrayList<>(editNormal(instrC, stuC));
        PlainSelect edited = stu.clone();
        Condition stuC_edited = Condition.find(stuC, edited.where);
        assert stuC_edited != null;
        OtherCond stuC_edited_o = (OtherCond) stuC_edited;
        stuC_edited_o.value = instrC.value;
        res.add(new Pair<>(edited, stuC_edited_o.score() - stuC_edited_o.score(stuC)));
        return res;
    }

    /**
     * 通用 edits，包括 not 和 operator 的 edit
     * Attention: 仅限同类型调用
     * @param instrC
     * @param stuC
     * @return
     */
    private List<Pair<PlainSelect, Float>> editNormal(Condition instrC, Condition stuC) {
        List<Pair<PlainSelect, Float>> res = new ArrayList<>();
        if ((!(instrC instanceof AtomCond)) && instrC.getNot() != stuC.getNot()) {
            PlainSelect edited = stu.clone();
            Condition match = Condition.find(stuC, edited.where);
            assert match != null;
            Condition.setNot(match);
            res.add(new Pair<>(edited, CostConfig.not));
        }
        if (instrC instanceof CompoundCond || instrC instanceof AtomCond) {
            if (!(instrC.operator.equals(stuC.operator))) {
                PlainSelect edited = stu.clone();
                Condition match = Condition.find(stuC, edited.where);
                assert match != null;
                match.operator = instrC.operator;
                if (instrC instanceof CompoundCond) {
                    res.add(new Pair<>(edited, CostConfig.logic_operator));
                } else {
                    res.add(new Pair<>(edited, CostConfig.math_operator));
                }
            }
        }
        return res;
    }

    private Pair<PlainSelect, Float> addInstrExtra(Condition instrC, Condition stuC, Condition match) {
        PlainSelect edited = stu.clone();
        Condition stuC_edited = Condition.find(stuC, edited.where);
        assert stuC_edited != null;
        if (stuC_edited.father == null) {
            edited.where = substitute(stuC, match, instrC.clone());
        } else {
            Condition holder = substitute(stuC, match, instrC.clone());
            substitute(holder, stuC_edited, stuC_edited.father);
        }
        return new Pair<>(edited, instrC.score() - match.score());
    }

    private Pair<PlainSelect, Float> removeStuExtra(Condition stuC, Condition match) {
        PlainSelect edited = substitute(stuC, match);
        return new Pair<>(edited, (stuC.score() -match.score()) * CostConfig.delete_cost_rate);
    }

    /**
     * 在 stuC 的同级加入 instrC
     * @param stuC
     * @param instrC
     * @return
     */
    private Pair<PlainSelect, Float> add(Condition stuC, Condition instrC) {
        PlainSelect edited = stu.clone();
        Condition stuC_edited = Condition.find(stuC, edited.where);
        assert stuC_edited != null;
        if (stuC_edited.father == null) {
            edited.where = new CompoundCond("AND", Arrays.asList(stuC_edited, instrC.clone()));
        } else {
            CompoundCond father = stuC_edited.father;
            father.add(instrC.clone());
        }
        return new Pair<>(edited, instrC.score());
    }

    private Pair<PlainSelect, Float> remove(Condition stuC) {
        PlainSelect edited = stu.clone();
        Condition stuC_edited = Condition.find(stuC, edited.where);
        assert stuC_edited != null;
        if (stuC_edited.father == null) {
            edited.where = null;
        } else {
            CompoundCond father = stuC_edited.father;
            father.remove(stuC_edited);
            while (father.size() == 1) {
                if (father.father != null) {
                    CompoundCond grandFather = father.father;
                    grandFather.remove(father);
                    grandFather.add(father.get(0));
                    father = grandFather;
                } else {
                    edited.where = father.get(0);
                    break;
                }
            }
        }
        return new Pair<>(edited, stuC.score() * CostConfig.delete_cost_rate);
    }

    /**
     * 将 stuC 替换为 instrC
     * @param stuC
     * @param instrC
     * @return
     */
    private PlainSelect substitute(Condition stuC, Condition instrC) {
        PlainSelect edited = stu.clone();
        Condition stuC_edited = Condition.find(stuC, edited.where);
        assert stuC_edited != null;
        if (stuC_edited.father == null) {
            edited.where = instrC.clone();
        } else {
            CompoundCond father = stuC_edited.father;
            father.remove(stuC_edited);
            father.add(instrC.clone());
        }
        return edited;
    }

    /**
     * 在 holder 里抠掉和 toAdd 匹配的部分 toDel ，并拿 toAdd去替代它
     * toDel != holder 且 toDel 不是 holder里的结构
     * @param toAdd
     * @param holder
     * @return
     */
    private Condition substitute(Condition toAdd, Condition toDel, Condition holder) {
        Condition match = Condition.find(toDel, holder);
        assert match != null;
        assert match.father != null;
        CompoundCond father = match.father;
        father.remove(match);
        father.add(toAdd.clone());
        return holder;
    }

    /**
     * flatten
     *
     * A and B and C 和 A and B or C的互转？ /  = 和 >, < 的互转？
     *       AND                    OR
     *     /  |  \      <->         /  \
     *    A   B   C              AND   C
     *                          /   \
     *                         A    B
     *
     * 右->左： Compound有子Compound（operator一样就展平了，所以肯定不一样），且instr与其同operator且包含子Compound。。。
     * 简单的考虑吧：有子Compound的将operator改后展平，再比较
     *
     * 至于 A=B and B=C and C=D 和  A=B and C=D 互转的情况，
     * =(A,B,C,D) <-> AND(=(A,B),=(C,D))
     * 暂时不考虑
     */

    /**
     * a: 需要被 flatten的
     * @param a
     * @param b
     * @return
     */
    private boolean canflatten(CompoundCond a, CompoundCond b) {
        List<Condition> aConds = new ArrayList<>(a.getSubConds());
        List<Condition> bConds = new ArrayList<>(b.getSubConds());
        Condition match = Condition.findMostSimalrInList(b, aConds);
        if (!(match instanceof CompoundCond))
            return false;
        CompoundCond match_cc = (CompoundCond) match;
        if (!(match_cc.operator.equals(b.operator)))
            return false;
        if (match_cc.getNot() != b.getNot())
            return false;
        aConds.remove(match);
        for (Condition item: aConds) {
            if (Condition.isDirectlyIn(item, bConds))
                return false;
            bConds.remove(item);
        }
        for (Condition item: match_cc.getSubConds()) {
            if (Condition.isDirectlyIn(item, bConds))
                return false;
            bConds.remove(item);
        }
        return bConds.size() == 0;
    }

    private Pair<PlainSelect, Float> flatten(CompoundCond instr, CompoundCond stu) {
        return new Pair<>(substitute(stu, instr), CostConfig.logic_operator);
    }

}
