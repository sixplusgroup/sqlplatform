package org.example;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLSelectQuery;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.ast.statement.SQLUnionQuery;
import com.alibaba.druid.sql.repository.SchemaRepository;
import com.alibaba.druid.util.JdbcConstants;
import org.example.node.*;
import org.example.node.expr.Expr;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 将 sql 语句解析为 AST
 * @author shenyichen
 * @date 2021/12/4
 **/
public class BuildAST {

    private static Logger logger = Logger.getLogger(BuildAST.class.getName());

    public static Select buildSelect(String sql, Env env) {
        try {
            List<SQLStatement> stmtList = SQLUtils.parseStatements(sql, env.dbType);
            SQLSelectStatement stmt = (SQLSelectStatement) stmtList.get(0);
            SQLSelectQuery query = stmt.getSelect().getQuery();
            return buildSelect(query,env);
        } catch (Exception e){
            logger.log(Level.WARNING,"SQL parse error:\n" + e.getMessage());
            throw e;
        }
    }

    public static Select buildSelect(SQLSelectQuery query, Env env) {
        if (query instanceof SQLUnionQuery){
            return buildSetOpSelect((SQLUnionQuery) query, env);
        } else if (query instanceof SQLSelectQueryBlock) {
            return buildPlainSelect((SQLSelectQueryBlock) query, env);
        } else {
            logger.log(Level.WARNING,"SQL is PGValuesQuery");
            return null;
        }
    }

    public static Select buildSetOpSelect(SQLUnionQuery query, Env env) {
        Select left = buildSelect(query.getLeft(),env);
        Select right = buildSelect(query.getRight(),env);
        return new SetOpSelect(left,right,query.getOperator(),query.getOrderBy(),env);
    }

    public static Select buildPlainSelect(SQLSelectQueryBlock query, Env env) {
        return new PlainSelect(query,env);
    }


    public static void main(String[] args) {
        String dbType = JdbcConstants.MYSQL;
//        String sql = "select student.id, (select 1 from lesson where lesson.id=1) from student";
//        String sql = "select u.uid, u.name myname from user u, stu";
//        String sql = "select * from  (select * from table_a left outer join table_b on table_a.aa=table_b.bb)";
//        String sql = "select * from table_a left outer join table_b on table_a.aa=table_b.bb and table_a.aa>2";
//        String sql = "select course_id, sec_id, year, semester, count(ID) from takes group by (course_id, sec_id, year, semester)";
//        String sql = "select e.dno, eno, salary from employees e\n" +
//                "where eno>1 and salary >= min (select e1.salary from employees e1 where e1.dno=e.dno)\n" +
//                "order by dno asc";
        String sql = "select u.user_id, u.join_date, ifnull(num,0) orders_in_2019\n" +
                "from users u left join\n" +
                "  (select buyer_id, count(*) num\n" +
                "  from orders\n" +
                "  where year(order_date)=2019\n" +
                "  group by buyer_id) as o\n" +
                "on u.user_id=o.buyer_id\n" +
                "order by u.user_id asc";
//        String sql = "select date, round(count(l.user_id)/count(nu.user_id), 3) rate from\n" +
//                "  (select user_id, min(login_date) date from logins group by user_id) nu\n" +
//                "  left join logins l\n" +
//                "  on l.user_id=nu.user_id and l.login_date=date_add(nu.date, interval 1 day)\n" +
//                "group by date\n" +
//                "union all\n" +
//                "select login_date date, 0.000 rate from logins\n" +
//                "where login_date not in\n" +
//                "  (select min(login_date) date from logins group by user_id)\n" +
//                "order by date asc, rate desc";
//        String sql = "select user_id, sum(num) friend_num\n" +
//                "from\n" +
//                "  (select requester_id user_id, count(*) num\n" +
//                "  from (select distinct requester_id, accepter_id from accepted_requests) t1\n" +
//                "  group by requester_id\n" +
//                "  union all\n" +
//                "  select accepter_id user_id, count(*) num\n" +
//                "  from (select distinct requester_id, accepter_id from accepted_requests) t2\n" +
//                "  group by accepter_id) tmp\n" +
//                "group by user_id\n" +
//                "order by friend_num desc, user_id asc\n" +
//                "limit 1,8";
//        String sql = "select customer_id, count(*) order_num\n" +
//                "from orders\n" +
//                "where order_date>='2020-08-01' and order_date<='2020-08-31'\n" +
//                "group by customer_id\n" +
//                "order by order_num desc, customer_id asc\n" +
//                "limit 1";
//        String sql = "select customer_id, o.order_id, count(*),\n" +
//                "  rank() over (partition by customer_id order by order_date desc) r," +
//                "  sum(if(p.player_id=m.first_player, first_score, second_score))," +
//                "  month(request_date), min(date), 0.000, ifnull(contacts_cnt,0)," +
//                "  count(l.user_id) * 1.0 / (select count(distinct user_id) from logins) rate," +
//                "  round(sum(if(s.s_score<60,1,0)) / count(*), 2) \n" +
//                "  from orders o";
        buildSelect(sql,new Env(dbType,null));
    }
}
