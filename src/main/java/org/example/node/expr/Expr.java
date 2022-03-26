package org.example.node.expr;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLObject;
import com.alibaba.druid.sql.ast.expr.*;
import com.alibaba.druid.sql.ast.statement.SQLTableSource;
import javafx.util.Pair;
import org.example.CalculateScore;
import org.example.edit.CostConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shenyichen
 * @date 2021/12/7
 **/
public abstract class Expr {
    public static Expr build(SQLExpr expr, HashMap<SQLTableSource, String> tableMapping){
        if (expr == null)
            return null;
        if (expr instanceof SQLAggregateExpr) {
            SQLAggregateExpr aggrExpr = (SQLAggregateExpr) expr;
            return new FuncExpr(aggrExpr.getMethodName(), aggrExpr.getOption(), aggrExpr.getArguments(), tableMapping);
        } else if (expr instanceof SQLMethodInvokeExpr) {
            SQLMethodInvokeExpr methodExpr = (SQLMethodInvokeExpr) expr;
            return new FuncExpr(methodExpr.getMethodName(), methodExpr.getArguments(), tableMapping);
        } else if (expr instanceof SQLPropertyExpr) {
            SQLPropertyExpr propExpr = (SQLPropertyExpr) expr;
            return new PropertyExpr(propExpr.getOwner().toString(),propExpr.getName());
        } else if (expr instanceof SQLIdentifierExpr) {
            if (((SQLIdentifierExpr) expr).getResolvedOwnerObject() != null) {
                String attr = ((SQLIdentifierExpr) expr).getName();
                SQLObject object = ((SQLIdentifierExpr) expr).getResolvedOwnerObject();
                String table = object.toString();
                if (object instanceof SQLTableSource
                        && tableMapping != null && tableMapping.containsKey(object)) {
                    table = tableMapping.get(object);
                }
                if (object instanceof SQLTableSource && ((SQLTableSource) object).getAlias() != null) {
                    table = ((SQLTableSource) object).getAlias();
                }
                return new PropertyExpr(table, attr);
            }
            SQLIdentifierExpr idExpr = (SQLIdentifierExpr) expr;
            return new AtomExpr(idExpr.getName());
        } else if (expr instanceof SQLAllColumnExpr) {
            // * 单独处理
            return new AtomExpr("*");
        } else if (expr instanceof SQLNullExpr) {
            return new ConstantExpr("NULL");
        } else if (expr instanceof SQLIntegerExpr) {
            return new ConstantExpr(((SQLIntegerExpr) expr).getValue().toString());
        } else if (expr instanceof SQLNumberExpr) {
            return new ConstantExpr(((SQLNumberExpr) expr).getValue().toString());
        } else if (expr instanceof SQLListExpr) {
            return new ListExpr(((SQLListExpr) expr).getItems()
            .stream()
            .map(e -> Expr.build(e, tableMapping))
            .collect(Collectors.toList()));
        } else {
            return new OtherExpr(expr.toString());
        }
    }

    public abstract float score();

    public abstract float score(Expr e);

    public static boolean isDirectlyStrictlyIn(Expr e, List<Expr> l){
        boolean flag = false;
        for (Expr tmp: l){
            if (e.equals(tmp)){
                flag = true;
                break;
            }
        }
        return flag;
    }

    public static float listScore(List<Expr> instr, List<Expr> student) {
        float score = 0.0f;
        Pair<List<Expr>, List<Expr>> matches = getMatches(instr, student);
        List<Expr> match_instr = matches.getKey();
        List<Expr> match_stu = matches.getValue();
        List<Expr> exprs_clone = new ArrayList<>(student);
        for (int i=0; i<match_instr.size(); i++) {
            score += match_instr.get(i).score(match_stu.get(i));
            exprs_clone.remove(match_stu.get(i));
        }
        for (Expr item: exprs_clone) {
            score -= item.score() * CostConfig.delete_cost_rate;
        }
        return score;
    }

    public static Pair<List<Expr>, List<Expr>> getMatches(List<Expr> instr, List<Expr> stu) {
        double score = 0;
        Expr instr_expr = null, stu_expr = null;
        List<Expr> instr_clone = new ArrayList<>(instr);
        List<Expr> stu_clone = new ArrayList<>(stu);
        List<Expr> res_key = new ArrayList<>();
        List<Expr> res_value = new ArrayList<>();
        while (instr_clone.size() > 0 && stu_clone.size() > 0) {
            for (Expr item: instr_clone) {
                Pair<Expr, Double> match = isIn(item, stu_clone);
                Expr match_expr = match.getKey();
                double match_score = match.getValue();
                if (match_expr != null && match_score > score) {
                    score = match_score;
                    instr_expr = item;
                    stu_expr = match_expr;
                }
            }
            if (score == 0)
                break;
            res_key.add(instr_expr);
            res_value.add(stu_expr);
            score = 0;
            instr_clone.remove(instr_expr);
            stu_clone.remove(stu_expr);
        }
        return new Pair<>(res_key, res_value);
    }

    /**
     * 在 list 里有 similar 的（用于计算分数和 edit 步骤）
     */
    public static Pair<Expr, Double> isIn(Expr e, List<Expr> l) {
        Expr res = null;
        String s = e.toString();
        int distance = s.length();
        for (Expr item: l) {
            String tmp = item.toString();
            int d = CalculateScore.editDistance(s,tmp);
            if (d < distance) {
                distance = d;
                res = item;
            }
        }
        if (res != null && e.score(res) > 0) {
            return new Pair<>(res, 1 - (distance * 1.0 / s.length()));
        }
        return new Pair<>(null, 1d);
    }

    /**
     * 被 contains 方法调用
     * @return
     */
    @Override
    public abstract Expr clone();

    @Override
    public abstract int hashCode();

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract String toString();
}
