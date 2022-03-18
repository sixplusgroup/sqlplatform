package org.example;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLPropertyExpr;
import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.repository.SchemaRepository;
import com.alibaba.druid.util.JdbcConstants;
import org.example.node.select.Select;
import org.example.util.CSVReader;
import org.example.util.TxtWriter;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shenyichen
 * @date 2021/12/4
 **/
public class PartialMarking {
    Env env;

    public PartialMarking(String dbType, List<String> sqls){
        SchemaRepository repository = new SchemaRepository(dbType);
//        repository.console("use sc00;");
        for (String sql: sqls) {
            repository.console(sql);
        }
        env = new Env(dbType,repository);
    }

    /**
     * 对每个小题，遍历答案集，取最高分
     * @param instrSqls 答案集
     * @param studentSql 学生sql
     * @param maxScore 分数上限
     * @return
     */
    public float partialMarkForAnswerSet(List<String> instrSqls, String studentSql, float maxScore) {
        float score = 0.0f;
        for (String instrSql: instrSqls) {
            score = Math.max(partialMark(instrSql, studentSql, maxScore), score);
        }
        return score;
    }

    /**
     * 给定一对sql语句，计算分数
     * @param instrSql 答案sql
     * @param studentSql 学生sql
     * @param maxScore 分数上限
     * @return
     */
    public float partialMark(String instrSql, String studentSql, float maxScore) {
        try {
            Select instrAST = BuildAST.buildSelect(instrSql.replaceAll("\\s+", " ").trim(),env);
//            Canonicalizer.canonicalize(instrAST);
            Select studentAST = BuildAST.buildSelect(studentSql.replaceAll("\\s+", " ").trim(),env);
            BuildAST.substituteAlias(instrAST, studentAST);
            // 不能在buildSelect时就替换，可能因为tableAlias交叉导致问题
            Canonicalizer.substituteEqualClass(instrAST);
            Canonicalizer.substituteEqualClass(studentAST);
            float totalScore = CalculateScore.totalScore(instrAST);
            float score = CalculateScore.editScore(instrAST,studentAST,totalScore);
            return getScaledScore(score,totalScore,maxScore);
        } catch (Exception e) {
            e.printStackTrace(); // to del
            int editDistance = CalculateScore.editDistance(instrSql, studentSql);
            return (float) ((1 - (editDistance * 1.0 / instrSql.length())) * maxScore);
        }
    }

    /**
     * get scaled score
     * @param score 原始分数（总分totalScore）
     * @param totalScore 根据instrAST的组件数计算出的总分
     * @param maxScore 分数上限
     * @return
     */
    public float getScaledScore(float score, float totalScore, float maxScore) {
        if (score <= 0) {
            return 0;
        } else {
            return score / totalScore * maxScore;
        }
    }

