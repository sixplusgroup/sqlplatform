package com.example.sqlexercise.lib;

public class DockerContainer {

    int index;
    String name;
    String password;
    int port;

    public DockerContainer(int index, String name, String password, int port){
        this.index = index;
        this.name = name;
        this.password = password;
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }
}
