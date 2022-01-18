package org.example;

import javafx.util.Pair;
import org.example.node.*;
import org.example.node.condition.CommutativeCond;
import org.example.node.condition.Condition;
import org.example.node.condition.UncommutativeCond;
import org.example.node.enums.SetOp;
import org.example.node.expr.Expr;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author shenyichen
 * @date 2021/12/4
 **/
public class CalculateScore {

    private static Logger logger = Logger.getLogger(CalculateScore.class.getName());
    /**
     * 根据 instrAST 的组件数，计算总分
     * @param instrAST
     * @return
     */
    public static float totalScore(Expr instrAST) {
        if (instrAST instanceof SetOpSelect) {
            SetOpSelect instr = (SetOpSelect) instrAST;
            return totalScore(instr.left) + totalScore(instr.right) + 1;
        } else if (instrAST instanceof PlainSelect) {
            PlainSelect instr = (PlainSelect) instrAST;
            float res = 0.0f;
            res += instr.distinct?1:0;
            res += instr.selections.size();
            res += instr.from.tables.size();
            res += instr.from.joinPatterns.size()*3;
            res += instr.from.joinConditions.size()*3;
            res += instr.where.conds.size()*3;
            res += instr.groupBy.items.size();
            res += instr.groupBy.having.size();
            res += instr.orderBy.items.size()*2;
            res += instr.limit.rowCount==null?0:1;
            res += instr.limit.offset==null?0:1;
            for (Select s: instr.from.subqueries) {
                res += totalScore(s);
            }
            for (Select s: instr.where.subqueries){
                res += totalScore(s);
            }
            return res;
        } else {
            logger.log(Level.WARNING,"TotalScore node is not of Select type.");
        }
        return 0.0f;
    }

    /**
     * 计算两个sql之间的距离，用于选定最佳edit
     * @param instrSql
     * @param studentSql
     * @return
     */
    public static float calculateScore(Select instrSql, Select studentSql) {
        if (instrSql instanceof SetOpSelect || studentSql instanceof SetOpSelect){
            if (!(instrSql instanceof SetOpSelect)){
                PlainSelect instr = (PlainSelect) instrSql;
                SetOpSelect student = (SetOpSelect) studentSql;
                if (student.operator == SetOp.EXCEPT) {
                    return calculateScore(instr,student.left) - totalScore(student.right) - 1;
                } else {
                    float left = calculateScore(instr,student.left) - totalScore(student.right) - 1;
                    float right = calculateScore(instr,student.right) - totalScore(student.left) - 1;
                    return Math.max(left,right);
                }
            }else if (!(studentSql instanceof SetOpSelect)){
                SetOpSelect instr = (SetOpSelect) instrSql;
                PlainSelect student = (PlainSelect) studentSql;
                if (instr.operator == SetOp.EXCEPT) {
                    return calculateScore(instr.left,student);
                } else {
                    float left = calculateScore(instr.left,student);
                    float right = calculateScore(instr.right,student);
                    return Math.max(left,right);
                }
            }else {
                SetOpSelect instr = (SetOpSelect) instrSql;
                SetOpSelect student = (SetOpSelect) studentSql;
                float score = 0.0f;
                if (instr.operator == student.operator) {
                    score += 1;
                }
                if (instr.operator == SetOp.EXCEPT) {
                    score += calculateScore(instr.left,student.left) + calculateScore(instr.right,student.right);
                } else {
                    float score1 = calculateScore(instr.left,student.left) + calculateScore(instr.right,student.right);
                    float score2 = calculateScore(instr.left,student.right) + calculateScore(instr.right,student.left);
                    score += Math.max(score1, score2);
                }
                return score;
            }
        } else {
            PlainSelect instr = (PlainSelect) instrSql;
            PlainSelect student = (PlainSelect) studentSql;
            float res = totalScore(instr);
            res -= calculateDistinctCost(instr.distinct,student.distinct);
            res -= calculateSelectionsCost(instr.selections,student.selections);
            res -= calculateFromCost(instr.from,student.from);
            res -= calculateWhereCost(instr.where,student.where);
            res -= calculateGroupByCost(instr.groupBy,student.groupBy);
            res -= calculateOrderByCost(instr.orderBy,student.orderBy);
            res -= calculateLimitCost(instr.limit,student.limit);
            return res;
        }
    }

