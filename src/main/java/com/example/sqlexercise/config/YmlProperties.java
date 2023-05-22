package com.example.sqlexercise.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = "classpath:application.yml")
@Getter
public class YmlProperties {

    @Value("${sqlexercise.database.oceanbase.table-duplicate-num}")
    private int oceanbaseTableDuplicateNum;

    @Value("${sqlexercise.docker.host}")
    private String dockerHost;

    @Value("${sqlexercise.docker.port}")
    private int dockerPort;

    @Value("${sqlexercise.database.mysql.need}")
    private boolean needMysql;

    @Value("${sqlexercise.database.oceanbase.need}")
    private boolean needOceanbase;

    @Value("${sqlexercise.database.mysql.recreate}")
    private boolean recreateMysql;

    @Value("${sqlexercise.database.oceanbase.recreate}")
    private boolean recreateOceanbase;

}
