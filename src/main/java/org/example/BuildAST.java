package org.example;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLSelectQuery;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.ast.statement.SQLUnionQuery;
import com.alibaba.druid.sql.repository.SchemaRepository;
import com.alibaba.druid.util.JdbcConstants;
import javafx.util.Pair;
import org.example.enums.SetOp;
import org.example.node.condition.*;
import org.example.node.expr.Expr;
import org.example.node.expr.FuncExpr;
import org.example.node.expr.ListExpr;
import org.example.node.expr.PropertyExpr;
import org.example.node.orderby.OrderByItem;
import org.example.node.select.PlainSelect;
import org.example.node.select.Select;
import org.example.node.select.SetOpSelect;
import org.example.node.table.Table;
import org.example.util.CSVReader;
import org.example.util.ErrorLogger;
import org.example.util.TxtWriter;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 将 sql 语句解析为 AST
 * @author shenyichen
 * @date 2021/12/4
 **/
public class BuildAST {
    public static Select buildSelect(String sql, Env env) {
        try {
            List<SQLStatement> stmtList = SQLUtils.parseStatements(sql, env.dbType);
            SQLSelectStatement stmt = (SQLSelectStatement) stmtList.get(0);
            env.repository.resolve(stmt);
            SQLSelectQuery query = stmt.getSelect().getQuery();
            return buildSelect(query,env);
        } catch (Exception e){
            ErrorLogger.logSevere("SQL parse error:\nSQL: " + sql, e);
            throw e;
        }
    }

