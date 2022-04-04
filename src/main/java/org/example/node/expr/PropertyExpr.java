package org.example.node.expr;

import com.alibaba.druid.sql.ast.SQLObject;
import com.alibaba.druid.sql.ast.statement.SQLTableSource;
import org.example.edit.CostConfig;

/**
 * @author shenyichen
 * @date 2022/1/17
 **/
public class PropertyExpr extends Expr {
    public AtomExpr table;
    public AtomExpr attribute;

    public PropertyExpr(){}

    public PropertyExpr(String table, String attribute) {
        if (table != null)
            this.table = new AtomExpr(table);
        if (attribute != null)
            this.attribute = new AtomExpr(attribute);
    }

    public PropertyExpr(String table, String attribute, SQLObject object) {
        if (table != null)
            this.table = new AtomExpr(table);
        if (attribute != null)
            if (object instanceof SQLTableSource)
                this.attribute = new AtomExpr(attribute, (SQLTableSource) object);
            else
                this.attribute = new AtomExpr(attribute);
    }

    public PropertyExpr(AtomExpr table, AtomExpr attribute) {
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
        if (equals(e)) {
            return score();
        }
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
            Expr match = Expr.isIn(this,fe.parameters).getKey();
            if (match != null) {
                return score(match) - (fe.score() - match.score()) * CostConfig.delete_cost_rate;
            }
        } else if (e instanceof AtomExpr) {
            return (attribute.equals(e)) ? score() : 0;
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
        if (!(obj instanceof PropertyExpr || obj instanceof AtomExpr))
            return false;
        if (obj instanceof PropertyExpr) {
            PropertyExpr e = (PropertyExpr) obj;
            return (table == null || table.equals(e.table)) && e.attribute.equals(attribute);
        }
        else {
            return attribute.equals(obj);
        }
    }

    @Override
    public String toString() {
        if (table == null)
            return attribute.toString();
        return table.toString() + "." + attribute.toString();
    }
}
