package org.example.node.orderby;

import com.alibaba.druid.sql.ast.statement.SQLSelectOrderByItem;
import org.example.edit.CostConfig;
import org.example.enums.Order;
import org.example.node.expr.Expr;

import java.util.List;
import java.util.stream.Collectors;

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
        if (item.getType() == null){
            order = Order.ASC;
        } else {
            order = Order.valueOf(item.getType().name);
        }
    }

    public float score(){
        return column.score() + CostConfig.order;
    }

    public float score(OrderByItem o){
        return column.score(o.column) + (order.equals(o.order) ? CostConfig.order : 0);
    }

    /**
     * 在 list 里有 similar 的（用于计算分数和 edit 步骤）
     */
    public static OrderByItem isIn(OrderByItem o, List<OrderByItem> l) {
        List<Expr> exprs = l.stream()
                .map(OrderByItem::getColumn)
                .collect(Collectors.toList());
        Expr match = Expr.isIn(o.column, exprs);
        if (match == null)
            return null;
        for (OrderByItem item: l) {
            if (item.column.equals(match))
                return item;
        }
        return null;
    }

    public Expr getColumn() {
        return column;
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

    @Override
    public String toString() {
        return column.toString() + " " + order.toString();
    }
}