    /**
     * 贪心，计算 edit score
     * @param instrAST
     * @param studentAST
     * @param totalScore 总分
     * @return
     */
    public static float editScore(Expr instrAST, Expr studentAST, float totalScore) {
        boolean instrIsSetOp = instrAST instanceof SetOpSelect;
        boolean studentIsSetOp = studentAST instanceof SetOpSelect;
        if (instrIsSetOp || studentIsSetOp) {
            // case 1: 学生sql和正确sql中至少有一个是set operator
            if (!instrIsSetOp){
                // case 1.1: Only student has a set op
                // 如果不是except，匹配左右子树两种情况取最大
                //     * 左子树分数计算：左子树edit score 【减去】 右子树totalNodes 【减去】set operator不匹配的cost 1
                // 如果是except，匹配左子树（因为右子树的元素被剔除了，没有匹配它的必要）
                SetOpSelect student = (SetOpSelect) studentAST;
                if (student.operator == SetOp.EXCEPT) {
                    return editScore(instrAST,student.left,totalScore) - totalScore(student.right) - 1;
                } else {
                    float left = editScore(instrAST,student.left,totalScore) - totalScore(student.right) - 1;
                    float right = editScore(instrAST,student.right,totalScore) - totalScore(student.left) - 1;
                    return Math.max(left,right);
                }
            } else if (!studentIsSetOp){
                // case 1.2: Only instructor has a set op
                // 如果不是except，匹配左右子树两种情况取最大
                //     * 左子树分数计算：左子树edit score （分数上限是正确sql左子树的totalNodes）
                // 如果是except，匹配左子树
                //     * 同上，传入分数上限
                SetOpSelect instr = (SetOpSelect) instrAST;
                if (instr.operator == SetOp.EXCEPT) {
                    return editScore(instr.left,studentAST,totalScore(instr.left));
                } else {
                    float left = editScore(instr.left,studentAST,totalScore(instr.left));
                    float right = editScore(instr.right,studentAST,totalScore(instr.right));
                    return Math.max(left,right);
                }
            } else {
                // case 1.3: 两个都是 set operator
                // set operator不匹配则分数-1
                // 如果不是except，两种情况取max
                //     * left对left, right对right的情况分数计算：edit score相加，注意分数上限
                // 如果是except，匹配左子树
                //     * 同上，传入分数上限
                SetOpSelect instr = (SetOpSelect) instrAST;
                SetOpSelect student = (SetOpSelect) studentAST;
                float score = 0.0f;
                if (instr.operator == student.operator) {
                    score += 1;
                }
                if (instr.operator == SetOp.EXCEPT) {
                    score += editScore(instr.left,student.left,totalScore(instr.left)) + editScore(instr.right,student.right,totalScore(instr.right));
                } else {
                    float score1 = editScore(instr.left,student.left,totalScore(instr.left)) + editScore(instr.right,student.right,totalScore(instr.right));
                    float score2 = editScore(instr.left,student.right,totalScore(instr.left)) + editScore(instr.right,student.left,totalScore(instr.right));
                    score += Math.max(score1, score2);
                }
                return score;
            }
        } else {
            // case 2: 都是 plain select，走编辑路径
            PlainSelect instr = (PlainSelect) instrAST;
            PlainSelect student = (PlainSelect) studentAST;
            List<Pair<PlainSelect,Float>> singleEdits;
            try {
                singleEdits = SingleEdit.singleEdit(instr,student);
            } catch (Exception e){
                logger.log(Level.SEVERE,"Exception when calculating eidtScore:\n"+e.getMessage());
                return 0.0f;
            }
            // 如果没有可进行的编辑，说明学生sql和正确sql已经完全一致，直接返回总分
            if (singleEdits.size()==0) {
                return totalScore;
            }
            // 如果还有可进行的编辑，迭代edit
            // 每个edit可以是add, remove, edit，它们的cost分别按不匹配的组件算（增删cost是operator+left+right，edit是什么不一样再减）
            // 每次取edits中 calculateScore - cost 最高的edit，分数减去 cost
            float maxScore = 0.0f;
            float cost = 0.0f;
            PlainSelect bestMatch = student;
            for (Pair<PlainSelect,Float> edit: singleEdits) {
                float tmpCost = edit.getValue();
                float tmpScore = calculateScore(instr,edit.getKey()) - tmpCost;
                if (tmpScore > maxScore) {
                    maxScore = tmpScore;
                    cost = tmpCost;
                    bestMatch = edit.getKey();
                }
            }
            float newTotalScore = totalScore - cost;
            if (newTotalScore <= 0) {
                return 0;
            }
            return editScore(instr,bestMatch,newTotalScore);
        }
    }

    public static float calculateDistinctCost(boolean instr, boolean student){
        if (instr != student){
            return 1;
        }
        return 0;
    }

    public static float calculateSelectionsCost(List<String> instr, List<String> student){
        float res = 0.0f;
        for (int i=0;i<instr.size();i++){
            if (i>=student.size()){
                break;
            }
            if (! (instr.get(i).equals(student.get(i)))){
                res += 1;
            }
        }
        return res;
    }

    public static float calculateFromCost(From instr, From student){
        float res = 0.0f;
        res += calculateTableCost(instr.tables,student.tables);
        res += calculateConditionCost(instr.joinConditions,student.joinConditions);
        res += calculateSubQCost(instr.subqueries,student.subqueries);
        return res;
    }

    public static float calculateTableCost(List<Table> instr, List<Table> student){
        float res = 0.0f;
        for (int i=0;i<instr.size();i++) {
            if (i>=student.size()){
                break;
            }
            if (! (instr.get(i).name.equals(student.get(i).name))){
                res += 1;
            }
        }
        return res;

    }

