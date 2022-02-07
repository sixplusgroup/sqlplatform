package org.example.node;

import com.alibaba.druid.sql.ast.statement.SQLSelectGroupByClause;
import org.example.Env;
import org.example.edit.CostConfig;
import org.example.node.condition.Condition;
import org.example.node.expr.Expr;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shenyichen
 * @date 2021/12/8
 **/
public class GroupBy {
    public List<Expr> items;
    public Condition having;

    public GroupBy(){
        items = new ArrayList<>();
        having = null;
    }

    public GroupBy(SQLSelectGroupByClause clause, Env env){
        if (clause==null || clause.getItems()==null || clause.getItems().size()==0){
            items = new ArrayList<>();
            having = null;
            return;
        }
        items = clause.getItems()
                .stream()
                .map(Expr::build)
                .collect(Collectors.toList());
        if (clause.getHaving()==null){
            having = null;
        } else {
            having = Condition.build(clause.getHaving(),env);
        }
    }

    public GroupBy(List<Expr> items, Condition having){
        this.items = items;
        this.having = having;
    }

    public float score() {
        float score = (float) items.stream()
                .mapToDouble(Expr::score)
                .sum();
        score += items.size() * CostConfig.sequence_penalty;
        if (having != null)
            score += having.score();
        return score;
    }

    public float score(GroupBy groupBy) {
        float score = 0;
        // items
        int idx = -1;
        List<Expr> items_clone = new ArrayList<>(groupBy.items);
        for (Expr e: items) {
            Expr tmp = Expr.isIn(e,items_clone);
            if (tmp != null) {
                score += e.score(tmp);
                items_clone.remove(tmp);
                int curIdx = groupBy.items.indexOf(tmp);
                if (curIdx > idx)
                    score += CostConfig.sequence_penalty;
                idx = curIdx;
            }
        }
        for (Expr e: items_clone) {
            score -= e.score() * CostConfig.delete_cost_rate;
        }
        // having
        if (having != null) {
            score += having.score(groupBy.having);
        } else {
            if (groupBy.having != null)
                score -= groupBy.having.score() * CostConfig.delete_cost_rate;
        }
        return score;
    }

    @Override
    public GroupBy clone() {
        List<Expr> items_clone = items.stream()
                .map(Expr::clone)
                .collect(Collectors.toList());
        return new GroupBy(items_clone,having.clone());
    }

    @Override
    public int hashCode() {
        return items.get(0).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof GroupBy))
            return false;
        GroupBy groupBy = (GroupBy) obj;
        if (groupBy.items.size() != items.size())
            return false;
        for (int i=0; i<items.size(); i++){
            if (!(groupBy.items.get(i).equals(items.get(i))))
                return false;
        }
        if (groupBy.having == null){
            if (having == null)
                return true;
            return false;
        } else {
            return groupBy.having.equals(having);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("groupBy ");
        List<String> items_s = items.stream()
                .map(Expr::toString)
                .collect(Collectors.toList());
        sb.append(String.join(",",items_s));
        sb.append(" having ");
        sb.append(having.toString());
        return sb.toString();
    }
}
