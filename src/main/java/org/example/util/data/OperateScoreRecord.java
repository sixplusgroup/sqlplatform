package org.example.util.data;

import org.example.util.vo.UpsertScoreRecordVO;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.example.util.Constants.*;
import static org.example.util.Constants.PWD;

/**
 * @author shenyichen
 * @date 2022/3/30
 **/
public class OperateScoreRecord {

    public static void upsertScoreRecord(UpsertScoreRecordVO data) {
        float score = existRecord(data);
        if (score == 0) {
            insertRecord(data);
        }
        else if (score < data.getScore()) {
            updateRecord(data);
        }
    }

    private static float existRecord(UpsertScoreRecordVO data) {
        float res = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PWD);
            String sql = "SELECT score FROM score_record " +
                    "where student_id=? and exam_id=? and main_id=? and sub_id=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, data.getStudentId());
            stmt.setString(2, data.getExamId());
            stmt.setInt(3, data.getMainId());
            stmt.setInt(4, data.getSubId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                res = rs.getFloat(1);
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
        return res;
    }

    private static void insertRecord(UpsertScoreRecordVO data) {
        Connection conn = null;
        PreparedStatement stmt = null;
        String sql = "insert into score_record(student_id, exam_id, main_id," +
                "sub_id, score, created_at, updated_at) values (?,?,?,?,?,?,?)";
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PWD);
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, data.getStudentId());
            stmt.setString(2, data.getExamId());
            stmt.setInt(3, data.getMainId());
            stmt.setInt(4, data.getSubId());
            stmt.setFloat(5, data.getScore());
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String d = sdf.format(date);
            stmt.setString(6, d);
            stmt.setString(7, d);
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

    private static void updateRecord(UpsertScoreRecordVO data) {
        Connection conn = null;
        PreparedStatement stmt = null;
        String sql = "update score_record set score=?, updated_at=? " +
                "where student_id=? and exam_id=? and main_id=? and sub_id=?";
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PWD);
            stmt = conn.prepareStatement(sql);
            stmt.setFloat(1, data.getScore());
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String d = sdf.format(date);
            stmt.setString(2,d);
            stmt.setString(3, data.getStudentId());
            stmt.setString(4, data.getExamId());
            stmt.setInt(5, data.getMainId());
            stmt.setInt(6, data.getSubId());
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
}
