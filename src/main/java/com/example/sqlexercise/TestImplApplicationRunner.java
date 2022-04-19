package com.example.sqlexercise;

import com.example.sqlexercise.lib.SqlDatabase;
import com.example.sqlexercise.lib.SqlDatabaseConfig;
import com.fasterxml.uuid.Generators;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.UUID;

@Component
public class TestImplApplicationRunner implements ApplicationRunner {

    private final String NAMESPACE_URL = "6ba7b811-9dad-11d1-80b4-00c04fd430c8";

    @Override
    public void run(ApplicationArguments args) throws Exception{
        System.out.println(args);
        System.out.println("测试ApplicationRunner接口！");
        SqlDatabaseConfig config = new SqlDatabaseConfig();
        config.tags = new HashMap<>();
        config.tags.put("schemaName", "test");
        config.tags.put("driver", "mysql");
        config.tags.put("server", "1");
        config.tags.put("index", 1);
        SqlDatabase sqlDatabase = new SqlDatabase(config);
        sqlDatabase.testConnect();
    }
}
