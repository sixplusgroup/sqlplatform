package com.example.sqlexercise.lib;

import com.example.sqlexercise.driver.Client;
import com.example.sqlexercise.driver.MysqlClient;
import com.mysql.cj.jdbc.Driver;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class SqlDatabase {

    private String id;
    private String name;
    private String driver; //"mysql"
    private SqlDatabaseConfig config;
    private Client client;
    private SqlDatabase root;
    private Timer timer;
    public int consecutiveErrors;
    public int lockedQueries;

    public SqlDatabase(SqlDatabaseConfig config, SqlDatabase root){
        this.id = UUID.randomUUID().toString();
        this.name = config.tags.get("schemaName").toString()+"-"+config.tags.get("driver").toString()+"-"+config.tags.get("server").toString()+"-"+config.tags.get("index").toString();
        this.root = root;
        this.config = config;
        this.consecutiveErrors = 0;
        this.lockedQueries = 0;
        this.driver = config.tags.get("driver").toString();
        this.timer = null;
        this.client = null;

    }

    public SqlDatabase(SqlDatabaseConfig config){
        this.id = UUID.randomUUID().toString();
        this.name = config.tags.get("schemaName").toString()+"-"+config.tags.get("driver").toString()+"-"+config.tags.get("server").toString()+"-"+config.tags.get("index").toString();
        this.config = config;
        this.consecutiveErrors = 0;
        this.lockedQueries = 0;
        this.driver = config.tags.get("driver").toString();
        this.timer = null;
        this.client = null;
    }

    private int lock(){
        int errors = this.consecutiveErrors;
        this.lockedQueries = errors * errors;
        return this.lockedQueries;
    }

    public int unlock(){
        if(this.lockedQueries>0){
            this.lockedQueries--;
        }
        return this.lockedQueries;
    }

    private void startTimer(){
        if(this.timer==null) {
            this.timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    disconnect();
                }
            };
            timer.schedule(task, 3 * 60 * 1000);
        }
    }

    private void stopTimer(){
        if(this.timer != null){
            this.timer.cancel();
            this.timer = null;
        }
    }

    private boolean isConnected(){
        if(this.client==null){
            return false;
        }else{
            return true;
        }
    }

    private void disconnect(){
        if(!isConnected()){
            return;
        }
        try {
            this.client.destroy();
            System.out.println("Database disconnected");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Failed to disconnect");
        }
        this.client = null;
        this.stopTimer();
    }

    private void connect(int maxRetryTimes){
        if (isConnected()){
            return;
        }
        for(int i=1; i<= maxRetryTimes; i++){
            try {
                if (i == 1) {
                    System.out.println("Try to connect....");
                    if (this.driver.equals("mysql")) {
                        this.client = new MysqlClient();
                    }
                    this.client.init(this.config);
                    startTimer();
                } else {
                    System.out.println("Retry to connect in " + 2 * i + " seconds...");
                    try {
                        TimeUnit.SECONDS.sleep(2 * i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Database connected");
                return;
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("Fail to connect");
            }
        }
        this.client = null;
        stopTimer();
    }

    private ResultOfTask runQuery(String sqlText){
        stopTimer();
        if(!isConnected()){
            connect(1);
        }
        ResultOfTask result = this.client.runQuery(sqlText);
        if(result.error!=null){
            this.consecutiveErrors++;
        }else{
            this.consecutiveErrors = 0;
        }
        lock();
        startTimer();
        return result;
    }

    public void pretask(String schemaConstructor){
        if(schemaConstructor==null){
            return;
        }
        String sqlText = this.client.getSchemaSql(this.config.tags.get("schemaName").toString());
        ResultOfTask result = this.root.runQuery(sqlText);
        if(result.error==null && result.sheet.size()==0){
            sqlText = this.client.initSchemaSql(this.config.tags.get("schemaName").toString()) + schemaConstructor;
            this.root.runQuery(sqlText);
        }
    }

    public ResultOfTask task(String sqlText, int maxRetryTimes){
        for(int i=1; i<=maxRetryTimes; i++){
            if(i==1){
                System.out.println("Try to execute SQL task");
            }else{
                System.out.println("Retry to execute SQL task in "+2*i+" seconds");
                try {
                    TimeUnit.SECONDS.sleep(2 * i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            ResultOfTask result = this.runQuery(sqlText);
            if(result.error==null || i>=maxRetryTimes){
                return result;
            }
        }
        return null;
    }

    public void posttask(){
        //TODO

    }

    public void testConnect(){
        try {
            Driver driver = new com.mysql.cj.jdbc.Driver();
            System.out.println("正在连接远端mysql数据库.....");
            String url = "jdbc:mysql://localhost:3306/sqlexam?useUnicode=true&useJDBCComliantTimezoneShift=true&serverTimezone=UTC&characterEncoding=utf8";
            Properties info = new Properties();
            info.put("user", "root");
            info.put("password", "123456");
            Connection connection = driver.connect(url, info);
            String sql = "select b.* from batches b";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            int columnCount = rs.getMetaData().getColumnCount();
            while(rs.next()) {
                for(int i=1;i<=columnCount;i++){
                    System.out.print(rs.getString(i)+" ");
                }
                System.out.println();
            }
            rs.close();
            statement.close();
            connection.close();
            System.out.println("数据库连接已关闭");
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
