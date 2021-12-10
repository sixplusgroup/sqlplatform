package org.example.node;

import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.alibaba.druid.sql.repository.SchemaRepository;
import org.example.BuildAST;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @author shenyichen
 * @date 2021/12/7
 **/
public class PlainSelect extends Select {
    private static Logger logger = Logger.getLogger(BuildAST.class.getName());
    // TODO 等价类替换：selections, UncommutativeCond, from
    // 可以把from.joinConditions全移到where里，from里只判断uniqueJoinType和tables
    // TODO selections, from里会有表和列的alias
    // group by， order by，having, from和where的subqueries可能用到alias
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
    public List<String> selections;
    public From from;
    // Where: SQLBinaryOpExpr,  SQLPropertyExpr
    // where: year(order_date)=2019, date_add(l1.login_date,interval 1 day)
    public Where where;
    public GroupBy groupBy;
    public OrderBy orderBy;
    public Limit limit;

    // TODO aggr, predicate, func单独列出来算score
    public List<String> keywords;

    public PlainSelect(){}

    public PlainSelect(SQLSelectQueryBlock query, SchemaRepository repository){
        distinct = query.getDistionOption()!=0;
        name = query.toString();
        selections = query.getSelectList().stream().map(SQLSelectItem::toString).sorted().collect(Collectors.toList());
        from = new From();
        from.init(query.getFrom(),query.getWhere(),repository);
        where = new Where(Condition.processCond(query.getWhere(),repository));
        groupBy = new GroupBy(query.getGroupBy(),repository);
        orderBy = new OrderBy(query.getOrderBy());
        limit = new Limit(query.getLimit());
    }

    @Override
    public PlainSelect clone() throws CloneNotSupportedException {
        PlainSelect select = new PlainSelect();
        select.distinct = this.distinct;
        select.selections = new ArrayList<>();
        for (String s: selections){
            select.selections.add(s);
        }
        select.from = this.from.clone();
        select.where = this.where.clone();
        select.groupBy = this.groupBy.clone();
        select.orderBy = this.orderBy.clone();
        select.limit = this.limit.clone();
        return select;
    }

}