    public static float calculateConditionCost(List<Condition> instr, List<Condition> student){
        float res = 0.0f;
        for (Condition i: instr){
            boolean flag = false;
            for (Condition s: student){
                if (compareCond(i,s)){
                    flag = true;
                    break;
                }
            }
            if (!flag){
                res += 3;
            }
        }
        res -= 3*Math.max(student.size()-instr.size(),0);
        return res;
    }

    public static boolean compareCond(Condition instr, Condition stu){
//        if ((instr instanceof CommutativeCond && (!(stu instanceof CommutativeCond)))
//        || (instr instanceof UncommutativeCond && (!(stu instanceof UncommutativeCond))))
//            return false;
//        if (instr instanceof CommutativeCond){
//            if (!(((CommutativeCond) instr).operator.equals(((CommutativeCond) stu).operator)))
//                return false;
//            List<String> opI = ((CommutativeCond) instr).operands;
//            List<String> opS = ((CommutativeCond) instr).operands;
//            boolean flag = true;
//            for (int i=0;i<opI.size();i++){
//                if (i>=opS.size())
//                    break;
//                if (!opI.get(i).equals(opS.get(i)))
//                    flag = false;
//            }
//            return flag;
//        }else if (instr instanceof UncommutativeCond){
//            if (!((UncommutativeCond) instr).operator.equals(((UncommutativeCond) stu).operator))
//                return false;
//            if (!(((UncommutativeCond) instr).left.equals(((UncommutativeCond) stu))))
//                return false;
//            if (!(((UncommutativeCond) instr).right.equals(((UncommutativeCond) stu))))
//                return false;
//        } else {
//            return instr.value.equals(stu.value);
//        }
        return false;
    }

    public static float calculateSubQCost(List<Select> instr, List<Select> stu){
        float res = 0.0f;
        for (Select i: instr){
            float totalScore = totalScore(i);
            float maxScore = 0.0f;
            for (Select s: stu){
                if ((s instanceof SetOpSelect && i instanceof PlainSelect)
                || (i instanceof SetOpSelect && s instanceof PlainSelect)){
                    continue;
                } else if (s instanceof SetOpSelect && i instanceof SetOpSelect
                && (!(((SetOpSelect) s).operator.equals(((SetOpSelect) i).operator))))
                    continue;
                else {
                    maxScore = Math.max(calculateScore(i,s),maxScore);
                }
            }
            res += totalScore - maxScore;
        }
        return res;
    }

    public static float calculateWhereCost(Where instr, Where student){
        float res = 0.0f;
        res += calculateConditionCost(instr.conds,student.conds);
        res += calculateSubQCost(instr.subqueries,student.subqueries);
        return res;
    }

    public static float calculateGroupByCost(GroupBy instr, GroupBy student){
        float res = 0.0f;
        for (int i=0;i<instr.items.size();i++){
            if (i>=student.items.size()){
                break;
            }
            if (! (instr.items.get(i).column.equals(student.items.get(i).column))){
                res += 1;
            }
        }
        for (int i=0;i<instr.having.size();i++){
            if (i>=student.having.size()){
                break;
            }
            if (! (instr.having.get(i).value.equals(student.having.get(i).value))){
                res += 1;
            }
        }
        return res;
    }

    public static float calculateOrderByCost(OrderBy instr, OrderBy student){
        float res = 0.0f;
        for (int i=0;i<instr.items.size();i++) {
            if (i>=student.items.size()){
                break;
            }
            if (! (instr.items.get(i).column.equals(student.items.get(i).column))){
                res += 1;
            }
            if (!(instr.items.get(i).order.equals(student.items.get(i).order))){
                res += 1;
            }
        }
        return res;
    }

    public static float calculateLimitCost(Limit instr, Limit student){
        float res = 0.0f;
        if (instr.rowCount!=student.rowCount)
            res += 1;
        if (instr.offset!=student.offset)
            res += 1;
        return res;
    }

    /**
     * LCS
     * https://blog.csdn.net/u012845099/article/details/78274095
     * @param A
     * @param B
     * @return
     */
    private static int findLCS(String A, String B) {
        int n = A.length();
        int m = B.length();
        char[] a = A.toCharArray();
        char[] b = B.toCharArray();
        int[][] db = new int[n][m];
        for (int i = 0; i < n; i++) {
            if (a[i] == b[0]) {
                db[i][0] = 1;
                for (int j = i + 1; j < n; j++) {
                    db[j][0] = 1;
                }
                break;
            }
        }
        for (int i = 0; i < m; i++) {
            if (a[0] == b[i]) {
                db[0][i] = 1;
                for (int j = i + 1; j < m; j++) {
                    db[0][j] = 1;
                }
                break;
            }
        }

        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                if (a[i] == b[j]) {
                    db[i][j] = db[i - 1][j - 1] + 1;
                } else {
                    db[i][j] = Math.max(db[i - 1][j], db[i][j - 1]);
                }
            }
        }

        for(int i = 0;i<n;i++){
            for(int j = 0;j<m;j++){
                System.out.print(db[i][j]+" ");
            }
            System.out.println();
        }
        return db[n-1][m-1];
    }
}
