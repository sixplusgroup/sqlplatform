package org.example.node.expr;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.expr.SQLAggregateOption;
import com.alibaba.druid.sql.ast.statement.SQLTableSource;
import javafx.util.Pair;
import org.example.edit.CostConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 聚合函数/其他函数的情况
 * @author shenyichen
 * @date 2022/1/17
 **/
public class FuncExpr extends Expr {
    public String name;
    public List<Expr> parameters;
    public String option;

    public FuncExpr(String name, SQLAggregateOption option, List<SQLExpr> exprs,
                    HashMap<SQLTableSource, String> tableMapping, String originStr){
        super(originStr);
        this.name = name;
        if (option != null)
            this.option = option.toString();
        parameters = new ArrayList<>();
        for (SQLExpr expr: exprs) {
            parameters.add(Expr.build(expr, tableMapping));
        }
    }

    public FuncExpr(String name, List<SQLExpr> exprs,
                    HashMap<SQLTableSource, String> tableMapping, String originStr){
        super(originStr);
        this.name = name;
        parameters = new ArrayList<>();
        for (SQLExpr expr: exprs) {
            parameters.add(Expr.build(expr, tableMapping));
        }
    }

    public FuncExpr(List<Expr> exprs, String name, String option, String originStr){
        this.originStr = originStr;
        this.name = name;
        this.option = option;
        parameters = new ArrayList<>();
        if (exprs != null){
            parameters.addAll(exprs);
        }
    }

    public String getAlias() {
        if (parameters.size() != 1)
            return null;
        Expr e = parameters.get(0);
        if (e instanceof PropertyExpr) {
            return ((PropertyExpr) e).attribute.value;
        }
        else if (e instanceof AtomExpr) {
            return ((AtomExpr) e).value;
        }
        else if (e instanceof FuncExpr) {
            return ((FuncExpr) e).getAlias();
        }
        return null;
    }

    @Override
    public float score() {
        return (float) (CostConfig.func + parameters.stream()
                        .mapToDouble(Expr::score)
                        .sum()
                        + (option == null ? 0 : CostConfig.expr_option));
    }

    @Override
    public float score(Expr e) {
        if (name.equals("COUNT") && option == null
                && e instanceof FuncExpr && ((FuncExpr) e).name.equals(name) && ((FuncExpr) e).option == null) {
            if ((parameters.get(0) instanceof AtomExpr && ((AtomExpr) parameters.get(0)).value.equals("*"))
            || (((FuncExpr) e).parameters.get(0) instanceof AtomExpr
                    && (((AtomExpr) ((FuncExpr) e).parameters.get(0)).value.equals("*")))) {
                return score();
            }
        }
        // case 1: this includes e
        float score = 0.0f;
        Expr match = Expr.isIn(e, parameters).getKey();
        if (match != null) {
            score = match.score(e);
        }
        if (e instanceof FuncExpr) {
            FuncExpr fe = (FuncExpr) e;
            // case 2: this equals e
            float matchScore = fe.name.equals(name) ? CostConfig.func : 0;
            if (option != null) {
                matchScore += (option.equals(fe.option) ? CostConfig.expr_option : 0);
            } else if (fe.option != null) {
                matchScore -= CostConfig.expr_option * CostConfig.delete_cost_rate;
            }
            List<Expr> stuC_clone = new ArrayList<>(fe.parameters);
            Pair<List<Expr>, List<Expr>> matches = Expr.getMatches(parameters, fe.parameters);
            List<Expr> match_instr = matches.getKey();
            List<Expr> match_stu = matches.getValue();
            for (int i=0; i<match_instr.size(); i++) {
                Expr item = match_instr.get(i);
                Expr match_item = match_stu.get(i);
                stuC_clone.remove(match);
                matchScore += item.score(match_item);
            }
            for (Expr item: stuC_clone) {
                matchScore -= item.score() * CostConfig.delete_cost_rate;
            }
            score = Math.max(score, matchScore);
            // case 3: e includes this
            match = Expr.isIn(this,fe.parameters).getKey();
            if (match != null) {
                matchScore = score(match) - (fe.score() - match.score()) * CostConfig.delete_cost_rate;
            }
            score = Math.max(score, matchScore);
        }
        return score;
    }

    @Override
    public Expr clone() {
        List<Expr> parameters_clone = parameters.stream()
                .map(Expr::clone)
                .collect(Collectors.toList());
        return new FuncExpr(parameters_clone, name, option, originStr);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (name.equals("COUNT") && option == null
                && obj instanceof FuncExpr && ((FuncExpr) obj).name.equals(name) && ((FuncExpr) obj).option == null) {
            if ((parameters.get(0) instanceof AtomExpr && ((AtomExpr) parameters.get(0)).value.equals("*"))
                    || (((FuncExpr) obj).parameters.get(0) instanceof AtomExpr
                    && (((AtomExpr) ((FuncExpr) obj).parameters.get(0)).value.equals("*")))) {
                return true;
            }
        }
        if (!(obj instanceof FuncExpr))
            return false;
        FuncExpr e = (FuncExpr) obj;
        if (!(e.name.equals(name)))
            return false;
        if (option != null) {
            if (!(option.equals(e.option)))
                return false;
        } else if (e.option != null)
            return false;
        if (e.parameters.size()!=parameters.size())
            return false;
        for (int i=0;i<parameters.size();i++) {
            if (!(e.parameters.get(i).equals(parameters.get(i))))
                return false;
        }
        return true;
    }

    @Override
    public String toString() {
        List<String> s = parameters.stream()
                .map(Expr::toString)
                .collect(Collectors.toList());
        if (option == null)
            return name + "(" + String.join(",",s) + ")";
        return name + "(" + option + " " + String.join(",",s) + ")";
    }
}
