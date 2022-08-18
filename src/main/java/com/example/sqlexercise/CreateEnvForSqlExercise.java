package com.example.sqlexercise;

import com.example.sqlexercise.lib.*;
import com.example.sqlexercise.serviceImpl.MyAsyncService;
import com.fasterxml.uuid.Generators;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class CreateEnvForSqlExercise implements ApplicationRunner {

    private final SqlDatabasePool pool;
    private final MyAsyncService myAsyncService;
    private final String NAMESPACE_URL = "6ba7b811-9dad-11d1-80b4-00c04fd430c8";
    private final String SALT = "sqlexercise";

    @Autowired
    public CreateEnvForSqlExercise(SqlDatabasePool pool, MyAsyncService myAsyncService) {
        this.pool = pool;
        this.myAsyncService = myAsyncService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception{
        System.out.println(args);
        System.out.println("ApplicationRunner接口！");
        /*
        SqlDatabaseConfig config = new SqlDatabaseConfig();
        config.tags = new HashMap<>();
        config.tags.put("schemaName", "test");
        config.tags.put("driver", "mysql");
        config.tags.put("server", "1");
        config.tags.put("index", 1);
        SqlDatabase sqlDatabase = new SqlDatabase(config);
        sqlDatabase.testConnect();
        */
        //检查大题数据文件夹，里面包含schema的建表语句
        try {
            Path dbPath = Paths.get("src/main/resources/examDataFiles");
            if (!Files.exists(dbPath)) {
                Files.createDirectory(dbPath);
            }else {
                System.out.println("examDataFiles exists");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //配置远端服务器的Docker，初始化sql的运行环境
        createEnv(true);
        //Container mysql-1-0 uses password 77d3bc2d-d446-5b79-b64e-95cfa00dfd5c
        //Container mysql-1-1 uses password 33b60f2b-1dbb-5fe0-81a0-98252b6414bb
    }

    /**
     *
     * @param Recreate 是否要重新创建容器
     * @throws Exception
     */
    private void createEnv(boolean Recreate) throws Exception{
        ArrayList<DockerServer> dockerServerArrayList = this.pool.getDockerServers();
        for(DockerServer dockerServer : dockerServerArrayList){
            SqlServer server = new SqlServer(dockerServer.getHost());
            dockerServer.connectDocker();
            List<Image> images = dockerServer.getDockerImageByReference("mysql:5.7");
            if(images.size()==0){
                //没有mysql镜像，先拉取镜像
                dockerServer.pullImageByRepository("mysql", "5.7");
            }
            System.out.println("After checking, Image mysql:5.7 exists");
            //生成容器信息
            UUID namespace = Generators.nameBasedGenerator(UUID.fromString(NAMESPACE_URL)).generate(SALT);
            ArrayList<DockerContainer> dockerContainers = new ArrayList<>();
            for(int i = 0; i < dockerServer.getContainer(); i++){
                String name = "mysql-"+dockerServer.getId()+"-"+i;
                String password = Generators.nameBasedGenerator(namespace).generate(name).toString();
                int port = 3310+i;
                dockerContainers.add(new DockerContainer(i, name, password, port));
                System.out.println("Container "+name+" uses password "+password);
            }
            //为每个容器创建环境
            for(DockerContainer container : dockerContainers){
                //获取同名容器
                Container container1 = dockerServer.getDockerContainerByName(container.getName());
                //同名容器存在，且无需删除
                if(container1!=null && !Recreate){
                    if(container1.getState().toLowerCase().equals("running")){
                        System.out.println("Container "+container.getName()+" already exists and it's running");
                        break;
                    }
                }
                //同名容器存在，且需要删除
                if(container1!=null){
                    if(container1.getState().toLowerCase().equals("running")){
                        dockerServer.stopDockerContainerById(container1.getId());
                    }
                    dockerServer.removeDockerContainerById(container1.getId());
                    System.out.println("Container "+container.getName()+" has been removed");
                }else{
                    //同名容器不存在
                    System.out.println("Container "+container.getName()+" does not exist");
                }
                //检查端口
                try {
                    server.detectServerPort(true, container.getPort(), container.getPort());
                }catch (Exception e){
                    e.printStackTrace();
                }
                dockerServer.createDockerContainerForMysql57(container.getName(), container.getPassword(), container.getPort());
                //以下部分已改成Spring异步执行，不阻塞主线程
                SqlDatabase sqlDatabase = this.pool.getSqlDatabase("", "mysql", dockerServer.getId(), container.getIndex());
                myAsyncService.asyncInit(dockerServer, container, sqlDatabase);
            }
        }
    }

}