    public static void main(String[] args) {
// PASS
//        String instrSql = "select s.id sid from student s, user u, lesson l\n" +
//                "where u.uid=s.id and u.uid=l.sid";
// PASS: selections等价类替换
//        String studentSql = "select l.sid from user u, student s, lesson l\n" +
//                "where u.uid=s.id and u.uid=l.sid";
// PASS: AND展平
//        String studentSql = "select s.id sid from student s, user u, lesson l\n" +
//                "where u.uid=s.id and s.id=l.sid";
// PASS: alias替换、alias重名
//        String studentSql = "select l.id from student l, user s, lesson r\n" +
//                "where s.uid=l.id and l.id=r.sid";
// PASS: null
//        String instrSql = "select s.id sid from student s";
//        String studentSql = "select s.id from student s";
// PASS: alias替换
//        String instrSql = "select u.user_id, u.join_date, ifnull(num,0) orders_in_2019\n" +
//                "from users u left join\n" +
//                "  (select buyer_id, count(*) num\n" +
//                "  from orders\n" +
//                "  where year(order_date)=2019\n" +
//                "  group by buyer_id) as o\n" +
//                "on u.user_id=o.buyer_id\n" +
//                "order by u.user_id asc";
//        String studentSql = "select u.user_id, u.join_date, ifnull(haha,0) orders_in_2019\n" +
//                "from\n" +
//                "  (select buyer_id, count(*) haha\n" +
//                "  from orders\n" +
//                "  where year(order_date)=2019\n" +
//                "  group by buyer_id) as o1\n" +
//                "  right join users u\n" +
//                "on u.user_id=o1.buyer_id\n" +
//                "order by u.user_id asc";
//        String studentSql = "select u.user_id, u.join_date, ifnull(haha,0) orders_in_2019\n" +
//                "from\n" +
//                "  (select buyer_id, count(*) haha\n" +
//                "  from orders\n" +
//                "  where year(order_date)=2019\n" +
//                "  group by buyer_id) as o1\n" +
//                "  right join users u\n" +
//                "on u.user_id=o1.buyer_id and u.user_id>2\n" +
//                "order by u.user_id desc";
// PASS: 复杂Expr，List<Expr>的match （下面的例子里修改的是MAX里的distinct和order by的顺序）
//        String instrSql = "select o.customer_id, max(distinct order_id) as order_num, sum(distinct order_id), abs(distinct order_id), if(favorite_brand = item_brand, 'yes', 'no'), ROUND(COUNT(b.user_id) * 1.0/COUNT(a.user_id), 3) AS rate, date_add(l1.login_date,interval 1 day) as date\n" +
//                "from orders o\n" +
//                "where o.order_date BETWEEN '2020-08-01' and '2020-08-31' and year(order_date)=2019\n" +
//                "GROUP BY o.customer_id\n" +
//                "order by order_num DESC, customer_id asc\n" +
//                "limit 1;";
//        String studentSql = "select ROUND(COUNT(b.user_id) * 1.0/COUNT(a.user_id), 3) AS rate, o.customer_id, sum(distinct order_id), abs(distinct order_id), if(favorite_brand = item_brand, 'yes', 'no'), max(order_id) order_num, date_add(l1.login_date,interval 1 day) as date\n" +
//                "from orders o\n" +
//                "where o.order_date BETWEEN '2020-08-01' and '2020-08-31' and year(order_date)=2019\n" +
//                "GROUP BY o.customer_id\n" +
//                "order by customer_id asc, order_num DESC\n" +
//                "limit 1;";
// PASS: 无from todo parameters顺序
//        String instrSql = "select round(\n" +
//                "ifnull(\n" +
//                "      (select count(distinct requester_id ,accepter_id) from accepted_requests) /\n" +
//                "      (select count(distinct sender_id ,send_to_id) from friend_requests)\n" +
//                ",0)\n" +
//                ",2) as accept_rate ;";
//        String studentSql = "select round(\n" +
//                "ifnull(\n" +
//                "      (select count(distinct requester_id ,accepter_id) from accepted_requests) /\n" +
//                "      (select count(distinct sender_id ,send_to_id) from friend_requests)\n" +
//                ",0)\n" +
//                ",2) as accept_rate ;";
// PASS: union all连接的两个Select的顺序，alias，省略alias
//        String instrSql = "select group_id,min(player_id) as player_id\n" +
//                "from\n" +
//                "    (select player,sum(score) as score\n" +
//                "    from\n" +
//                "        ((select first_player player,first_score score from matches)\n" +
//                "        union all\n" +
//                "        (select second_player player,second_score score from matches)) t\n" +
//                "    group by player) a\n" +
//                "    right join players p on a.player=p.player_id\n" +
//                "where (group_id,score) in\n" +
//                "(select group_id,max(score) as mx\n" +
//                "from \n" +
//                "    (select player,sum(score) as score\n" +
//                "    from\n" +
//                "        ((select first_player player,first_score score from matches)\n" +
//                "        union all\n" +
//                "        (select second_player player,second_score score from matches)) t\n" +
//                "    group by player) a\n" +
//                "    right join players p on a.player=p.player_id\n" +
//                "group by group_id)\n" +
//                "group by group_id\n" +
//                "order by group_id;";
//        String studentSql = "select group_id,min(player_id) as player_id\n" +
//                "from\n" +
//                "    (select player,sum(marks) as mark\n" +
//                "    from\n" +
//                "       (select second_player player,second_score marks from matches) \n" +
//                "        union all\n" +
//                "        ((select first_player player,first_score marks from matches)) haha\n" +
//                "    group by player) lala\n" +
//                "    right join players wa on lala.player=wa.player_id\n" +
//                "where (group_id,mark) in\n" +
//                "(select group_id,max(haha)\n" +
//                "from \n" +
//                "    (select gamer,sum(lala) as haha\n" +
//                "    from\n" +
//                "        ((select first_player gamer,first_score lala from matches)\n" +
//                "        union all\n" +
//                "        (select second_player gamer,second_score lala from matches))\n" +
//                "    group by player) a\n" +
//                "    right join players p on a.gamer=p.player_id\n" +
//                "group by group_id)\n" +
//                "group by group_id\n" +
//                "order by group_id;";
// PASS: not的等价，tables顺序、alias, 条件的顺序
//        String instrSql = "select s.id sid from student s, user u, lesson l\n" +
//                "where not u.uid>=s.id and not(u.uid<=l.sid or u.uid<'0')";
//        String studentSql = "select u.id sid from user l, student u, lesson s\n" +
//                "where l.uid>s.sid and l.uid>='0' and l.uid<u.id";

        // to test
//        String instrSql = "select e1.dno,e1.eno, e1.salary\n" +
//                "from employees e1\n" +
//                "where e1.salary not in (\n" +
//                "\tselect MAX(salary)\n" +
//                "\tfrom employees e2\n" +
//                "\twhere e1.dno = e2.dno\n" +
//                "\tgroup by dno\n" +
//                ")\n" +
//                "order by dno;";
        // PASS: 去掉not
//        String studentSql = "select e1.dno,e1.eno, e1.salary\n" +
//                "from employees e1\n" +
//                "where e1.salary in (\n" +
//                "\tselect MAX(salary)\n" +
//                "\tfrom employees e2\n" +
//                "\twhere e1.dno = e2.dno\n" +
//                "\tgroup by dno\n" +
//                ")\n" +
//                "order by dno;";
        String instrSql = "select activity\n" +
                "from friends\n" +
                "group by activity\n" +
                "having count(*)>any(\n" +
                "    select count(*) from friends group by activity\n" +
                ") and count(*)<any(\n" +
                "    select count(*) from friends group by activity\n" +
                ")";
        String studentSql = "select activity\n" +
                "from friends\n" +
                "group by activity\n" +
                "having count(*)<any(\n" +
                "    select count(*) from friends group by activity\n" +
                ") and count(*)>any(\n" +
                "    select count(*) from friends group by activity\n" +
                ")";
        final String dbType = JdbcConstants.MYSQL;
        List<String> envs = new ArrayList<>();
        PartialMarking marking = new PartialMarking(dbType, envs);
//        System.out.println(marking.partialMark(instrSql,studentSql,100.0f));

        List<String> res = CSVReader.readCsv("../../src/main/resources/org/example/sqls.csv");
        String wirteToPath = "src/main/resources/org/example/PartialMarking.txt";
        for (int i=0;i<res.size();i++) {
            String s = res.get(i);
            try {
                float score = marking.partialMark(s,s,100.0f);
                if (score < 100.0f) {
                    TxtWriter.writeTo(wirteToPath, "Attention!! 评分" + score + " < 100 ！ " + (i+1) + "\n\n" +
                            s + "\n\n\n\n\n");
                }
            } catch (Exception e) {
                StringWriter trace = new StringWriter();
                e.printStackTrace(new PrintWriter(trace));
                TxtWriter.writeTo(wirteToPath, "Attention!! Error! " + (i+1) + "\n\n" +
                        s + "\n\n" + trace.toString() + "\n\n\n\n\n");
            }
        }

        // test column resolve
//        final String dbType = JdbcConstants.MYSQL;
//        List<String> envs = new ArrayList<>();
//        SchemaRepository repository = new SchemaRepository(dbType);
//        repository.console("create table t_emp(emp_id bigint, name varchar(20));");
//        repository.console("create table t_org(org_id bigint, name varchar(20));");
//        String sql = "SELECT emp_id, a.name AS emp_name, org_id, b.name AS org_name\n" +
//                "FROM t_emp a\n" +
//                "\tINNER JOIN t_org b ON table_a.emp_id = b.org_id";
//        List<SQLStatement> stmtList = SQLUtils.parseStatements(sql, dbType);
//        SQLSelectStatement stmt = (SQLSelectStatement) stmtList.get(0);
//        SQLSelectQueryBlock queryBlock = stmt.getSelect().getQueryBlock();
//
//        System.out.println((queryBlock.findTableSource("a")));//全名才能匹配，返回表名
//        repository.resolve(stmt);// 使用repository做column resolve
//        System.out.println(queryBlock.findTableSourceWithColumn("emp_id"));//返回表名
//
//        SQLExprTableSource tableSource = (SQLExprTableSource) queryBlock.findTableSourceWithColumn("emp_id");// .expr取得Identifier，.expr.name取得表名，.alias取得alias
//        SQLCreateTableStatement createTableStmt = (SQLCreateTableStatement) tableSource.getSchemaObject().getStatement();
//        System.out.println(createTableStmt);//取得建表语句
//
//        SQLSelectItem selectItem = queryBlock.findSelectItem("org_name");
//        System.out.println(selectItem);
//        SQLPropertyExpr selectItemExpr = (SQLPropertyExpr) selectItem.getExpr();
//        SQLColumnDefinition column = selectItemExpr.getResolvedColumn();
//        System.out.println(column);
    }


}
