package org.example.node;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLObject;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.alibaba.druid.sql.ast.statement.SQLSelectGroupByClause;
import javafx.util.Pair;
import org.example.Env;
import org.example.edit.CostConfig;
import org.example.node.condition.Condition;
import org.example.node.expr.Expr;
import org.example.node.expr.PropertyExpr;

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
        if (clause == null || clause.getItems() == null || clause.getItems().size() == 0){
            items = new ArrayList<>();
            having = null;
            return;
        }
        items = new ArrayList<>();
        for (SQLExpr item: clause.getItems()) {
            items.add(Expr.build(item));
        }
        if (clause.getHaving() == null){
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
        List<Expr> stuC_clone = new ArrayList<>(groupBy.items);
        Pair<List<Expr>, List<Expr>> matches = Expr.getMatches(items, groupBy.items);
        List<Expr> match_instr = matches.getKey();
        List<Expr> match_stu = matches.getValue();
        for (int i=0; i<match_instr.size(); i++) {
            Expr item = match_instr.get(i);
            Expr match = match_stu.get(i);
            stuC_clone.remove(match);
            score += item.score(match);
            int curIdx = groupBy.items.indexOf(match);
            if (curIdx > idx)
                score += CostConfig.sequence_penalty;
            idx = curIdx;
        }
        for (Expr item: stuC_clone) {
            score -= item.score() * CostConfig.delete_cost_rate;
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
        if (having == null) {
            return new GroupBy(items_clone, null);
        } else {
            return new GroupBy(items_clone, having.clone());
        }
    }

    @Override
    public int hashCode() {
        if (items.size() == 0)
            return 1;
        return items.get(0).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof GroupBy))
            return false;
        GroupBy groupBy = (GroupBy) obj;
        if (groupBy.items.size() != items.size())
            return false;
        for (int i=0; i<items.size(); i++) {
            if (!(groupBy.items.get(i).equals(items.get(i))))
                return false;
        }
        if (groupBy.having == null) {
            if (having == null)
                return true;
            return false;
        } else {
            return groupBy.having.equals(having);
        }
    }

    @Override
    public String toString() {
        if (items.size() == 0 && having == null)
            return "";
        StringBuilder sb = new StringBuilder();
        sb.append(" groupBy ");
        List<String> items_s = items.stream()
                .map(Expr::toString)
                .collect(Collectors.toList());
        sb.append(String.join(",",items_s));
        if (having != null) {
            sb.append(" having ");
            sb.append(having.toString());
        }
        return sb.toString();
    }
}
