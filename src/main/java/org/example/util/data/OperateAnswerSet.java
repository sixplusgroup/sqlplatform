package org.example.util.data;

import org.example.util.po.AnswerPO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.example.util.Constants.*;

/**
 * @author shenyichen
 * @date 2022/3/28
 **/
public class OperateAnswerSet {

    public static void build() {
        buildAnswerSet(getAnswers());
    }

    public static List<String> getAnswers(Integer main_id, Integer sub_id) {
        List<String> answers = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PWD);
            String sql = "SELECT answer FROM answer_set where main_id=? and sub_id=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, main_id);
            stmt.setInt(2, sub_id);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                answers.add(rs.getString(1));
            }
            // 完成后关闭
            rs.close();
            stmt.close();
            conn.close();
        }
        catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }
        catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }
        finally {
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
        return answers;
    }

    public static void addAnswer(Integer mainId, Integer subId, String instrSql) {
        Connection conn = null;
        PreparedStatement stmt = null;
        String sql = "insert into answer_set(main_id, sub_id, answer) values (?,?,?)";
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PWD);
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, mainId);
            stmt.setInt(2, subId);
            stmt.setString(3, instrSql);
            stmt.executeUpdate();
            stmt.close();
            conn.close();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
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
    }

    private static List<AnswerPO> getAnswers() {
        List<AnswerPO> answers = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        try{
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);
            // 打开链接
            conn = DriverManager.getConnection(DB_URL,USER,PWD);
            // 执行查询
            stmt = conn.createStatement();
            String sql = "SELECT id, main_id, answer FROM sub_questions";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                answers.add(new AnswerPO(null, rs.getInt("main_id"),
                        rs.getInt("id"), rs.getString("answer")));
            }
            // 完成后关闭
            rs.close();
            stmt.close();
            conn.close();
        }
        catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }
        catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }
        finally{
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
        return answers;
    }

    private static void buildAnswerSet(List<AnswerPO> answers) {
        Connection conn = null;
        PreparedStatement stmt = null;
        String sql = "insert into answer_set(main_id, sub_id, answer) values (?,?,?)";
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PWD);
            stmt = conn.prepareStatement(sql);
            for (AnswerPO answerPO: answers) {
                stmt.setInt(1, answerPO.getMain_id());
                stmt.setInt(2, answerPO.getSub_id());
                stmt.setString(3, answerPO.getAnswer());
                stmt.executeUpdate();
            }
            stmt.close();
            conn.close();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
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
    }

    public static void main(String[] args) {
        List<String> answers = getAnswers(1, 10);
        System.out.println(String.join("\n", answers));
    }
}
