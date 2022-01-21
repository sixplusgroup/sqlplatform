package org.example.node;

import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import org.example.BuildAST;
import org.example.Env;
import org.example.node.condition.Condition;
import org.example.node.expr.Expr;

import java.util.List;
import java.util.logging.Logger;

/**
 * @author shenyichen
 * @date 2021/12/7
 **/
public class PlainSelect extends Select {
    private static Logger logger = Logger.getLogger(BuildAST.class.getName());
    // TODO LIST: alias处理；等价类；条件树；提谓词
    // 现在的想法是：提谓词的话edit用不上，也不能体现max(...)这种edit，
    // 所以想的是把condition先按and和or构成条件树，然后每个真正的条件上再分left, right这种，
    // 最后每个item都可以是由predicate组成
    // 每个项（粒度没想好）可以都赋一个score或者计算score的方法，这样比较统一
    // TODO 等价类替换：selections, UncommutativeCond, from
    // 可以把from.joinConditions全移到where里，from里只判断uniqueJoinType和tables
    // TODO selections, from里会有表和列的alias
    // alias处理：alias对应的表是subquery时，按计算和instrSQL的分数时最接近的instr的subquery，如果没有就直接记0分
    // group by， order by，having, from和where的subqueries可能用到alias
    // TODO 规范化
    // 已废弃：aggr, predicate, func单独列出来算score
    public boolean distinct;
    // TODO: String 改成 Node
    // SQLIdentifierExpr, SQLPropertyExpr, SQLAggregateExpr [methodName, arguments[0], over..]
    // if: SQLMethodInvokeExpr[name, parameters]
    // SQLNumberExpr
    // SQLBinaryOpExpr[operator, left, right]
    // select: 属性, 表.属性，聚合函数count，rank()，sum(if(p.player_id=m.first_player, first_score, second_score))
    // ifnull(num,0), a/b, month(request_date), min(date), sum(date), count(date), 0.000, ifnull(contacts_cnt,0)
    // count(l.user_id) * 1.0 / (select count(distinct user_id) from logins) rate,
    // round(sum(if(s.s_score<60,1,0)) / count(*), 2)
    public List<Expr> selections;
    public From from;
    public Condition where;
    public GroupBy groupBy;
    public OrderBy orderBy;
    public Limit limit;

    public PlainSelect(){}

    public PlainSelect(SQLSelectQueryBlock query, Env env){
        distinct = query.getDistionOption()!=0;
        name = query.toString();
//        selections = query.getSelectList().stream().map(SQLSelectItem::toString).sorted().collect(Collectors.toList());
        from = new From();
        from.init(query.getFrom(),query.getWhere(),env);
//        where = new Where(Condition.processCond(query.getWhere(),repository));
        where = Condition.build(query.getWhere(),env);
        where.rearrange();
        groupBy = new GroupBy(query.getGroupBy(),env);
        orderBy = new OrderBy(query.getOrderBy());
        limit = new Limit(query.getLimit());
//        keyWords = new KeyWords(query);
    }

    @Override
    public PlainSelect clone() throws CloneNotSupportedException {
        PlainSelect select = new PlainSelect();
        select.distinct = this.distinct;
//        select.selections = new ArrayList<>();
//        for (String s: selections){
//            select.selections.add(s);
//        }
        select.from = this.from.clone();
        select.where = this.where.clone();
        select.groupBy = this.groupBy.clone();
        select.orderBy = this.orderBy.clone();
        select.limit = this.limit.clone();
//        select.keyWords = null;// 如果single_edit的每一步中用到了，要深拷贝
        return select;
    }

}