    public static Select buildSelect(SQLSelectQuery query, Env env) {
        if (query instanceof SQLUnionQuery){
            return buildSetOpSelect((SQLUnionQuery) query, env);
        } else if (query instanceof SQLSelectQueryBlock) {
            return buildPlainSelect((SQLSelectQueryBlock) query, env);
        } else {
            ErrorLogger.logSevere("SQL is PGValuesQuery:\nSQL: " + query.toString());
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

    public static void substituteAlias(Select instr, Select stu) {
        boolean instrIsSetOp = instr instanceof SetOpSelect;
        boolean studentIsSetOp = stu instanceof SetOpSelect;
        // case 1: 学生sql和正确sql中至少有一个是set operator
        if (instrIsSetOp || studentIsSetOp) {
            // case 1.1: Only student has a set op
            if (!instrIsSetOp) {
                SetOpSelect stu_ss = (SetOpSelect) stu;
                if (stu_ss.operator == SetOp.EXCEPT) {
                    substituteAlias(instr, stu_ss.left);
                } else {
                    int disLeft = CalculateScore.editDistance(instr.toString(), stu_ss.left.toString());
                    int disRight = CalculateScore.editDistance(instr.toString(), stu_ss.right.toString());
                    if (disLeft < disRight) {
                        substituteAlias(instr, stu_ss.left);
                    } else {
                        substituteAlias(instr, stu_ss.right);
                    }
                }
            }
            // case 1.2: Only instructor has a set op
            else if (!studentIsSetOp) {
                SetOpSelect instr_ss = (SetOpSelect) instr;
                if (instr_ss.operator == SetOp.EXCEPT) {
                    substituteAlias(((SetOpSelect) instr).left, stu);
                } else {
                    int disLeft = CalculateScore.editDistance(instr_ss.left.toString(), stu.toString());
                    int disRight = CalculateScore.editDistance(instr_ss.right.toString(), stu.toString());
                    if (disLeft < disRight) {
                        substituteAlias(instr_ss.left, stu);
                    } else {
                        substituteAlias(instr_ss.right, stu);
                    }
                }
            }
            // case 1.3: 两个都是 set operator
            else {
                SetOpSelect instr_ss = (SetOpSelect) instr;
                SetOpSelect stu_ss = (SetOpSelect) stu;
                if (instr_ss.operator == SetOp.EXCEPT) {
                    substituteAlias(instr_ss.left, stu_ss.left);
                    substituteAlias(instr_ss.right, stu_ss.right);
                } else {
                    int disLeft = CalculateScore.editDistance(instr_ss.left.toString(), stu_ss.left.toString());
                    int disRight = CalculateScore.editDistance(instr_ss.left.toString(), stu_ss.right.toString());
                    if (disLeft < disRight) {
                        substituteAlias(instr_ss.left, stu_ss.left);
                        substituteAlias(instr_ss.right, stu_ss.right);
                    } else {
                        substituteAlias(instr_ss.left, stu_ss.right);
                        substituteAlias(instr_ss.right, stu_ss.left);
                    }
                }
            }
        }
        // case 2: 都是 plain select
        else {
            substituteAliasForPlainSelect((PlainSelect) instr, (PlainSelect) stu);
        }
    }

    // 可能不需要 todo 如果出现instr是alias但student不是的情况，在aliasMap里替换（while，迭代替换）
    private static void substituteAliasForPlainSelect(PlainSelect instr, PlainSelect stu) {
        // step 1: 处理 from 里的 subQueries
        List<Table> stuFromSubQs = new ArrayList<>();
        if (instr.from != null && stu.from != null) {
            for (Table t: stu.from.tables) {
                if (t instanceof Select)
                    stuFromSubQs.add(t);
            }
            for (Table t: instr.from.tables) {
                if (t instanceof Select) {
                    Table match = Table.isIn(t, stuFromSubQs);
                    if (match instanceof Select) {
                        stuFromSubQs.remove(match);
                        substituteAlias((Select) t, (Select) match);
                    }
                }
            }
        }
        // step 2: main body
        HashMap<String, String> tableAliasMap = new HashMap<>();
        HashMap<String, String> attrAliasMap = new HashMap<>();
        // step 2.1: map stu alias to instr alias
        HashMap<Table, String> stuTableMap = exchangeHashMap(stu.tableAliasMap);
        List<Table> stuTables = new ArrayList<>(stuTableMap.keySet());
        for (Map.Entry<String, Table> item: instr.tableAliasMap.entrySet()) {
            Table instrTable = item.getValue();
            Table match = Table.isIn(instrTable, stuTables);
            if (match != null) {
                Pair<String, String> pair =
                        getCorrespondingAlias(stuTableMap.get(match),item.getKey(),instr.toString(), stu.toString());
                tableAliasMap.put(pair.getKey(), item.getKey());
                if (pair.getValue() == null || pair.getValue().length() == 0) {
                    stuTables.remove(match);
                    stuTableMap.remove(match);
                } else {
                    stuTableMap.put(match, pair.getValue());
                }
            }
        }
        HashMap<Expr, String> stuAttrMap = exchangeHashMap(stu.attrAliasMap);
        List<Expr> stuAttrs = new ArrayList<>(stuAttrMap.keySet());
        for (Map.Entry<String, Expr> item: instr.attrAliasMap.entrySet()) {
            Expr instrAttr = item.getValue();
            Expr match = Expr.isIn(instrAttr, stuAttrs).getKey();
            if (match != null) {
                Pair<String, String> pair =
                        getCorrespondingAlias(stuAttrMap.get(match),item.getKey(),instr.toString(), stu.toString());
                attrAliasMap.put(pair.getKey(), item.getKey());
                if (pair.getValue() == null || pair.getValue().length() == 0) {
                    stuAttrs.remove(match);
                    stuAttrMap.remove(match);
                } else {
                    stuAttrMap.put(match, pair.getValue());
                }
            }
        }
        // step 2.2: substitute, 注意要传递给上一层Select（e.g. sql.csv 15）
        substituteMainBody(stu, tableAliasMap, attrAliasMap);
        PlainSelect p = stu;
        if (p.getOuterSelect() != null) {
            PlainSelect outerSelect = p.getOuterSelect();
            substituteMainBody(outerSelect, tableAliasMap, attrAliasMap);
            p = outerSelect;
        }
        // step 3: 处理 where 里的 subQueries
        List<Table> stuWhereSubQs = getSubQsFromCondition(stu.where);
        for (Table t: getSubQsFromCondition(instr.where)) {
            if (t instanceof Select) {
                Table match = Table.isIn(t, stuWhereSubQs);
                if (match instanceof Select) {
                    stuWhereSubQs.remove(match);
                    substituteAlias((Select) t, (Select) match);
                }
            }
        }
        if (stu.where != null)
            stu.where = stu.where.rearrange();
    }

    private static List<Table> getSubQsFromCondition(Condition c) {
        List<Table> res = new ArrayList<>();
        if (c instanceof Exist) {
            res.add(((Exist) c).subQuery);
        }
        else if (c instanceof CompoundCond) {
            for (Condition item: ((CompoundCond) c).getSubConds()) {
                res.addAll(getSubQsFromCondition(item));
            }
        }
        return res;
    }

    private static <T2> HashMap<T2, String> exchangeHashMap(HashMap<String, T2> oriMap) {
        HashMap<T2, String> newMap = new HashMap<>();
        for (Map.Entry<String, T2> e: oriMap.entrySet()) {
            if (newMap.containsKey(e.getValue())) {
                newMap.put(e.getValue(), newMap.get(e.getValue()) + ":" + e.getKey());
            } else {
                newMap.put(e.getValue(), e.getKey());
            }
        }
        return newMap;
    }

    private static void substituteMainBody(PlainSelect stu,
                                           HashMap<String, String> tableAliasMap,
                                           HashMap<String, String> attrAliasMap) {
        for (Expr e: stu.selections) {
            substituteExpr(e, tableAliasMap, attrAliasMap);
        }
        substituteCondition(stu.where, tableAliasMap, attrAliasMap);
        for (Expr e: stu.groupBy.items) {
            substituteExpr(e, tableAliasMap, attrAliasMap);
        }
        substituteCondition(stu.groupBy.having, tableAliasMap, attrAliasMap);
        for (OrderByItem o: stu.orderBy.items) {
            substituteExpr(o.column, tableAliasMap, attrAliasMap);
        }
    }

    private static void substituteCondition(Condition c,
                                            HashMap<String, String> tableAliasMap,
                                            HashMap<String, String> attrAliasMap) {
        if (c instanceof CompoundCond) {
            for (Condition item: ((CompoundCond) c).getSubConds()) {
                substituteCondition(item, tableAliasMap, attrAliasMap);
            }
        }
        else if (c instanceof CommutativeCond) {
            for (Expr e: ((CommutativeCond) c).operands) {
                substituteExpr(e, tableAliasMap, attrAliasMap);
            }
        }
        else if (c instanceof UncommutativeCond) {
            substituteExpr(((UncommutativeCond) c).left, tableAliasMap, attrAliasMap);
            substituteExpr(((UncommutativeCond) c).right, tableAliasMap, attrAliasMap);
        }
        else if (c instanceof Exist) {
            substituteSubQ(((Exist) c).subQuery, tableAliasMap, attrAliasMap);
        }
    }

    private static void substituteSubQ(Select select,
                                         HashMap<String, String> tableAliasMap,
                                         HashMap<String, String> attrAliasMap) {
        if (select instanceof SetOpSelect) {
            substituteSubQ(((SetOpSelect) select).left, tableAliasMap, attrAliasMap);
            substituteSubQ(((SetOpSelect) select).right, tableAliasMap, attrAliasMap);
        }
        else {
            substituteMainBody((PlainSelect) select, tableAliasMap, attrAliasMap);
        }
    }

    private static void substituteExpr(Expr e,
                                       HashMap<String, String> tableAliasMap,
                                       HashMap<String, String> attrAliasMap) {
        if (e instanceof FuncExpr) {
            for (Expr item: ((FuncExpr) e).parameters) {
                substituteExpr(item, tableAliasMap, attrAliasMap);
            }
        }
        else if (e instanceof ListExpr) {
            for (Expr item: ((ListExpr) e).exprs) {
                substituteExpr(item, tableAliasMap, attrAliasMap);
            }
        }
        else if (e instanceof PropertyExpr) {
            PropertyExpr pe = (PropertyExpr) e;
            if (pe.table != null && tableAliasMap.containsKey(pe.table.value) && (!pe.table.substituted)) {
                pe.table.value = tableAliasMap.get(pe.table.value);
                pe.table.substituted = true;
            }
            if (attrAliasMap.containsKey(pe.attribute.value) && (!pe.attribute.substituted)) {
                pe.attribute.value = attrAliasMap.get(pe.attribute.value);
                pe.attribute.substituted = true;
            }
        }
    }

    private static Pair<String, String> getCorrespondingAlias(String toSplit, String standard, String instr, String stu) {
        if (!toSplit.contains(":")) {
            return new Pair<>(toSplit, "");
        }
        List<String> candidates = new ArrayList<>();
        for (String s: toSplit.split(":")) {
            candidates.add(s);
        }
        String match = "";
        int distance = Integer.MAX_VALUE;
        for (String s: candidates) {
            String stuAliased = stu.replaceAll(s,standard);
            int tmpDis = CalculateScore.editDistance(stuAliased, instr);
            if (tmpDis < distance) {
                match = s;
                distance = tmpDis;
            }
        }
        candidates.remove(match);
        return new Pair<>(match, String.join(":",candidates));
    }

    public static void main(String[] args) {
        String dbType = JdbcConstants.MYSQL;
        String sql = "select * from student, lesson";
//        String sql = "select student.id, (select 1 from lesson where lesson.id=1) from student";
//        String sql = "select u.uid, u.name myname from user u, stu";
//        String sql = "select * from  (select * from table_a left outer join table_b on table_a.aa=table_b.bb)";
//        String sql = "select * from table_a left outer join table_b on table_a.aa=table_b.bb and table_a.aa>2";
//        String sql = "select course_id, sec_id, year, semester, count(ID) from takes group by (course_id, sec_id, year, semester)";
//        String sql = "select e.dno, eno, salary from employees e\n" +
//                "where eno>1 and salary >= min (select e1.salary from employees e1 where e1.dno=e.dno)\n" +
//                "order by dno asc";
//        String sql = "select u.user_id, u.join_date, ifnull(num,0) orders_in_2019\n" +
//                "from users u left join\n" +
//                "  (select buyer_id, count(*) num\n" +
//                "  from orders\n" +
//                "  where year(order_date)=2019\n" +
//                "  group by buyer_id) as o\n" +
//                "on u.user_id=o.buyer_id\n" +
//                "order by u.user_id asc";
//        String sql = "SELECT\n" +
//                "\tp.product_name AS product_name,\n" +
//                "\to.order_id,\n" +
//                "\to.order_date\n" +
//                "FROM\n" +
//                "\torders o,\n" +
//                "\tcustomers c,\n" +
//                "\tproducts p\n" +
//                "WHERE\n" +
//                "\to.customer_id = c.customer_id\n" +
//                "AND o.product_id = p.product_id\n" +
//                "AND (o.product_id, o.order_date) IN (\n" +
//                "\tSELECT\n" +
//                "\t\tproduct_id,\n" +
//                "\t\tMAX(order_date)\n" +
//                "\tFROM\n" +
//                "\t\torders\n" +
//                "\tGROUP BY\n" +
//                "\t\tproduct_id\n" +
//                ")\n" +
//                "ORDER BY product_name;\n";
//        String sql = "select round(\n" +
//                "    ifnull(\n" +
//                "    (select count(distinct requester_id ,accepter_id) from accepted_requests) / \n" +
//                "    (select count(distinct sender_id ,send_to_id) from friend_requests)\n" +
//                "    ,0)\n" +
//                "    ,2) as accept_rate ;";
//        String sql = "select group_id,min(player_id) as player_id\n" +
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
        List<String> res = CSVReader.readCsv("../../src/main/resources/org/example/sqls.csv");
        SchemaRepository repository = new SchemaRepository(dbType);
        Env env = new Env(dbType,repository);
        String wirteToPath = "src/main/resources/org/example/BuildSelect.txt";
        for (int i=0;i<res.size();i++) {
            String s = res.get(i);
            try {
                buildSelect(s,env);
            } catch (Exception e) {
                StringWriter trace = new StringWriter();
                e.printStackTrace(new PrintWriter(trace));
                TxtWriter.writeTo(wirteToPath, "Attention!! " + (i+1) + "\n\n" +
                        s + "\n\n" + trace.toString() + "\n\n\n\n\n");
            }
        }
//        buildSelect(sql,new Env(dbType,repository));
    }
}
