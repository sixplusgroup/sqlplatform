package com.example.sqlexercise.serviceImpl;

import com.example.sqlexercise.config.YmlProperties;
import com.example.sqlexercise.lib.Constants;
import com.example.sqlexercise.lib.DockerContainer;
import com.example.sqlexercise.lib.DockerServer;
import com.example.sqlexercise.lib.SqlDatabase;
import com.example.sqlexercise.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class MyAsyncService implements AsyncService {

    private YmlProperties ymlProperties;

    @Autowired
    public MyAsyncService(YmlProperties ymlProperties) {
        this.ymlProperties = ymlProperties;
    }

    @Async("asyncTaskExecutor")
    public void myAsyncMethod() {
        System.out.println(Thread.currentThread().getName() + "---> enter async method!");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "---> end of async method!");
    }

    /*
    @Async("asyncTaskExecutor")
    @Scheduled(fixedRate = 2000)
    public void testScheduleTask(){
        try{
            Thread.sleep(6000);
            System.out.println("任务1使用"+Thread.currentThread().getName()+"-"+simpleDateFormat.format(new Date()));
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    */

    //@Async("asyncTaskExecutor")
    //@Scheduled(cron = "*/2 * * * * ?")
    //public void testAsync(){
    //    try{
    //        Thread.sleep(1000);
    //        System.out.println("任务2使用"+Thread.currentThread().getName()+"-"+simpleDateFormat.format(new Date()));
    //    }catch (InterruptedException e){
    //        e.printStackTrace();
    //    }
    //}

    @Async("asyncTaskExecutor")
    public void asyncInitMysqlContainer(DockerServer dockerServer, DockerContainer container, SqlDatabase sqlDatabase) throws Exception {
        // 启动容器
        dockerServer.startDockerContainer(container.getName());
        // 等待90s以便容器内mysql启动完成初始化
        try {
            TimeUnit.SECONDS.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 连接数据库
        sqlDatabase.connect("SHOW DATABASES;", 5);
        if (!sqlDatabase.isConnected()) {
            throw new Exception("Connection Error");
        }
        // 创建用户，仅授予查询权限
        sqlDatabase.createUser("CREATE USER 'sqlexercise'@'%' IDENTIFIED BY '" + container.getPassword() + "';\n" +
                "GRANT SELECT ON *.* TO 'sqlexercise'@'%';\n" +
                "FLUSH PRIVILEGES;", 1);
    }

    @Async("asyncTaskExecutor")
    public void asyncInitOceanbaseContainer(DockerServer dockerServer, DockerContainer container, SqlDatabase sqlDatabase) throws Exception {
        // 启动容器
        dockerServer.startDockerContainer(container.getName());
        // 等待4*60s以便容器内oceanbase启动完成初始化
        try {
            TimeUnit.SECONDS.sleep(90);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 连接OceanBase
        sqlDatabase.connect("SHOW DATABASES;", 5);
        if (!sqlDatabase.isConnected()) {
            throw new Exception("Connection Error");
        }
        // 创建用户，并授予查询、创建删除索引权限
        sqlDatabase.createUser("CREATE USER 'sqlexercise'@'%' IDENTIFIED BY '" + container.getPassword() + "';\n" +
                "GRANT SELECT, INDEX ON *.* TO 'sqlexercise'@'%';\n", 1);
        // 创建题目所需数据库
        sqlDatabase.createDatabase("CREATE DATABASE index_test;");
        // 创建题目所需表
        for (int i = 0; i < ymlProperties.getOceanbaseTableDuplicateNum(); i++) {
            sqlDatabase.createTable("create table index_test.app_user_" + i + "(\n" +
                    "id int auto_increment primary key,\n" +
                    "name varchar(10),\n" +
                    "email varchar(30),\n" +
                    "phone varchar(15),\n" +
                    "gender tinyint(1),\n" +
                    "passwd varchar(20),\n" +
                    "birthday datetime,\n" +
                    "province varchar(10)\n" +
                    ");");
        }
    }
}
