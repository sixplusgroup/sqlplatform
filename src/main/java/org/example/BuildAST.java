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
import org.example.node.expr.*;
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
        return new SetOpSelect(left, right, query.getOperator(), query.getOrderBy(), env, query.toString());
    }

    public static Select buildPlainSelect(SQLSelectQueryBlock query, Env env) {
        return new PlainSelect(query, env, query.toString());
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
        substituteMainBody(stu, tableAliasMap, attrAliasMap, true);
        PlainSelect p = stu;
        if (p.getOuterSelect() != null) {
            PlainSelect outerSelect = p.getOuterSelect();
            substituteMainBody(outerSelect, tableAliasMap, attrAliasMap, false);
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
        stu.tableAliasMap = instr.tableAliasMap;
        stu.attrAliasMap = instr.attrAliasMap;
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

    public static <T2> HashMap<T2, String> exchangeHashMap(HashMap<String, T2> oriMap) {
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
                                           HashMap<String, String> attrAliasMap,
                                           boolean self) {
        for (Expr e: stu.selections) {
            if (self) {
                substituteExpr(e, tableAliasMap, new HashMap<>());
            }
            else {
                substituteExpr(e, tableAliasMap, attrAliasMap);
            }
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
            substituteMainBody((PlainSelect) select, tableAliasMap, attrAliasMap, false);
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
            if (tableAliasMap.containsKey(pe.table.value) && (!pe.table.substituted)) {
                pe.table.value = tableAliasMap.get(pe.table.value);
                pe.table.substituted = true;
            }
            if (attrAliasMap.containsKey(pe.attribute.value) && (!pe.attribute.substituted)) {
                pe.attribute.value = attrAliasMap.get(pe.attribute.value);
                pe.attribute.substituted = true;
            }
        }
        else if (e instanceof AtomExpr) {
            AtomExpr ae = (AtomExpr) e;
            if (attrAliasMap.containsKey(ae.value) && (!ae.substituted)) {
                ae.value = attrAliasMap.get(ae.value);
                ae.substituted = true;
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
        Env env = new Env(dbType,new ArrayList<>());
//        String sql = "select departmentId, salary\n" +
//                "from b, c\n" +
//                "where b.rk in (cnt+1/2,cnt+1,cnt) or b.rk=cnt+0.6;";
        String sql = "select u.uid from u, t where not (not(u.id=t.id) and not(u.name!=t.name))";
        Select s = buildSelect(sql, env);
        System.out.println("success");

//        List<String> res = CSVReader.readCsv("../../src/main/resources/org/example/sqls.csv");
//        String wirteToPath = "src/main/resources/org/example/BuildSelect.txt";
//        for (int i=0;i<res.size();i++) {
//            String s = res.get(i);
//            try {
//                buildSelect(s,env);
//            } catch (Exception e) {
//                StringWriter trace = new StringWriter();
//                e.printStackTrace(new PrintWriter(trace));
//                TxtWriter.writeTo(wirteToPath, "Attention!! " + (i+1) + "\n\n" +
//                        s + "\n\n" + trace.toString() + "\n\n\n\n\n");
//            }
//        }
    }
}
