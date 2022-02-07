package org.example.node.expr;

import org.example.edit.CostConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shenyichen
 * @date 2022/1/27
 **/
public class ListExpr extends Expr {
    public List<Expr> exprs;

    public ListExpr(){
        exprs = new ArrayList<>();
    }

    public ListExpr(List<Expr> exprs) {
        this.exprs = exprs;
    }

    @Override
    public float score() {
        return (float) exprs.stream()
                .mapToDouble(Expr::score)
                .sum();
    }

    @Override
    public float score(Expr e) {
        // case 1: this includes e
        if (!(e instanceof ListExpr)) {
            Expr match = Expr.isIn(e,exprs);
            if (match != null) {
                return match.score(e);
            } else {
                return 0;
            }
        }
        // case 2: this equals e
        float score = 0.0f;
        ListExpr le = (ListExpr) e;
        return Expr.listScore(exprs, le.exprs);
    }

    @Override
    public Expr clone() {
        return new ListExpr(exprs.stream()
        .map(Expr::clone)
        .collect(Collectors.toList()));
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ListExpr))
            return false;
        ListExpr le = (ListExpr) obj;
        for (Expr e: exprs){
            if (!Expr.isStrictlyIn(e,le.exprs))
                return false;
        }
        return true;
    }

    @Override
    public String toString() {
        List<String> s = exprs.stream()
                .map(Expr::toString)
                .collect(Collectors.toList());
        return "(" + String.join(",",s) + ")";
    }
}
