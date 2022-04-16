package org.example.node.expr;

import com.alibaba.druid.sql.ast.statement.*;
import org.example.edit.CostConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shenyichen
 * @date 2022/2/14
 **/
public class AtomExpr extends Expr {
    public String value;
    public List<PropertyExpr> allColumnValue;
    public boolean substituted;

    public AtomExpr(String originStr) {
        super(originStr);
        substituted = false;
        this.value = originStr;
    }

    public AtomExpr(String value, String originStr) {
        this(originStr);
        this.value = value;
    }

    public AtomExpr(String value, SQLTableSource tableSource, String originStr) {
        this(originStr);
        this.value = value;
        this.allColumnValue = new ArrayList<>();
        if (tableSource instanceof SQLExprTableSource) {
            SQLExprTableSource t = (SQLExprTableSource) tableSource;
            String table = t.getAlias();
            if (table == null)
                table = t.toString();
            if (t.getSchemaObject() != null && t.getSchemaObject().getStatement() != null
                    && t.getSchemaObject().getStatement() instanceof SQLCreateTableStatement) {
                for (SQLTableElement item: ((SQLCreateTableStatement) t.getSchemaObject().getStatement()).getTableElementList()) {
                    if (item instanceof SQLColumnDefinition) {
                        allColumnValue.add(new PropertyExpr(table, ((SQLColumnDefinition) item).getNameAsString(), item.toString()));
                    }
                }
            }
        }
    }

    @Override
    public float score() {
        return CostConfig.property;
    }

    @Override
    public float score(Expr e) {
        if (e instanceof AtomExpr) {
            return equals(e) ? score() : 0;
        }
        else if (e instanceof PropertyExpr) {
            return equals(((PropertyExpr) e).attribute) ? score() : 0;
        }
        return 0;
    }

    @Override
    public Expr clone() {
        return new AtomExpr(value, originStr);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AtomExpr)
            return value.equals(((AtomExpr) obj).value);
        else if (obj instanceof PropertyExpr)
            return value.equals(((PropertyExpr) obj).attribute.value);
        return false;
    }

    @Override
    public String toString() {
        return value;
    }
}
