package com.example.sqlexercise.lib;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DockerContainer {
    int index;
    String name;
    String password;
    int port;
}
