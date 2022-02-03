package org.example.node.orderby;

import com.alibaba.druid.sql.ast.SQLOrderBy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shenyichen
 * @date 2021/12/8
 **/
public class OrderBy {
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
       // todo sort?
    }

    public float score(){
        return 0;
    }

    public float score(OrderBy o){
        return 0;
    }

    @Override
    public OrderBy clone() {
        OrderBy orderBy = new OrderBy();
        orderBy.items = items.stream()
                .map(OrderByItem::clone)
                .collect(Collectors.toList());
        return orderBy;
    }

    @Override
    public int hashCode() {
        return items.get(0).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof OrderBy))
            return false;
        OrderBy orderBy = (OrderBy) obj;
        if (orderBy.items.size()!=items.size())
            return false;
        for (int i=0;i<items.size();i++){
            if (!(orderBy.items.get(i).equals(items.get(i))))
                return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("orderBy ");
        List<String> items_s = items.stream()
                .map(OrderByItem::toString)
                .collect(Collectors.toList());
        sb.append(String.join(",",items_s));
        return sb.toString();
    }
}
