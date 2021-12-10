package org.example.node;

import com.alibaba.druid.sql.ast.statement.SQLSelectGroupByClause;
import com.alibaba.druid.sql.repository.SchemaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shenyichen
 * @date 2021/12/8
 **/
public class GroupBy implements Node {
    public List<GroupByItem> items;
    public List<Condition> having;

    public GroupBy(){
        items = new ArrayList<>();
        having = new ArrayList<>();
    }

    public GroupBy(SQLSelectGroupByClause clause, SchemaRepository repository){
        if (clause==null || clause.getItems()==null || clause.getItems().size()==0){
            items = new ArrayList<>();
            having = new ArrayList<>();
            return;
        }
        items = clause.getItems().stream().map(GroupByItem::new).collect(Collectors.toList());
        if (clause.getHaving()==null){
            having = new ArrayList<>();
        } else {
            having = Condition.processCond(clause.getHaving(),repository);
        }
    }

    @Override
    protected GroupBy clone() throws CloneNotSupportedException {
        GroupBy groupBy = new GroupBy();
        for (GroupByItem item: items){
            groupBy.items.add(item.clone());
        }
        for (Condition c: having){
            groupBy.having.add(c.clone());
        }
        return groupBy;
    }
}
