package com.example.sqlexercise.config;

import com.example.sqlexercise.lib.ResultOfTask;
import com.example.sqlexercise.service.SqlDatabaseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

/**
 * @author shenyichen
 * @date 2022/12/27
 **/
@SpringBootTest
public class CacheTest {
    @Autowired
    private SqlDatabaseService sqlDatabaseService;

    @Test
    public void cacheTest() {
        System.out.println("=============第一次查询：=============");
        ResultOfTask result = sqlDatabaseService.getStandardAnswer(10, "mysql");
        System.out.println(result);
        System.out.println("=============第二次查询：=============");
        result = sqlDatabaseService.getStandardAnswer(10, "mysql");
        System.out.println(result);
        System.out.println("=============第三次查询：=============");
        try {
            //休眠7s后再次查询，主要是模拟Caffeine过期
            TimeUnit.SECONDS.sleep(7);
            result = sqlDatabaseService.getStandardAnswer(10, "mysql");
            System.out.println(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("=============第四次查询：=============");
        try {
            //休眠30s后再次查询，主要是模拟Redis过期
            TimeUnit.SECONDS.sleep(30);
            result = sqlDatabaseService.getStandardAnswer(10, "mysql");
            System.out.println(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
