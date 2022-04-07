package org.example;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLPropertyExpr;
import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.dialect.mysql.ast.MySqlPrimaryKey;
import com.alibaba.druid.sql.repository.Schema;
import com.alibaba.druid.sql.repository.SchemaObject;
import com.alibaba.druid.sql.repository.SchemaRepository;
import com.alibaba.druid.util.JdbcConstants;
import org.example.node.select.Select;
import org.example.util.CSVReader;
import org.example.util.TxtWriter;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author shenyichen
 * @date 2021/12/4
 **/
public class PartialMarking {
    Env env;

    public PartialMarking(String dbType, List<String> sqls){
        env = new Env(dbType, sqls);
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
            Select instrAST = BuildAST.buildSelect(instrSql.replaceAll("\\s+", " ")
                    .toLowerCase().trim(),env);
            Canonicalizer.canonicalize(instrAST, env);
            Select studentAST = BuildAST.buildSelect(studentSql.replaceAll("\\s+", " ")
                    .toLowerCase().trim(),env);
            BuildAST.substituteAlias(instrAST, studentAST);
            // 不能在buildSelect时就替换，可能因为tableAlias交叉导致问题
            Canonicalizer.substituteEqualClass(instrAST);
            Canonicalizer.substituteEqualClass(studentAST);
            float totalScore = CalculateScore.totalScore(instrAST);
            float score = CalculateScore.editScore(instrAST, studentAST, totalScore, env);
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

    public List<String> getHintsForAnswerSet(List<String> instrSqls, String studentSql) {
        float score = 0.0f;
        String match = null;
        for (String instrSql: instrSqls) {
            float tmp = partialMark(instrSql, studentSql, 100);
            if (tmp > score) {
                score = tmp;
                match = instrSql;
            }
        }
        if (score > 0) {
            return getHints(match, studentSql);
        }
        return new ArrayList<>();
    }

    public List<String> getHints(String instrSql, String studentSql) {
        try {
            Select instrAST = BuildAST.buildSelect(instrSql.replaceAll("\\s+", " ")
                    .toLowerCase().trim(),env);
            Canonicalizer.canonicalize(instrAST, env);
            Select studentAST = BuildAST.buildSelect(studentSql.replaceAll("\\s+", " ")
                    .toLowerCase().trim(),env);
            BuildAST.substituteAlias(instrAST, studentAST);
            Canonicalizer.substituteEqualClass(instrAST);
            Canonicalizer.substituteEqualClass(studentAST);
            float totalScore = CalculateScore.totalScore(instrAST);
            return CalculateScore.hintsFromEdits(instrAST, studentAST, totalScore, env);
        } catch (Exception e) {
            e.printStackTrace(); // to del
            return new ArrayList<>();
        }
    }

    public static void main(String[] args) {
// PASS
//        String instrSql = "select s.id sid from student s, user u, lesson l\n" +
//                "where u.uid=s.id and u.uid=l.sid";
// PASS: selections等价类替换，AND展平
//        String studentSql = "select l.sid from user u, student s, lesson l\n" +
//                "where u.uid=s.id and s.id=l.sid";
// PASS: alias替换、alias重名
//        String studentSql = "select s.uid from student l, user s, lesson r\n" +
//                "where s.uid=l.id and l.id=r.sid";
// PASS: 复杂Expr的match
//        String instrSql = "select o.customer_id, max(distinct order_id) as order_num, sum(distinct order_id), abs(distinct order_id), if(favorite_brand = item_brand, 'yes', 'no'), ROUND(COUNT(b.user_id) * 1.0/COUNT(a.user_id), 3) AS rate, date_add(l1.login_date,interval 1 day) as date\n" +
//                "from orders o\n" +
//                "where o.order_date BETWEEN '2020-08-01' and '2020-08-31' and year(order_date)=2019\n" +
//                "GROUP BY o.customer_id\n" +
//                "order by order_num DESC, customer_id asc\n" +
//                "limit 1;";
//        String studentSql = "select ROUND(COUNT(b.user_id) * 1.0/COUNT(a.user_id), 3) AS rate, o.customer_id, sum(distinct order_id), abs(distinct order_id), if(favorite_brand = item_brand, 'yes', 'no'), max(distinct order_id) order_num, date_add(l1.login_date,interval 1 day) as date\n" +
//                "from orders o\n" +
//                "where o.order_date BETWEEN '2020-08-01' and '2020-08-31' and year(order_date)=2019\n" +
//                "GROUP BY o.customer_id\n" +
//                "order by order_num DESC, customer_id asc\n" +
//                "limit 1;";
// PASS: 无from
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
        String instrSql = "select group_id,min(player_id) as player_id\n" +
                "from\n" +
                "    (select player,sum(score)\n" +
                "    from\n" +
                "        ((select first_player player,first_score score from matches)\n" +
                "        union all\n" +
                "        (select second_player player,second_score score from matches)) t\n" +
                "    group by player) a\n" +
                "    right join players p on a.player=p.player_id\n" +
                "where (group_id,score) in\n" +
                "(select group_id,max(score) as mx\n" +
                "from \n" +
                "    (select player,sum(score) as score\n" +
                "    from\n" +
                "        ((select first_player player,first_score score from matches)\n" +
                "        union all\n" +
                "        (select second_player player,second_score score from matches)) t\n" +
                "    group by player) a\n" +
                "    right join players p on a.player=p.player_id\n" +
                "group by group_id)\n" +
                "group by group_id\n" +
                "order by group_id;";
        String studentSql = "select group_id,min(player_id) as player_id\n" +
                "from\n" +
                "    (select player,sum(marks) as mark\n" +
                "    from\n" +
                "       (select second_player player,second_score marks from matches) \n" +
                "        union all\n" +
                "        ((select first_player player,first_score marks from matches)) haha\n" +
                "    group by player) lala\n" +
                "    right join players wa on lala.player=wa.player_id\n" +
                "where (group_id,mark) in\n" +
                "(select group_id,max(haha)\n" +
                "from \n" +
                "    (select gamer,sum(lala) as haha\n" +
                "    from\n" +
                "        ((select first_player gamer,first_score lala from matches)\n" +
                "        union all\n" +
                "        (select second_player gamer,second_score lala from matches))\n" +
                "    group by player) a\n" +
                "    right join players p on a.gamer=p.player_id\n" +
                "group by group_id)\n" +
                "group by group_id\n" +
                "order by group_id;";
// PASS: not的等价，tables顺序、alias, 条件的顺序
//        String instrSql = "select s.id sid from student s, user u, lesson l\n" +
//                "where not u.uid>=s.id and not(u.uid<=l.sid or u.uid<'0')";
//        String studentSql = "select u.id sid from user l, student u, lesson s\n" +
//                "where l.uid>s.sid and l.uid>='0' and l.uid<u.id";
// PASS: not等价
//        String instrSql = "select * from r where r.a>10";
//        String studentSql = "select * from r where not (not (r.a>10))";
// PASS: 编辑距离的作用
//        String instrSql = "SELECT * FROM r INNER JOIN s ON (r.A=s.A)\n" +
//                "WHERE r.A>10";
//        String studentSql = "SELECT * FROM r INNER JOIN s ON (r.A=s.B)\n" +
//                "WHERE s.A>10";
// PASS: 等价类，joinType
//        String instrSql = "select a.id from a join b on a.id=b.id";
//        String studentSql = "select b.id from b,a on a.id=b.id";
// PASS: 带不带表名
//        String instrSql = "select u.uid from u, t order by tname";
//        String studentSql = "select uid from u, t order by t.tname";
// PASS: Condition.merge
//        String instrSql = "select u.uid from u,t where u.uid=t.tid and (u.uid=10 or u.uid=7)";
//        String studentSql = "select t.tid from u, t where (u.uid=t.tid and t.tid=10) or (u.uid=t.tid and t.tid=7)";
        final String dbType = JdbcConstants.MYSQL;
        List<String> sqls = new ArrayList<>();
        PartialMarking marking = new PartialMarking(dbType, sqls);
        System.out.println(marking.partialMark(instrSql,studentSql,100.0f));
    }
}
