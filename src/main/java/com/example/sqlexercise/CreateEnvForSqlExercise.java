package com.example.sqlexercise;

import com.example.sqlexercise.lib.*;
import com.example.sqlexercise.serviceImpl.MyAsyncService;
import com.fasterxml.uuid.Generators;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Image;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Slf4j(topic = "com.example.sqlexercise.CreateEnvForSqlExercise")
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
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(args);
        log.info("ApplicationRunner接口！");
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
            if (!Files.exists(dbPath)) {    // 不存在则创建该文件夹
                Files.createDirectory(dbPath);
            } else {
                log.info("examDataFiles exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 配置远端服务器的Docker，初始化sql的运行环境，本地测试用本地数据库，先注释掉，不用docker
//        createEnv(true);
        //Container mysql-1-0 uses password 77d3bc2d-d446-5b79-b64e-95cfa00dfd5c
        //Container mysql-1-1 uses password 33b60f2b-1dbb-5fe0-81a0-98252b6414bb
    }

    /**
     * 创建运行所需的docker容器
     *
     * @param recreate 是否要重新创建容器
     */
    private void createEnv(boolean recreate) throws Exception {
        ArrayList<DockerServer> dockerServerArrayList = this.pool.getDockerServers();
        for (DockerServer dockerServer : dockerServerArrayList) {
            SqlServer server = new SqlServer(dockerServer.getHost());
            dockerServer.connectDocker();
            // 检查 MySQL 镜像
            Image image = dockerServer.getDockerImageByReference("mysql:5.7");
            if (image == null) {
                dockerServer.pullImageByRepository("mysql", "5.7");
            }
            log.info("After checking, image mysql:5.7 exists");
            // 检查 Redis 镜像
            image = dockerServer.getDockerImageByReference("redis:latest");
            if (image == null) {
                dockerServer.pullImageByRepository("redis", "latest");
            }
            log.info("After checking, image redis:latest exists");
            // 若生成容器信息
            UUID namespace = Generators.nameBasedGenerator(UUID.fromString(NAMESPACE_URL)).generate(SALT);
            List<DockerContainer> dockerMysqlContainers =
                    createContainerInfos(dockerServer, namespace, "mysql", 3310, 1);
            List<DockerContainer> dockerRedisContainers =
                    createContainerInfos(dockerServer, namespace, "redis", 6379, 1);
            // 检查dockerServer中现有容器实例情况
            checkExistingContainerInstance(dockerMysqlContainers, dockerServer, recreate);
            checkExistingContainerInstance(dockerRedisContainers, dockerServer, recreate);
            // 根据容器信息创建容器实例
            createMysqlContainerInstance(dockerMysqlContainers, dockerServer, server);
//            createRedisContainerInstance(dockerRedisContainers, dockerServer, server);
        }
    }

    private List<DockerContainer> createContainerInfos(DockerServer dockerServer, UUID namespace,
                                                       String imageName, int basePort, int num) {
        List<DockerContainer> result = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            String name = imageName + "-" + dockerServer.getId() + "-" + i;
            String password = Generators.nameBasedGenerator(namespace).generate(name).toString();
            int port = basePort + i;
            result.add(new DockerContainer(i, name, password, port));
            log.info("Container " + name + " uses password " + password);
        }
        return result;
    }

    private void checkExistingContainerInstance(List<DockerContainer> containerList, DockerServer dockerServer, boolean recreate) {
        // 根据容器信息，为每个容器创建环境
        for (DockerContainer container : containerList) {
            // 获取同名容器
            Container container1 = dockerServer.getDockerContainerByName(container.getName());
            // 同名容器存在，且无需删除，即无需重新创建
            if (container1 != null && !recreate) {
                if (container1.getState().toLowerCase().equals("running")) {
                    log.info("Container " + container.getName() + " already exists and it's running");
                    continue;
                }
            }
            // 同名容器存在，且需要删除，即需要重新创建
            if (container1 != null) {
                if (container1.getState().toLowerCase().equals("running")) {
                    dockerServer.stopDockerContainerById(container1.getId());
                }
                dockerServer.removeDockerContainerById(container1.getId());
                log.info("Container " + container.getName() + " has been removed");
            } else {
                //同名容器不存在
                log.info("Container " + container.getName() + " does not exist");
            }
        }
    }

    private void createMysqlContainerInstance(List<DockerContainer> containerList, DockerServer dockerServer,
                                              SqlServer server) throws Exception {
        for (DockerContainer container : containerList) {
            // 检查可用端口
            try {
                server.detectServerPort(true, container.getPort(), container.getPort());
            } catch (Exception e) {
                e.printStackTrace();
            }
            dockerServer.createDockerContainerForMysql57(container.getName(), container.getPassword(), container.getPort());
            //以下部分已改成Spring异步执行，不阻塞主线程
            SqlDatabase sqlDatabase =
                    this.pool.getSqlDatabase("", "mysql", dockerServer.getId(), container.getIndex());
            myAsyncService.asyncInit(dockerServer, container, sqlDatabase);
        }
    }

    private void createRedisContainerInstance(List<DockerContainer> containerList, DockerServer dockerServer,
                                              SqlServer server) {
        for (DockerContainer container : containerList) {
            // 检查可用端口
            try {
                server.detectServerPort(true, container.getPort(), container.getPort());
            } catch (Exception e) {
                e.printStackTrace();
            }
            dockerServer.createDockerContainerForRedis(container.getName(), container.getPassword(), container.getPort());
        }
    }

}
