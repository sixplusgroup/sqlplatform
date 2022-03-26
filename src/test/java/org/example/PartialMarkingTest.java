package org.example;

import com.alibaba.druid.util.JdbcConstants;
import javafx.util.Pair;
import org.example.node.select.Select;
import org.example.util.CSVReader;
import org.example.util.MyFileReader;
import org.example.util.Triple;
import org.example.util.TxtWriter;
import org.junit.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shenyichen
 * @date 2022/3/25
 **/
public class PartialMarkingTest {

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/sqlexam";
    static final String USER = "root";
    static final String PASS = "aptx4869";
    static final String read_csv_prefix = "../../src/main/resources/org/example/";
    static final String prefix = "src/main/resources/org/example/";
    static final String dbType = JdbcConstants.MYSQL;

    @Test
    public void testOriginalAll() {
        // Normally test all
        List<String> sqls = new ArrayList<>();
        PartialMarking marking = new PartialMarking(dbType, sqls);
        List<String> res = CSVReader.readCsv(read_csv_prefix + "sqls.csv");
        String wirteToPath = prefix + "PartialMarking.txt";
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
    }

    @Test
    public void testAll() {
        HashMap<Integer, List<String>> mainQData = getResFromMainQ();
        HashMap<Integer, List<String>> subQData = getResFromSubQ();
        String wirteToPath = prefix + "PartialMarkingTestAll.txt";
        for (Map.Entry<Integer, List<String>> item: mainQData.entrySet()) {
            int mainId = item.getKey();
            List<String> sqls = item.getValue();
            PartialMarking marking = new PartialMarking(dbType, sqls);
            List<String> subQs = subQData.get(mainId);
            if (subQs != null) {
                for (String subQ: subQs) {
                    try {
                        float score = marking.partialMark(subQ,subQ,100.0f);
                        if (score < 100.0f) {
                            TxtWriter.writeTo(wirteToPath, "Attention!! 评分" + score + " < 100 ！ " + mainId + "\n\n" +
                                    subQ + "\n\n\n\n\n");
                        }
                    } catch (Exception e) {
                        StringWriter trace = new StringWriter();
                        e.printStackTrace(new PrintWriter(trace));
                        TxtWriter.writeTo(wirteToPath, "Attention!! Error! " + mainId + "\n\n" +
                                subQ + "\n\n" + trace.toString() + "\n\n\n\n\n");
                    }
                }
            }
        }
    }

    @Test
    public void testMyAnswer() {
        // stack over flow: 第6个
        HashMap<Integer, List<String>> mainQData = getResFromMainQ();
        List<Triple<Integer, String, String>> myAnswer = CSVReader.readCsv4(read_csv_prefix + "my_answer.csv");
        String wirteToPath = prefix + "PartialMarkingTestMyAnswer3.txt";
        for (Triple<Integer, String, String> item: myAnswer) {
            Integer mainId = item.first;
            List<String> sqls = mainQData.get(mainId);
            PartialMarking marking = new PartialMarking(dbType, sqls);
            System.out.println(item.first);
            try {
                float score = marking.partialMark(item.second,item.third,100.0f);
//                if (score < 100.0f) {
//                    TxtWriter.writeTo(wirteToPath, "Attention!! 评分" + score + " < 100 ！ " + mainId + "\n\n" +
//                            "答案sql:\n" + item.second + "\n学生sql:\n" + item.third + "\n\n\n\n\n");
//                }
            } catch (Exception e) {
                StringWriter trace = new StringWriter();
                e.printStackTrace(new PrintWriter(trace));
                TxtWriter.writeTo(wirteToPath, "Attention!! Error! " + mainId + "\n\n" +
                        "答案sql:\n" + item.second + "\n学生sql:\n" + item.third + "\n\n" + trace.toString() + "\n\n\n\n\n");
            }
        }
    }

    @Test
    public void testCreateSql() {
        String path = "src/main/resources/org/example/examDataFiles/upload_c9c0c1f5ceb0443bf580e69f8deb8633.sql";
        List<String> sqls = new ArrayList<>();
        sqls.add(MyFileReader.readFile(path));
//        sqls.add("create table t_emp(emp_id bigint, name varchar(20), primary key(emp_id));");
//        sqls.add("create table t_org(org_id bigint, name varchar(20));");
//        sqls.add("DROP TABLE IF EXISTS `customer`;\n" +
//                "CREATE TABLE `customer`  (\n" +
//                "  `customer_id` int NOT NULL,\n" +
//                "  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,\n" +
//                "  PRIMARY KEY (`customer_id`) USING BTREE\n" +
//                ") ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;");
        String sql = "select name from (select name from customer c where name=\"John\")";
        Env env = new Env(dbType, sqls);
        Select s = BuildAST.buildSelect(sql, env);
        System.out.println(s.toString());
    }

    private HashMap<Integer, List<String>> getResFromMainQ() {
        HashMap<Integer, List<String>> mainQData = new HashMap<>();
        Connection conn = null;
        Statement stmt = null;
        try{
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);
            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            // 执行查询
            System.out.println(" 实例化Statement对象...");
            stmt = conn.createStatement();
            String sql = "SELECT id, db_path FROM main_questions";
            ResultSet rs = stmt.executeQuery(sql);
            // 每道大题
            while(rs.next()){
                // 通过字段检索
                int id  = rs.getInt("id");
                String dbPath = rs.getString("db_path");
                // 输出数据
//                System.out.print("ID: " + id);
//                System.out.println(", dbPath: " + dbPath);
                List<String> sqls = new ArrayList<>();
                sqls.add(MyFileReader.readFile(prefix + dbPath));
                mainQData.put(id, sqls);
            }
            // 完成后关闭
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally{
            // 关闭资源
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){
            }// 什么都不做
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        return mainQData;
    }

    private HashMap<Integer, List<String>> getResFromSubQ() {
        HashMap<Integer, List<String>> subQData = new HashMap<>();
        Connection conn = null;
        Statement stmt = null;
        try{
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);
            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            // 执行查询
            System.out.println(" 实例化Statement对象...");
            stmt = conn.createStatement();
            String sql = "SELECT main_id, answer FROM sub_questions";
            ResultSet rs = stmt.executeQuery(sql);
            // 每道大题
            while(rs.next()){
                // 通过字段检索
                int mainId  = rs.getInt("main_id");
                String answer = rs.getString("answer");
                // 输出数据
                if (! subQData.containsKey(mainId)) {
                    subQData.put(mainId, new ArrayList<>());
                }
                subQData.get(mainId).add(answer);
            }
            // 完成后关闭
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally{
            // 关闭资源
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){
            }// 什么都不做
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        return subQData;
    }
}
