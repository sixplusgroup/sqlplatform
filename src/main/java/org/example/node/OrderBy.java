package org.example.node;

import com.alibaba.druid.sql.ast.SQLOrderBy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shenyichen
 * @date 2021/12/8
 **/
public class OrderBy implements Node {
    public List<OrderByItem> items;

    public OrderBy() {
        items = new ArrayList<>();
    }

    public OrderBy(SQLOrderBy orderBy) {
       if (orderBy==null || orderBy.getItems()==null || orderBy.getItems().size()==0) {
           items = new ArrayList<>();
       } else {
           items = orderBy.getItems().stream().map(OrderByItem::new).collect(Collectors.toList());
       }
    }

    @Override
    protected OrderBy clone() throws CloneNotSupportedException {
        OrderBy orderBy = new OrderBy();
        for (OrderByItem item: items){
            orderBy.items.add((OrderByItem) item.clone());
        }
        return orderBy;
    }
}
