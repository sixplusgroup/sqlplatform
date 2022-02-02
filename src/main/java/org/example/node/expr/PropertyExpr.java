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

    public PropertyExpr(String table,String attribute){
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
            score += table.equals(pe.table) ? 0.5*score() : 0;
            score += attribute.equals(pe.attribute) ? 0.5*score() : 0;
            return score;
        } else if (e instanceof FuncExpr){
            // case 2: e includes this
            FuncExpr fe = (FuncExpr) e;
            Expr match = Expr.isIn(this,fe.parameters);
            if (match != null) {
                return score(match) - (fe.score() - match.score());
            }
        }
        return -e.score();
    }

    @Override
    public Expr clone() {
        return new PropertyExpr(table,attribute);
    }

    @Override
    public int hashCode() {
        return table.hashCode()*31 + attribute.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PropertyExpr))
            return false;
        PropertyExpr e = (PropertyExpr) obj;
        return e.table.equals(table) && e.attribute.equals(attribute);
    }

    @Override
    public String toString() {
        return table + "." + attribute;
    }
}
