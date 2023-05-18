package com.example.sqlexercise.lib;

import com.example.sqlexercise.driver.Client;
import com.example.sqlexercise.driver.MysqlClient;
import com.example.sqlexercise.driver.OceanbaseClient;
import com.mysql.cj.jdbc.Driver;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j(topic = "com.example.sqlexercise.lib.SqlDatabase")
public class SqlDatabase {

    private String id;
    private String name;
    private String driver; //"mysql", "oceanbase"
    private SqlDatabaseConfig config;
    private Client client;
    private SqlDatabase root;
    private Timer timer;
    public int consecutiveErrors;
    public int lockedQueries;

    public SqlDatabase(@NotNull SqlDatabaseConfig config, SqlDatabase root) {
        this.id = UUID.randomUUID().toString();
        this.name = config.tags.get("schemaName").toString() + "-" + config.tags.get("driver").toString() + "-" + config.tags.get("server").toString() + "-" + config.tags.get("index").toString();
        this.root = root;
        this.config = config;
        this.consecutiveErrors = 0;
        this.lockedQueries = 0;
        this.driver = config.tags.get("driver").toString();
        this.timer = null;
        this.client = null;

    }

    public SqlDatabase(@NotNull SqlDatabaseConfig config) {
        this.id = UUID.randomUUID().toString();
        this.name = config.tags.get("schemaName").toString() + "-" + config.tags.get("driver").toString() + "-" + config.tags.get("server").toString() + "-" + config.tags.get("index").toString();
        this.config = config;
        this.consecutiveErrors = 0;
        this.lockedQueries = 0;
        this.driver = config.tags.get("driver").toString();
        this.timer = null;
        this.client = null;
    }

    private int lock() {
        int errors = this.consecutiveErrors;
        this.lockedQueries = errors * errors;
        return this.lockedQueries;
    }

    public int unlock() {
        if (this.lockedQueries > 0) {
            this.lockedQueries--;
        }
        return this.lockedQueries;
    }

