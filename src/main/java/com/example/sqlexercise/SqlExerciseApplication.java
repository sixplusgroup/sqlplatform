package com.example.sqlexercise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class SqlExerciseApplication {

    public static void main(String[] args) {
        SpringApplication.run(SqlExerciseApplication.class, args);
    }

}
