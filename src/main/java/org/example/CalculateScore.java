package org.example;

import javafx.util.Pair;
import org.example.edit.CostConfig;
import org.example.enums.SetOp;
import org.example.node.select.PlainSelect;
import org.example.node.select.Select;
import org.example.node.select.SetOpSelect;
import org.example.util.ErrorLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shenyichen
 * @date 2021/12/4
 **/
public class CalculateScore {
    /**
     * 根据 instrAST 的组件数，计算总分
     * @param s
     * @return
     */
    public static float totalScore(Select s) {
        return s.score();
    }

    /**
     * 贪心，计算 edit score
     */
    public static float editScore(Select instrAST, Select studentAST, float totalScore, Env env) throws Exception {
        boolean instrIsSetOp = instrAST instanceof SetOpSelect;
        boolean studentIsSetOp = studentAST instanceof SetOpSelect;
        // case 1: 学生sql和正确sql中至少有一个是set operator
        if (instrIsSetOp || studentIsSetOp) {
            // case 1.1: Only student has a set op
            // 如果不是except，匹配左右子树两种情况取最大
            //     * 左子树分数计算：左子树edit score 【减去】 右子树totalNodes 【减去】set operator不匹配的cost 1
            // 如果是except，匹配左子树（因为右子树的元素被剔除了，没有匹配它的必要）
            if (!instrIsSetOp) {
                SetOpSelect student = (SetOpSelect) studentAST;
                if (student.operator == SetOp.EXCEPT) {
                    return editScore(instrAST, student.left, totalScore, env) - totalScore(student.right) - 1;
                } else {
                    float left = editScore(instrAST, student.left, totalScore, env) - totalScore(student.right) - 1;
                    float right = editScore(instrAST, student.right, totalScore, env) - totalScore(student.left) - 1;
                    return Math.max(left,right);
                }
            }
            // case 1.2: Only instructor has a set op
            // 如果不是except，匹配左右子树两种情况取最大
            //     * 左子树分数计算：左子树edit score （分数上限是正确sql左子树的totalNodes）
            // 如果是except，匹配左子树
            //     * 同上，传入分数上限
            else if (!studentIsSetOp){
                SetOpSelect instr = (SetOpSelect) instrAST;
                if (instr.operator == SetOp.EXCEPT) {
                    return editScore(instr.left, studentAST, totalScore(instr.left), env);
                } else {
                    float left = editScore(instr.left, studentAST, totalScore(instr.left), env);
                    float right = editScore(instr.right, studentAST, totalScore(instr.right), env);
                    return Math.max(left,right);
                }
            }
            // case 1.3: 两个都是 set operator
            // set operator匹配则+1
            // 如果不是except，两种情况取max
            //     * left对left, right对right的情况分数计算：edit score相加，注意分数上限
            // 如果是except，匹配左子树
            //     * 同上，传入分数上限
            else {
                SetOpSelect instr = (SetOpSelect) instrAST;
                SetOpSelect student = (SetOpSelect) studentAST;
                float score = 0.0f;
                if (instr.operator == student.operator) {
                    score += CostConfig.set_operator;
                }
                if (instr.orderBy != null) {
                    score += instr.orderBy.score(student.orderBy);
                }
                if (instr.operator == SetOp.EXCEPT) {
                    score += editScore(instr.left, student.left, totalScore(instr.left), env)
                            + editScore(instr.right, student.right, totalScore(instr.right), env);
                } else {
                    float score1 = editScore(instr.left, student.left, totalScore(instr.left), env)
                            + editScore(instr.right, student.right, totalScore(instr.right), env);
                    float score2 = editScore(instr.left, student.right, totalScore(instr.left), env)
                            + editScore(instr.right, student.left, totalScore(instr.right), env);
                    score += Math.max(score1, score2);
                }
                return score;
            }
        }
        // case 2: 都是 plain select，走编辑路径
        else {
            PlainSelect instr = (PlainSelect) instrAST;
            PlainSelect student = (PlainSelect) studentAST;
            List<Pair<PlainSelect,Float>> singleEdits;
            try {
                singleEdits = SingleEdit.singleEdit(instr,student,env);
            } catch (Exception e){
                ErrorLogger.logSevere("Exception when calculating eidtScore:\ninstrSql:\n"
                        + instr.toString() + "\nstudentSql:\n" + student.toString(), e);
                throw e;
            }
            // 如果没有可进行的编辑，说明学生sql和正确sql已经完全一致，直接返回总分
            if (singleEdits.size()==0) {
                return totalScore;
            }
            // 如果还有可进行的编辑，迭代edit
            // 每个edit可以是add, remove, edit，它们的cost分别按不匹配的组件算
            // 每次取edits中 calculateScore - cost 最高的edit，分数减去 cost
            float maxScore = -2.0f * (instr.score() + student.score());
            float cost = 0.0f;
            PlainSelect bestMatch = null;
            for (Pair<PlainSelect,Float> edit: singleEdits) {
                float tmpCost = edit.getValue();
                float tmpScore = instr.score(edit.getKey()) - tmpCost;
                if (tmpScore > maxScore) {
                    maxScore = tmpScore;
                    cost = tmpCost;
                    bestMatch = edit.getKey();
                }
            }
            if (cost == 0.0f || bestMatch == null)
                return totalScore;
            float newTotalScore = totalScore - cost;
            if (newTotalScore <= 0) {
                return 0;
            }
            return editScore(instr, bestMatch, newTotalScore, env);
        }
    }

