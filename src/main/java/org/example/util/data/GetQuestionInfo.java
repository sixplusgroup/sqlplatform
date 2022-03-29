package org.example.util.data;

import org.example.util.MyFileReader;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.example.util.Constants.*;
import static org.example.util.Constants.PWD;

/**
 * @author shenyichen
 * @date 2022/3/28
 **/
public class GetQuestionInfo {

    public static List<String> getEnvSqls(Integer mainId) {
        List<String> sqls = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PWD);
            String sql = "SELECT db_path FROM main_questions where id=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, mainId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                String dbPath = rs.getString("db_path");
                sqls.add(MyFileReader.readFile(prefix + dbPath));
            }
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
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
        return sqls;
    }
}
