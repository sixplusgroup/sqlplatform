package org.example.node;

import com.alibaba.druid.sql.ast.statement.SQLSelectOrderByItem;
import org.example.node.enums.Order;

/**
 * @author shenyichen
 * @date 2021/12/8
 **/
public class OrderByItem {
    public String column;
    public Order order;

    public OrderByItem(String column, Order order) {
        this.column = column;
        this.order = order;
    }

    public OrderByItem(SQLSelectOrderByItem item){
        column = item.getExpr().toString();
        if (item.getType()==null){
            order = Order.ASC;
        } else {
            order = Order.valueOf(item.getType().name);
        }
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new OrderByItem(column,order);
    }
}
