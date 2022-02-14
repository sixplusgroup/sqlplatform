package org.example;

import com.alibaba.druid.sql.repository.SchemaRepository;
import com.alibaba.druid.util.JdbcConstants;
import org.example.node.select.Select;

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
        repository.console("use sc00;");
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
        Select instrAST = BuildAST.buildSelect(instrSql,env);
        Canonicalizer.canonicalize(instrAST);
        Select studentAST = BuildAST.buildSelect(studentSql,env);
        BuildAST.substituteAlias(instrAST, studentAST);
        float totalScore = CalculateScore.totalScore(instrAST);
        float score = CalculateScore.editScore(instrAST,studentAST,totalScore);
        return getScaledScore(score,totalScore,maxScore);
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
//        String instrSql = "select u.user_id, u.join_date, ifnull(num,0) orders_in_2019\n" +
//                "from users u left join\n" +
//                "  (select buyer_id, count(*) num\n" +
//                "  from orders\n" +
//                "  where year(order_date)=2019\n" +
//                "  group by buyer_id) as o\n" +
//                "on u.user_id=o.buyer_id\n" +
//                "order by u.user_id asc";
//        String studentSql = "select u.user_id, u.join_date, num orders_in_2019\n" +
//                "from users u left join\n" +
//                "  (select buyer_id, count(*) num\n" +
//                "  from orders\n" +
//                "  where year(order_date)=2019\n" +
//                "  group by buyer_id) as o\n" +
//                "on u.user_id=o.buyer_id and u.user_id>2\n" +
//                "order by u.user_id asc";
//        String instrSql = "select o.customer_id, max(distinct order_id) as order_num, sum(distinct order_id), abs(distinct order_id), if(favorite_brand = item_brand, 'yes', 'no'), ROUND(COUNT(b.user_id) * 1.0/COUNT(a.user_id), 3) AS rate, date_add(l1.login_date,interval 1 day) as date\n" +
//                "from orders o\n" +
//                "where o.order_date BETWEEN '2020-08-01' and '2020-08-31' and year(order_date)=2019\n" +
//                "GROUP BY o.customer_id\n" +
//                "order by order_num DESC, customer_id asc\n" +
//                "limit 1;";
        // select round(
        //    ifnull(
        //    (select count(distinct requester_id ,accepter_id) from accepted_requests) /
        //    (select count(distinct sender_id ,send_to_id) from friend_requests)
        //    ,0)
        //    ,2) as accept_rate ;
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
        String instrSql = "select s.id sid from student s, user u, lesson l\n" +
                "where u.uid=s.id and u.uid=l.sid";
//        String instrSql = "select s.id sid from student s, user u, lesson l\n" +
//                "where not u.uid>=s.id and not(u.uid<=l.sid or u.uid<'0')";
//        String instrSql = "select e1.dno,e1.eno, e1.salary\n" +
//                "from employees e1\n" +
//                "where e1.salary not in (\n" +
//                "\tselect MAX(salary)\n" +
//                "\tfrom employees e2\n" +
//                "\twhere e1.dno = e2.dno\n" +
//                "\tgroup by dno\n" +
//                ")\n" +
//                "order by dno;";
//        String instrSql = "select activity\n" +
//                "from friends\n" +
//                "group by activity\n" +
//                "having count(*)>any(\n" +
//                "    select count(*) from friends group by activity\n" +
//                ") and count(*)<any(\n" +
//                "    select count(*) from friends group by activity\n" +
//                ")";
        String studentSql = "select s.id from student s, user u, lesson l\n" +
                "where u.uid=s.id and s.id=l.sid";
        PartialMarking marking = new PartialMarking(JdbcConstants.MYSQL, new ArrayList<>());
        System.out.println(marking.partialMark(instrSql,studentSql,100.0f));
    }


}
