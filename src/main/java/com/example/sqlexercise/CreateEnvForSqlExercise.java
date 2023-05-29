package com.example.sqlexercise;

import com.example.sqlexercise.config.YmlProperties;
import com.example.sqlexercise.lib.*;
import com.example.sqlexercise.serviceImpl.MyAsyncService;
import com.fasterxml.uuid.Generators;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Image;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Slf4j(topic = "com.example.sqlexercise.CreateEnvForSqlExercise")
@Component
@PropertySource(value = "classpath:application.yml")
public class CreateEnvForSqlExercise implements ApplicationRunner {

    private final SqlDatabasePool pool;
    private final MyAsyncService myAsyncService;
    private final String NAMESPACE_URL = "6ba7b811-9dad-11d1-80b4-00c04fd430c8";
    private final String SALT = "sqlexercise";

    private YmlProperties ymlProperties;

    @Autowired
    public CreateEnvForSqlExercise(SqlDatabasePool pool, MyAsyncService myAsyncService, YmlProperties ymlProperties) {
        this.pool = pool;
        this.myAsyncService = myAsyncService;
        this.ymlProperties = ymlProperties;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("ApplicationRunner接口");
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
        if (!System.getProperty("os.name").startsWith("Linux")) {
            // 若不是Linux系统，则说明是在本地开发环境中，即Windows或MacOS，所以在resources文件夹下读取
            try {
                Path dbPath = Paths.get("src/main/resources/examDataFiles");
                if (!Files.exists(dbPath)) {    // 不存在则创建该文件夹
                    Files.createDirectory(dbPath);
                } else {
                    log.info("examDataFiles exists.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // 若是Linux系统，则说明以jar包形式部署到了生产环境中
            // do nothing, 因为会确保部署时所需文件被一并上传
        }
        // 配置远端服务器的Docker，初始化sql的运行环境
        createEnv();
        // Container mysql-1-0 uses password 77d3bc2d-d446-5b79-b64e-95cfa00dfd5c
        // Container mysql-1-1 uses password 33b60f2b-1dbb-5fe0-81a0-98252b6414bb
        // Container oceanbase-1-0 uses password 7e603b2c-8944-52f9-b754-f8b84f8c84dc
    }

    /**
     * 创建运行所需的docker容器
     */
    private void createEnv() throws Exception {
        ArrayList<DockerServer> dockerServerArrayList = this.pool.getDockerServers();
        for (DockerServer dockerServer : dockerServerArrayList) {

            SqlServer server = new SqlServer(dockerServer.getHost());
            dockerServer.connectDocker();

            // 检查 MySQL 镜像
            Image image = dockerServer.getDockerImageByReference(Constants.DockerRelated.MYSQL_IMAGE);
            if (image == null) {
                dockerServer.pullImageByRepository(Constants.DockerRelated.MYSQL_IMAGE_NAME, Constants.DockerRelated.MYSQL_IMAGE_TAG);
            }
            log.info("After checking, image " + Constants.DockerRelated.MYSQL_IMAGE + " exists.");
            image = dockerServer.getDockerImageByReference(Constants.DockerRelated.OCEANBASE_IMAGE);
            if (image == null) {
                dockerServer.pullImageByRepository(Constants.DockerRelated.OCEANBASE_IMAGE_NAME, Constants.DockerRelated.OCEANBASE_IMAGE_TAG);
            }
            log.info("After checking, image " + Constants.DockerRelated.OCEANBASE_IMAGE + " exists.");

            // 若生成容器信息
            UUID namespace = Generators.nameBasedGenerator(UUID.fromString(NAMESPACE_URL)).generate(SALT);
            List<DockerContainer> dockerMysqlContainers = createContainerInfos(dockerServer, namespace, Constants.DockerRelated.MYSQL_IMAGE_NAME,
                            Constants.DockerRelated.MYSQL_CONTAINER_DEFAULT_PORT, 1);
            List<DockerContainer> dockerOceanbaseContainers = createContainerInfos(dockerServer, namespace, Constants.DockerRelated.OCEANBASE,
                            Constants.DockerRelated.OCEANBASE_CONTAINER_DEFAULT_PORT, 1);

            // 准备docker中的数据库容器实例
            // 对于MySQL容器，每次启动都重新创建
            if (ymlProperties.isNeedMysql()) {
                prepareDatabaseContainerInstance(dockerMysqlContainers, dockerServer, ymlProperties.isRecreateMysql(), server,
                        Constants.DockerRelated.DATABASE_CONTAINER_TYPE.MySQL);
            }
            // 对于OceanBase容器，因为其数据量较大，不宜重新创建
            if (ymlProperties.isNeedOceanbase()) {
                prepareDatabaseContainerInstance(dockerOceanbaseContainers, dockerServer, ymlProperties.isRecreateOceanbase(), server,
                        Constants.DockerRelated.DATABASE_CONTAINER_TYPE.OceanBase);
            }
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

    /**
     * 准备好数据库容器实例。包括检查容器是否已存在，重新创建容器，启动容器并初始化
     *
     * @param recreate              为true表示需要重新创建，false表示不需要
     * @param databaseContainerType 数据库容器类型，目前包括MySQL和OceanBase
     */
    private void prepareDatabaseContainerInstance(List<DockerContainer> containerList, DockerServer dockerServer,
                                                  boolean recreate, SqlServer server,
                                                  Constants.DockerRelated.DATABASE_CONTAINER_TYPE databaseContainerType) throws Exception {
        // 根据容器信息，为每个容器创建环境
        for (DockerContainer container : containerList) {
            // 获取同名容器
            Container container1 = dockerServer.getDockerContainerByName(container.getName());
            // 判断同名容器是否存在
            if (container1 == null) {
                // 同名容器不存在
                log.info("Container " + container.getName() + " does not exist.");
                createDatabaseContainerInstance(containerList, dockerServer, server, databaseContainerType);
            } else {
                // 同名容器存在，则判断是否需要删除后重新创建
                if (recreate) {
                    // 需要重新创建
                    // 1.停止该容器
                    if ((container1.getState() == null && container1.getStatus().toLowerCase().startsWith("up")) ||
                            (container1.getState() != null && container1.getState().toLowerCase().equals("running"))) {
                        dockerServer.stopDockerContainerById(container1.getId());
                    }
                    // 2.删除该容器
                    dockerServer.removeDockerContainerById(container1.getId());
                    log.info("Container " + container.getName() + " has been removed.");
                    // 3.重新创建
                    createDatabaseContainerInstance(containerList, dockerServer, server, databaseContainerType);
                } else {
                    // 不需要重新创建
                    if (!container1.getState().toLowerCase().equals("running")) {
                        // 若容器未启动，则启动容器
                        dockerServer.startDockerContainer(container.getName());
                    }
                    log.info("Container " + container.getName() + " already exists and it's running.");
                }
            }
        }
    }

    private void createDatabaseContainerInstance(List<DockerContainer> containerList, DockerServer dockerServer, SqlServer server,
                                                 Constants.DockerRelated.DATABASE_CONTAINER_TYPE databaseContainerType) throws Exception {
        for (DockerContainer container : containerList) {
            // 检查可用端口
            try {
                server.detectServerPort(true, container.getPort(), container.getPort());
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 创建容器
            SqlDatabase sqlDatabase;
            switch (databaseContainerType) {
                case MySQL:
                    dockerServer.createDockerContainerForMysql57(container.getName(), container.getPassword(), container.getPort());
                    sqlDatabase = this.pool.getSqlDatabase("", "mysql", dockerServer.getId(), container.getIndex());
                    myAsyncService.asyncInitMysqlContainer(dockerServer, container, sqlDatabase);
                    break;
                case OceanBase:
                    dockerServer.createDockerContainerForOceanbase(container.getName(), container.getPassword(), container.getPort());
                    sqlDatabase = this.pool.getSqlDatabase("", "oceanbase", dockerServer.getId(), container.getIndex());
                    myAsyncService.asyncInitOceanbaseContainer(dockerServer, container, sqlDatabase);
                    break;
            }
        }
    }
}
