package org.example;

import com.alibaba.druid.sql.repository.SchemaRepository;
import com.alibaba.druid.util.JdbcConstants;
import org.example.node.Node;
import org.example.node.enums.JoinType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shenyichen
 * @date 2021/12/4
 **/
public class PartialMarking {
    /**
     * 数据库类型
     */
    final String dbType;
    /**
     * 缓存SQL Schema信息，用于SQL语义解析中的ColumnResolve等操作
     */
    SchemaRepository repository;

    public PartialMarking(String dbType, List<String> sqls){
        this.dbType = dbType;
        this.repository = new SchemaRepository(dbType);
        repository.console("use sc00;");
        for (String sql: sqls) {
            repository.console(sql);
        }
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
        Node instrAST = BuildAST.buildAST(instrSql,dbType,repository);
        Canonicalizer.canonicalize(instrAST);
        Node studentAST = BuildAST.buildAST(studentSql,dbType,repository);
        Canonicalizer.canonicalize(studentAST);
        // TODO: 写一个有意义的规范化，删掉student的规范化
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
        String instrSql = "select s.id from student s, user u, lesson l\n" +
                "where u.uid=s.id and u.uid=l.sid";
        String studentSql = "select s.id from student s, user u, lesson l\n" +
                "where u.uid=s.id and s.id=l.sid";
        PartialMarking marking = new PartialMarking(JdbcConstants.MYSQL, new ArrayList<>());
        System.out.println(marking.partialMark(instrSql,studentSql,100.0f));
    }


}