    /**
     * 获得对学生的提示
     */
    public static List<String> hintsFromEdits(Select instrAST, Select studentAST, float totalScore, Env env) throws Exception {
        List<String> hints = new ArrayList<>();
        boolean instrIsSetOp = instrAST instanceof SetOpSelect;
        boolean studentIsSetOp = studentAST instanceof SetOpSelect;
        // case 1: 学生sql和正确sql中至少有一个是set operator
        if (instrIsSetOp || studentIsSetOp) {
            // case 1.1: Only student has a set op
            if (!instrIsSetOp){
                SetOpSelect student = (SetOpSelect) studentAST;
                if (student.operator == SetOp.EXCEPT) {
                    hints.add("请尝试去掉except后面的内容");
                    hints.addAll(hintsFromEdits(instrAST, student.left, totalScore, env));
                } else {
                    float left = instrAST.score(student.left) - totalScore(student.right) - 1;
                    float right = instrAST.score(student.right) - totalScore(student.left) - 1;
                    if (left > right) {
                        hints.add("请尝试去掉" + student.operator.toString() + "后面的内容");
                        hints.addAll(hintsFromEdits(instrAST, student.left, totalScore, env));
                    }
                    else {
                        hints.add("请尝试去掉" + student.operator.toString() + "前面的内容");
                        hints.addAll(hintsFromEdits(instrAST, student.right, totalScore, env));
                    }
                }
            }
            // case 1.2: Only instructor has a set op
            else if (!studentIsSetOp){
                SetOpSelect instr = (SetOpSelect) instrAST;
                hints.add("请考虑使用" + instr.operator.toString() + "操作符");
                if (instr.operator == SetOp.EXCEPT) {
                    hints.addAll(hintsFromEdits(instr.left, studentAST, totalScore(instr.left), env));
                } else {
                    float left = instr.left.score(studentAST);
                    float right = instr.right.score(studentAST);
                    if (left > right) {
                        hints.addAll(hintsFromEdits(instr.left, studentAST, totalScore(instr.left), env));
                    }
                    else {
                        hints.addAll(hintsFromEdits(instr.right, studentAST, totalScore(instr.right), env));
                    }
                }
            }
            // case 1.3: 两个都是 set operator
            else {
                SetOpSelect instr = (SetOpSelect) instrAST;
                SetOpSelect student = (SetOpSelect) studentAST;
                if (instr.operator == student.operator) {
                    hints.add("请考虑将" + student.operator.toString() + "改为" + instr.operator.toString());
                }
                if (instr.orderBy != null && instr.orderBy.score(student.orderBy) < instr.orderBy.score()) {
                    hints.add("请检查orderBy部分");
                }
                if (instr.operator == SetOp.EXCEPT) {
                    hints.addAll(hintsFromEdits(instr.left, student.left, totalScore(instr.left), env));
                    hints.addAll(hintsFromEdits(instr.right, student.right, totalScore(instr.right), env));
                } else {
                    float score1 = instr.left.score(student.left) + instr.right.score(student.right);
                    float score2 = instr.left.score(student.right) + instr.right.score(student.left);
                    if (score1 > score2) {
                        hints.addAll(hintsFromEdits(instr.left, student.left, totalScore(instr.left), env));
                        hints.addAll(hintsFromEdits(instr.right, student.right, totalScore(instr.right), env));
                    }
                    else {
                        hints.addAll(hintsFromEdits(instr.left, student.right, totalScore(instr.left), env));
                        hints.addAll(hintsFromEdits(instr.right, student.left, totalScore(instr.right), env));
                    }
                }
            }
        }
        // case 2: 都是 plain select，走编辑路径
        else {
            PlainSelect instr = (PlainSelect) instrAST;
            PlainSelect student = (PlainSelect) studentAST;
            List<Pair<PlainSelect,Float>> singleEdits;
            try {
                singleEdits = SingleEdit.singleEdit(instr,student,env);
            } catch (Exception e){
                ErrorLogger.logSevere("Exception when calculating eidtScore:\ninstrSql:\n"
                        + instr.toString() + "\nstudentSql:\n" + student.toString(), e);
                throw e;
            }
            if (singleEdits.size()==0) {
                return hints;
            }
            float maxScore = -2.0f * (instr.score() + student.score());
            float cost = 0.0f;
            PlainSelect bestMatch = null;
            for (Pair<PlainSelect,Float> edit: singleEdits) {
                float tmpCost = edit.getValue();
                float tmpScore = instr.score(edit.getKey()) - tmpCost;
                if (tmpScore > maxScore) {
                    maxScore = tmpScore;
                    cost = tmpCost;
                    bestMatch = edit.getKey();
                }
            }
            if (cost == 0.0f || bestMatch == null)
                return hints;
            hints.addAll(SingleEdit.hint(bestMatch, student, env));
            float newTotalScore = totalScore - cost;
            if (newTotalScore <= 0) {
                return hints;
            }
            hints.addAll(hintsFromEdits(instr, bestMatch, newTotalScore, env));
        }
        return hints;
    }

