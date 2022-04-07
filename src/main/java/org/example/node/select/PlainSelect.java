package org.example.node.select;

import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import org.example.Canonicalizer;
import org.example.Env;
import org.example.node.From;
import org.example.node.GroupBy;
import org.example.node.Limit;
import org.example.node.expr.AtomExpr;
import org.example.node.expr.FuncExpr;
import org.example.node.expr.PropertyExpr;
import org.example.node.orderby.OrderBy;
import org.example.node.condition.CompoundCond;
import org.example.node.condition.Condition;
import org.example.node.expr.Expr;
import org.example.node.table.Table;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author shenyichen
 * @date 2021/12/7
 **/
public class PlainSelect extends Select {
    public boolean distinct;
    public List<Expr> selections; // empty
    public From from; // from.joinTypes empty
    public Condition where; // null
    public GroupBy groupBy; // items empty, having null
    public OrderBy orderBy; //
    public Limit limit;

    private PlainSelect outerSelect;

    public PlainSelect(){}

    public PlainSelect getOuterSelect() {
        return outerSelect;
    }

    @Override
    public void setOuterSelect(PlainSelect outerSelect) {
        this.outerSelect = outerSelect;
    }

    public PlainSelect(SQLSelectQueryBlock query, Env env){
        super();
        distinct = query.getDistionOption()!=0;
        if(query.getFrom() != null) {
            from = new From(query.getFrom(), env, this);
        }
        else {
            from = new From();
        }
        // alias：从selections和tables中来，where中的不会干预到外面，而且还可能和外面重名，所以不考虑
        // 先做 from 后做 selections 可以避免重名问题（e.g. sql.csv 15）
        tableAliasMap.putAll(from.tableAliasMap);
        attrAliasMap.putAll(from.attrAliasMap);
        if (query.getSelectList() == null) {
            selections = new ArrayList<>();
        }
        else {
            selections = query.getSelectList()
                    .stream()
                    .map(x -> Expr.build(x.getExpr(), from.tableMapping))
                    .collect(Collectors.toList());
        }
        for (int i=0;i<selections.size();i++) {
            String alias = query.getSelectList().get(i).getAlias();
            if (alias != null) {
                attrAliasMap.put(alias, selections.get(i));
            }
            else if (selections.get(i) instanceof FuncExpr
                    && ((FuncExpr) selections.get(i)).parameters.size() == 1
            && (!(((FuncExpr) selections.get(i)).parameters.get(0) instanceof FuncExpr))) {
                Expr e = ((FuncExpr) selections.get(i)).parameters.get(0);
                if (e instanceof PropertyExpr)
                    alias = ((PropertyExpr) e).attribute.value;
                else
                    alias = e.toString();
                attrAliasMap.put(alias, selections.get(i));
            }
        }
        normalizeSelections();
        where = Condition.build(query.getWhere(), env, from.tableMapping);
        if (from != null && from.joinCondition != null) {
            if (where != null){
                where = new CompoundCond("AND", Arrays.asList(where,from.joinCondition));
            } else {
                where = from.joinCondition;
            }
        }
        if (where != null) {
            where = where.rearrange();
        }
        groupBy = new GroupBy(query.getGroupBy(), env, from.tableMapping);
        orderBy = new OrderBy(query.getOrderBy(), from.tableMapping);
        limit = new Limit(query.getLimit());
        outerSelect = null;
    }

    /**
     * 处理 *：转换为具体的列
     */
    public void normalizeSelections() {
        List<Expr> l = new ArrayList<>(selections);
        selections = new ArrayList<>();
        for (Expr e: l) {
            if (e instanceof PropertyExpr && ((PropertyExpr) e).attribute.value.equals("*")
                    && ((PropertyExpr) e).attribute.allColumnValue != null
                    && ((PropertyExpr) e).attribute.allColumnValue.size() > 0)
                selections.addAll(((PropertyExpr) e).attribute.allColumnValue);
            else if (e instanceof AtomExpr && ((AtomExpr) e).value.equals("*")
                    && ((AtomExpr) e).allColumnValue != null
                    && ((AtomExpr) e).allColumnValue.size() > 0)
                selections.addAll(((AtomExpr) e).allColumnValue);
            else
                selections.add(e);
        }
    }

    @Override
    public float score() {
        float score = 0.0f;
        score += distinct ? 1 : 0;
        score += selections.stream()
                .mapToDouble(Expr::score)
                .sum();
        if (from != null)
            score += from.score();
        if (where != null)
            score += where.score();
        score += groupBy.score();
        score += orderBy.score();
        score += limit.score();
        return score;
    }

    @Override
    public float score(Table t) {
        if (!(t instanceof PlainSelect))
            return 0;
        PlainSelect student = (PlainSelect) t;
        float score = 0;
        if (distinct) {
            if (student.distinct)
                score += 1;
        } else {
            if (student.distinct)
                score -= 1;
        }
        score += Expr.listScore(selections, student.selections);
        if (from != null)
            score += from.score(student.from);
        if (where != null)
            score += where.score(student.where);
        score += groupBy.score(student.groupBy);
        score += orderBy.score(student.orderBy);
        score += limit.score(student.limit);
        return score;
    }

    @Override
    public PlainSelect clone() {
        PlainSelect select = new PlainSelect();
        select.distinct = distinct;
        select.selections = selections.stream()
                .map(Expr::clone)
                .collect(Collectors.toList());
        if (from != null)
            select.from = from.clone();
        if (where != null)
            select.where = where.clone();
        select.groupBy = groupBy.clone();
        select.orderBy = orderBy.clone();
        select.limit = limit.clone();
        select.subqueries = subqueries;
        select.tableAliasMap = tableAliasMap;
        select.attrAliasMap = attrAliasMap;
        return select;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(Object t) {
        if (!(t instanceof PlainSelect))
            return false;
        PlainSelect ps = (PlainSelect) t;
        boolean res =  ps.distinct==distinct && ps.selections.size()==selections.size()
                && ((from==null && ps.from==null) || (from!=null && from.equals(ps.from)))
                && ((where==null && ps.where==null) || (where!=null && where.equals(ps.where)))
                && ps.groupBy.equals(groupBy) && ps.orderBy.equals(orderBy) && ps.limit.equals(limit);
        if (!res)
            return res;
        for (Expr e: selections) {
            if (!Expr.isDirectlyStrictlyIn(e, ps.selections))
                return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("select ");
        if (distinct)
            sb.append("distinct ");
        List<String> selections_s = selections.stream()
                .map(Expr::toString)
                .collect(Collectors.toList());
        sb.append(String.join(",",selections_s));
        if (from != null) {
            sb.append(from.toString());
        }
        if (where != null) {
            sb.append(" where ");
            sb.append(where.toString());
        }
        sb.append(groupBy.toString()).append(orderBy.toString()).append(limit.toString());
        return sb.toString().replaceAll("\\s+", " ").trim();
    }

}
