package com.example.sqlexercise.config;

import com.example.sqlexercise.lib.SqlDatabasePool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collections;

@Configuration
public class PoolConfig {

    private final DockerConfig dockerConfig;

    public PoolConfig(DockerConfig dockerConfig) {
        this.dockerConfig = dockerConfig;
    }

    @Bean
    SqlDatabasePool sqlDatabasePool(){
        ArrayList<String> drivers = new ArrayList<>();
        drivers.add("mysql");
        drivers.add("oceanbase");
        return new SqlDatabasePool(dockerConfig, drivers);
    }

}
