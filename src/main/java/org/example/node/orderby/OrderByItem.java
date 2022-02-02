package org.example.node.orderby;

import com.alibaba.druid.sql.ast.statement.SQLSelectOrderByItem;
import org.example.enums.Order;
import org.example.node.expr.Expr;

/**
 * @author shenyichen
 * @date 2021/12/8
 **/
public class OrderByItem {
    public Expr column;
    public Order order;

    public OrderByItem(Expr column, Order order) {
        this.column = column;
        this.order = order;
    }

    public OrderByItem(SQLSelectOrderByItem item){
        column = Expr.build(item.getExpr());
        if (item.getType()==null){
            order = Order.ASC;
        } else {
            order = Order.valueOf(item.getType().name);
        }
    }

    @Override
    public OrderByItem clone() {
        return new OrderByItem(column,order);
    }

    @Override
    public int hashCode() {
        return order.ordinal()*31 + column.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof OrderByItem))
            return false;
        OrderByItem item = (OrderByItem) obj;
        return item.column.equals(column) && item.order.equals(order);
    }
}
