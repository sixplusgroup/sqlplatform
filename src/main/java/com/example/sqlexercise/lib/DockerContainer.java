package com.example.sqlexercise.lib;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DockerContainer {
    private int index;
    private String name;
    private String password;
    private int port;
}
