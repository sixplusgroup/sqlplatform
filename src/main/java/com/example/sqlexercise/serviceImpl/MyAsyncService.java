package com.example.sqlexercise.serviceImpl;

import com.example.sqlexercise.lib.DockerContainer;
import com.example.sqlexercise.lib.DockerServer;
import com.example.sqlexercise.lib.SqlDatabase;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class MyAsyncService {

    //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Async("asyncTaskExecutor")
    public void myAsyncMethod(){
        System.out.println(Thread.currentThread().getName()+"---> enter async method!");
        try{
            Thread.sleep(5000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+"---> end of async method!");
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
    public void asyncInit(DockerServer dockerServer, DockerContainer container, SqlDatabase sqlDatabase) throws Exception{
        dockerServer.startDockerContainer(container.getName());
        //连接数据库
        sqlDatabase.connect("SHOW DATABASES;", 5);
        if(!sqlDatabase.isConnected()){
            throw new Exception("Connection Error");
        }
        sqlDatabase.createUser("CREATE USER 'sqlexercise'@'%' IDENTIFIED BY '"+container.getPassword()+"';\n" +
                "GRANT SELECT ON *.* TO 'sqlexercise'@'%';\n" +
                "FLUSH PRIVILEGES;", 1);
    }

}
