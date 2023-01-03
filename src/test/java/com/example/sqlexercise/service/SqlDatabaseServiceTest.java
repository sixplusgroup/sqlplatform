package com.example.sqlexercise.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootTest
@Disabled
public class SqlDatabaseServiceTest {

    @Autowired
    private SqlDatabaseService sqlDatabaseService;

    @Test
    public void getSchemaConstructorByMainIdTest(){
        try {
            Path dbPath = Paths.get("../examDataFiles/upload_1a1059a102b4f59752d0fe0e0f972a84.sql");
            if (!Files.exists(dbPath)) {
                System.out.println("File of main question is not found!");
            }
            System.out.println( Files.readString(dbPath) );
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
