package org.example.node;

import com.alibaba.druid.sql.ast.SQLExpr;

/**
 * @author shenyichen
 * @date 2021/12/8
 **/
public class GroupByItem {
    public String column;

    public GroupByItem(String column) {
        this.column = column;
    }

    public GroupByItem(SQLExpr expr) {
        column = expr.toString();
    }

    @Override
    protected GroupByItem clone() throws CloneNotSupportedException {
        return new GroupByItem(column);
    }
}
