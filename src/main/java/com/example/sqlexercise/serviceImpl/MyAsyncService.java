package com.example.sqlexercise.serviceImpl;

import com.example.sqlexercise.lib.DockerContainer;
import com.example.sqlexercise.lib.DockerServer;
import com.example.sqlexercise.lib.SqlDatabase;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MyAsyncService {

    @Async
    public void myAsyncMethod(){
        System.out.println("---> enter async method!");
        try{
            Thread.sleep(5000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("---> end of async method!");
    }

    @Async
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
