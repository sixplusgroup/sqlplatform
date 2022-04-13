package com.example.sqlexercise.lib;

import java.util.ArrayList;

public class DockerServer {
    String id;
    String host;
    int port;
    String protocol;
    String certPath;
    int container;

    public DockerServer(){
        this.id = "1";
        this.host = "localhost";
        this.port = 2375;
        this.protocol = "https";
        this.certPath = "";
        this.container = 2;
    }

    public static ArrayList<DockerServer> getDockerServers(){
        ArrayList<DockerServer> dockerServers = new ArrayList<>();
        DockerServer dockerServer = new DockerServer();
        dockerServers.add(dockerServer);
        return dockerServers;
    }

}
