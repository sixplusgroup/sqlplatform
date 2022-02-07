package org.example.node.select;

import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import org.example.BuildAST;
import org.example.Env;
import org.example.edit.CostConfig;
import org.example.node.From;
import org.example.node.GroupBy;
import org.example.node.Limit;
import org.example.node.orderby.OrderBy;
import org.example.node.condition.CompoundCond;
import org.example.node.condition.Condition;
import org.example.node.expr.Expr;
import org.example.node.table.Table;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @author shenyichen
 * @date 2021/12/7
 **/
public class PlainSelect extends Select {
    private static Logger logger = Logger.getLogger(BuildAST.class.getName());
    // TODO LIST:
    //  alias处理： alias对应的表是subquery时，按计算和instrSQL的分数时最接近的instr的subquery，如果没有就直接记0分
    //      selections, group by， order by，having, from和where的subqueries里会有表和列的alias
    //  等价类替换：selections, UncommutativeCond, from
    //  规范化
    // todo 顺序sort; null ；重跑
    /**
     * 现在的想法是：提谓词的话edit用不上，也不能体现max(...)这种edit，
     * 所以想的是把condition先按and和or构成条件树，然后每个真正的条件上再分left, right这种，
     * 最后每个item都可以是由predicate组成
     * 每个项（粒度没想好）可以都赋一个score或者计算score的方法，这样比较统一
     */
    public boolean distinct;
    /**
     * SQLBinaryOpExpr[operator, left, right]
     * select: 属性, 表.属性，聚合函数count，rank()，sum(if(p.player_id=m.first_player, first_score, second_score))
     * ifnull(num,0), a/b, month(request_date), min(date), sum(date), count(date), 0.000, ifnull(contacts_cnt,0)
     * count(l.user_id) * 1.0 / (select count(distinct user_id) from logins) rate,
     * round(sum(if(s.s_score<60,1,0)) / count(*), 2)
     */
    public List<Expr> selections; // empty
    public From from; // from.joinTypes empty
    public Condition where; // null
    public GroupBy groupBy; // items empty, having null
    public OrderBy orderBy; //
    public Limit limit;

    public PlainSelect(){}

    public PlainSelect(SQLSelectQueryBlock query, Env env){
        super();
        distinct = query.getDistionOption()!=0;
        selections = query.getSelectList()
                .stream()
                .map(x -> Expr.build(x.getExpr()))
                .collect(Collectors.toList());
        from = new From(query.getFrom(),env);
        where = Condition.build(query.getWhere(),env);
        if (from.joinCondition != null) {
            if (where != null){
                where = new CompoundCond("AND", Arrays.asList(where,from.joinCondition));
            } else {
                where = from.joinCondition;
            }
        }
        if (where != null) {
            where.rearrange();
        }
        groupBy = new GroupBy(query.getGroupBy(),env);
        orderBy = new OrderBy(query.getOrderBy());
        limit = new Limit(query.getLimit());
        // 统计量赋值：从selections和tables中来，where中的不会干预到外面，而且还可能和外面重名，所以不考虑
        tableAliasMap.putAll(from.tableAliasMap);
        attrAliasMap.putAll(from.attrAliasMap);
        processSelectionStats(query.getSelectList());
    }

    private void processSelectionStats(List<SQLSelectItem> original) {
        for (int i=0;i<selections.size();i++){
            String alias = original.get(i).getAlias();
            if (alias != null){
                attrAliasMap.put(alias,selections.get(i));
            }
        }
    }

    @Override
    public float score() {
        float score = 0.0f;
        score += distinct ? 1 : 0;
        score += selections.stream()
                .mapToDouble(Expr::score)
                .sum();
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
        select.from = from.clone();
        if (where!=null)
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
        return ps.distinct==distinct && ps.selections.equals(selections) && ps.from.equals(from)
                && ((where==null && ps.where==null) || (where!=null && ps.where.equals(where)))
                && ps.groupBy.equals(groupBy) && ps.orderBy.equals(orderBy) && ps.limit.equals(limit);
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
        sb.append(" from ");
        List<String> tables = from.tables.stream()
                .map(Table::toString)
                .collect(Collectors.toList());
        sb.append(String.join(",",tables));
        sb.append("(");
        List<String> joinTypes = new ArrayList<>();
        for (Map.Entry<String, Integer> entry: from.joinTypes.entrySet()) {
            joinTypes.add(entry.getValue() + " " + entry.getKey());
        }
        sb.append(String.join(",",joinTypes));
        sb.append(") where ");
        sb.append(where.toString());
        sb.append(" ").append(groupBy.toString());
        sb.append(" ").append(orderBy.toString());
        sb.append(" ").append(limit.toString());
        return sb.toString();
    }

}
