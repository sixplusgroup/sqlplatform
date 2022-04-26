package com.example.sqlexercise.config;

import com.example.sqlexercise.lib.DockerServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
public class DockerConfig {

    private ArrayList<DockerServer> dockerServers;

    @Autowired
    public DockerConfig(){
        this.dockerServers = new ArrayList<>();
        DockerServer dockerServer = new DockerServer("1", "localhost", 2375, "tcp", "", 1);
        this.dockerServers.add(dockerServer);
    }

    public ArrayList<DockerServer> getDockerServers(){
        return this.dockerServers;
    }
}