    /**
     * LCS
     * https://blog.csdn.net/u012845099/article/details/78274095
     * @param A
     * @param B
     * @return
     */
    public static int lcs(String A, String B) {
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

    /**
     * 字符串编辑距离
     * https://blog.csdn.net/a1439775520/article/details/97398090
     * @param A
     * @param B
     * @return
     */
    public static int editDistance(String A, String B) {
        if(A.equals(B)) {
            return 0;
        }
        //dp[i][j]表示源串A位置i到目标串B位置j处最低需要操作的次数
        int[][] dp = new int[A.length() + 1][B.length() + 1];
        for(int i = 1;i <= A.length();i++)
            dp[i][0] = i;
        for(int j = 1;j <= B.length();j++)
            dp[0][j] = j;
        for(int i = 1;i <= A.length();i++) {
            for(int j = 1;j <= B.length();j++) {
                if(A.charAt(i - 1) == B.charAt(j - 1))
                    dp[i][j] = dp[i - 1][j - 1];
                else {
                    dp[i][j] = Math.min(dp[i - 1][j] + 1,
                            Math.min(dp[i][j - 1] + 1, dp[i - 1][j - 1] + 1));
                }
            }
        }
        return dp[A.length()][B.length()];
    }

    public static void main(String[] args) {
        String A = "ALGORITHM";
        String B = "ALTRUISTIC";
        System.out.println(editDistance(A, B));;
    }

}
