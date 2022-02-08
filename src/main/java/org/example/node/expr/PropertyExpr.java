package org.example.node.expr;

import org.example.edit.CostConfig;

/**
 * @author shenyichen
 * @date 2022/1/17
 **/
public class PropertyExpr extends Expr {
    public String table;
    public String attribute;

    public PropertyExpr(){}

    public PropertyExpr(String table, String attribute){
        this.table = table;
        this.attribute = attribute;
    }

    @Override
    public float score() {
        return CostConfig.property;
    }

    @Override
    public float score(Expr e) {
        float score = 0;
        // case 1: this equals e
        if (e instanceof PropertyExpr) {
            PropertyExpr pe = (PropertyExpr) e;
            if (table != null) {
                score += table.equals(pe.table) ? 0.5*score() : 0;
            }
            score += attribute.equals(pe.attribute) ? 0.5*score() : 0;
            return score;
        } else if (e instanceof FuncExpr){
            // case 2: e includes this
            FuncExpr fe = (FuncExpr) e;
            Expr match = Expr.isIn(this,fe.parameters);
            if (match != null) {
                return score(match) - (fe.score() - match.score()) * CostConfig.delete_cost_rate;
            }
        }
        return 0;
    }

    @Override
    public Expr clone() {
        return new PropertyExpr(table,attribute);
    }

    @Override
    public int hashCode() {
        return attribute.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PropertyExpr))
            return false;
        PropertyExpr e = (PropertyExpr) obj;
        return (table == null || table.equals(e.table)) && e.attribute.equals(attribute);
    }

    @Override
    public String toString() {
        if (table == null)
            return attribute;
        return table + "." + attribute;
    }
}
