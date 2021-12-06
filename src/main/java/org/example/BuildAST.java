package org.example;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.util.JdbcConstants;

import java.util.List;

/**
 * @author shenyichen
 * @date 2021/12/4
 **/
public class BuildAST {
    public static void main(String[] args) {
        String dbType = JdbcConstants.MYSQL;
//        String sql = "select u.uid, u.name from user u, stu";
//        String sql = "select * from  (select * from table_a left outer join table_b on table_a.aa=table_b.bb)";
//        String sql = "select course_id, sec_id, year, semester, count(ID) from takes group by (course_id, sec_id, year, semester)";
        String sql = "select e.dno, eno, salary from employees e\n" +
                "where eno>1 and salary >= min (select e1.salary from employees e1 where e1.dno=e.dno)\n" +
                "order by dno asc";
        List<SQLStatement> stmtList = SQLUtils.parseStatements(sql, dbType);
        SQLSelectStatement stmt = (SQLSelectStatement) stmtList.get(0);
        SQLSelectQueryBlock query = (SQLSelectQueryBlock) stmt.getSelect().getQuery();
        SQLBinaryOpExpr subQuery = (SQLBinaryOpExpr) query.getWhere();
        System.out.println(subQuery);
    }
}
