package com.example.sqlexercise.lib;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.*;
import com.github.dockerjava.core.DockerClientBuilder;
import lombok.Getter;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j(topic = "com.example.sqlexercise.lib.DockerServer")
@Getter
public class DockerServer {
    private String id;
    private String host;
    private int port;
    private String protocol;
    private String certPath;
    private int container;
    private DockerClient client;

    public DockerServer(String id, String host, int port, String protocol, String certPath, int container) {
        this.id = id;
        this.host = host;
        this.port = port;
        this.protocol = protocol;
        this.certPath = certPath;
        this.container = container;
        this.client = null;
    }

    public void connectDocker() {
        this.client = DockerClientBuilder.getInstance(this.protocol + "://" + this.host + ":" + this.port).build();
        log.info("Docker " + this.host + " 已连接！");
    }

    public Image getDockerImageByReference(String reference) throws NullPointerException {
        if (this.client == null) {
            throw new NullPointerException("还未连接Docker");
        }
        List<Image> images = this.client.listImagesCmd().exec();
        for (Image image : images) {
            for (String tag : image.getRepoTags()) {
                if (tag.equals(reference)) {
                    return image;
                }
            }
        }
        return null;
    }

    public void pullImageByRepository(String repository, String tag) {
        try {
            log.info("Please wait! Docker is starting to pull image " + repository + ":" + tag);
            this.client.pullImageCmd(repository).withTag(tag).start().awaitCompletion();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("Image " + repository + ":" + tag + " is pulled successfully!");
    }

    public Container getDockerContainerByName(String name) {
        List<Container> containers = this.client.listContainersCmd().withShowAll(true).exec();
        for (Container container : containers) {
            if (container.getNames()[0].equals("/" + name)) {
                return container;
            }
        }
        return null;
    }

    public void removeDockerContainerById(String Id) {
        this.client.removeContainerCmd(Id).exec();
    }

    public void createDockerContainerForMysql57(String name, String password, int port) {
        HostConfig hostConfig = new HostConfig();
        PortBinding portBinding = new PortBinding(Ports.Binding.bindPort(port), ExposedPort.tcp(3306));
        hostConfig.withPortBindings(portBinding);
        List<String> cmd = new ArrayList<>();
        cmd.add("--lower_case_table_names=1");
        cmd.add("--max_connections=1024");
        CreateContainerResponse response = this.client.createContainerCmd("mysql:5.7").withName(name)
                .withHostConfig(hostConfig)
                .withEnv("MYSQL_ROOT_PASSWORD=" + password).withCmd(cmd).exec();
        log.info(name + " container " + response.getId() + " is created successfully!");
    }

    public void createDockerContainerForRedis(String name, String password, int port) {
        // TODO 创建redis容器
    }

    public void startDockerContainer(String containerName) {
        //根据源码，该方法没有response，所以无法精准地知道容器内的镜像什么时候启动完成
        this.client.startContainerCmd(containerName).exec();
        //等待90s以便容器内的镜像启动
        try {
            TimeUnit.SECONDS.sleep(90);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("Container " + containerName + " is started!");
    }

    public void stopDockerContainerById(String Id) {
        this.client.stopContainerCmd(Id).exec();
    }
}
