package com.example.sqlexercise.config;

import com.example.sqlexercise.lib.DockerServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

/**
 * Docker配置类
 */
@Configuration
public class DockerConfig {

    private ArrayList<DockerServer> dockerServers;

    @Autowired
    public DockerConfig(YmlProperties ymlProperties){
        this.dockerServers = new ArrayList<>();
        // TODO 在远程服务器上配置Docker，并使用certPath进行加密连接
        DockerServer dockerServer = new DockerServer("1", ymlProperties.getDockerHost(), ymlProperties.getDockerPort(), "tcp", "", 1);
        this.dockerServers.add(dockerServer);
    }

    public ArrayList<DockerServer> getDockerServers(){
        return this.dockerServers;
    }
}
