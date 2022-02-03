package org.example.node.expr;

import com.alibaba.druid.sql.ast.SQLExpr;
import org.example.edit.CostConfig;

import java.util.ArrayList;
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

    public FuncExpr(){
    }

    public FuncExpr(String name, List<SQLExpr> exprs){
        this.name = name;
        parameters = new ArrayList<>();
        for (SQLExpr expr: exprs) {
            parameters.add(Expr.build(expr));
        }
    }

    public FuncExpr(List<Expr> exprs, String name){
        this.name = name;
        parameters = new ArrayList<>();
        if (exprs!=null){
            parameters.addAll(exprs);
        }
    }

    @Override
    public float score() {
        return (float) (CostConfig.func + parameters.stream()
                        .mapToDouble(Expr::score)
                        .sum());
    }

    @Override
    public float score(Expr e) {
        // case 1: this includes e
        float score = 0.0f;
        Expr match = Expr.isIn(e,parameters);
        if (match != null) {
            score = match.score(e);
        }
        if (e instanceof FuncExpr) {
            FuncExpr fe = (FuncExpr) e;
            // case 2: this equals e
            float matchScore = fe.name.equals(name) ? CostConfig.func : 0;
            List<Expr> parameters_clone = new ArrayList<>(fe.parameters);
            for (Expr item: parameters) {
                match = Expr.isIn(item,fe.parameters);
                if (match != null) {
                    matchScore += item.score(match);
                    parameters_clone.remove(item);
                }
            }
            for (Expr item: parameters_clone) {
                matchScore -= item.score() * CostConfig.delete_cost_rate;
            }
            score = Math.max(score, matchScore);
            // case 3: e includes this
            match = Expr.isIn(this,fe.parameters);
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
        return new FuncExpr(parameters_clone,name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof FuncExpr))
            return false;
        FuncExpr e = (FuncExpr) obj;
        if (!(e.name.equals(name)))
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
        return name + "(" + String.join(",",s) + ")";
    }
}