    private void startTimer() {
        if (this.timer == null) {
            this.timer = new Timer();
            // 启动定时任务，3min后断开连接
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    disconnect();
                }
            };
            timer.schedule(task, 3 * 60 * 1000);
        }
    }

    private void stopTimer() {
        if (this.timer != null) {
            this.timer.cancel();
            this.timer = null;
        }
    }

    public boolean isConnected() {
        return this.client != null;
    }

    private void disconnect() {
        if (!isConnected()) {
            return;
        }
        try {
            this.client.destroy();
            log.info("Database disconnected");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Failed to disconnect");
        }
        this.client = null;
        this.stopTimer();
    }

    public void connect(String testText, int maxRetryTimes) {
        if (isConnected()) {
            return;
        }
        // 重复尝试连接
        for (int i = 1; i <= maxRetryTimes; i++) {
            try {
                if (i == 1) {
                    log.info("Try to connect " + this.driver + "....");
                    if (this.driver.equals("mysql")) {
                        this.client = new MysqlClient();
                    } else if (this.driver.equals("oceanbase")) {
                        this.client = new OceanbaseClient();
                    }
                    this.client.init(this.config);
                    startTimer();
                } else {
                    log.info("Retry to connect " + this.driver + " in " + 2 * i + " seconds...");
                    try {
                        TimeUnit.SECONDS.sleep(2 * i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (testText != null) {
                    runQuery(testText);
                }
                log.info("Database " + this.driver + " connected");
                return;
            } catch (Exception e) {
                e.printStackTrace();
                log.info("Fail to connect " + this.driver);
            }
        }
        this.client = null;
        stopTimer();
    }

    private ResultOfTask runQuery(String sqlText) {
        stopTimer();
        if (!isConnected()) {
            connect(null, 1);
        }
        ResultOfTask result = this.client.runQuery(sqlText);
        if (result.error != null) {
            this.consecutiveErrors++;
        } else {
            this.consecutiveErrors = 0;
        }
        lock();
        startTimer();
        return result;
    }

    /**
     * 进行sql任务执行前的处理工作，主要是初始化题目用到的表
     */
    public void preTask(String schemaConstructor) {
        if (schemaConstructor == null) {
            return;
        }
        if (!isConnected()) {
            connect(null, 1);
        }
        String sqlText = this.client.getSchemaSql(this.config.tags.get("schemaName").toString());
        ResultOfTask result = this.root.runQuery(sqlText);
        if (result.error != null || result.sheet.size() == 0) {
            // 还未初始化表，先进行初始化
            sqlText = this.client.initSchemaSql(this.config.tags.get("schemaName").toString()) + schemaConstructor;
            this.root.createTable(sqlText);
        }
    }

    /**
     * sql任务执行完毕后的处理
     */
    public void postTask() {

    }

    public ResultOfTask task(String sqlText, int maxRetryTimes) {
        for (int i = 1; i <= maxRetryTimes; i++) {
            if (i == 1) {
                log.info("Try to execute SQL task. SQL: " + sqlText);
            } else {
                log.info("Retry to execute SQL task in " + 2 * i + " seconds");
                try {
                    TimeUnit.SECONDS.sleep(2 * i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            ResultOfTask result = this.runQuery(sqlText);
            if (result.error == null || i >= maxRetryTimes) {
                return result;
            }
        }
        return null;
    }

    public void createUser(String sqlText, int maxRetryTimes) {
        for (int i = 1; i <= maxRetryTimes; i++) {
            if (i == 1) {
                log.info("Try to execute " + this.driver + " createUser task");
            } else {
                log.info("Retry to execute " + this.driver + " createUser task in " + 2 * i + " seconds");
                try {
                    TimeUnit.SECONDS.sleep(2 * i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            boolean result = this.runCreate(sqlText);
            if (result) {
                log.info("Execute " + this.driver + " createUser task successfully!");
            }
            if (result || i >= maxRetryTimes) {
                return;
            }
        }
    }

    public boolean operateIndex(String sqlText) {
        log.info("Try to execute " + this.driver + " task. SQL: " + sqlText);
        stopTimer();
        if (!isConnected()) {
            connect(null, 1);
        }
        boolean result = this.client.operateIndex(sqlText);
        if (result) {
            log.info("Success. SQL: " + sqlText);
        } else {
            log.info("Fail. SQL: " + sqlText);
        }
        lock();
        startTimer();
        return result;
    }

    private boolean runCreate(String sqlText) {
        stopTimer();
        if (!isConnected()) {
            connect(null, 1);
        }
        boolean result = this.client.createUser(sqlText);
        if (result) {
            this.consecutiveErrors = 0;
        } else {
            this.consecutiveErrors++;
        }
        lock();
        startTimer();
        return result;
    }

    public void createDatabase(String sqlText) {
        log.info("Try to execute " + this.driver + " createDatabase task.");
        stopTimer();
        if (!isConnected()) {
            connect(null, 1);
        }
        this.client.createDatabase(sqlText);
        log.info("Execute " + this.driver + " createDatabase task successfully.");
        lock();
        startTimer();
    }

    public void createTable(String sqlText) {
        log.info("Try to execute " + this.driver + " createTable task.");
        stopTimer();
        if (!isConnected()) {
            connect(null, 1);
        }
        this.client.createTable(sqlText);
        log.info("Execute " + this.driver + " createTable task successfully.");
        lock();
        startTimer();
    }

    public void testObConnect() {
        try {
            String url = "jdbc:oceanbase://localhost:2881";
            String user = "root@sys#obcluster";
            String password = "";
            Class.forName("com.oceanbase.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);
            log.info("与OB集群的连接已经创建");
            connection.close();
            log.info("与OB集群的连接已关闭");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testConnect() {
        try {
            Driver driver = new com.mysql.cj.jdbc.Driver();
            log.info("正在连接远端mysql数据库.....");
            //77d3bc2d-d446-5b79-b64e-95cfa00dfd5c
            String url = "jdbc:mysql://localhost:3306/sqlexam?useUnicode=true&useJDBCComliantTimezoneShift=true&serverTimezone=UTC&characterEncoding=utf8";
            Properties info = new Properties();
            info.put("user", "root");
            info.put("password", "123456");
            Connection connection = driver.connect(url, info);
            String sql = "select b.* from batch b";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            int columnCount = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(rs.getString(i) + " ");
                }
                System.out.println();
            }
            rs.close();
            statement.close();
            connection.close();
            log.info("数据库连接已关闭");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
