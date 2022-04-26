package com.example.sqlexercise.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
public class MessageCodeServiceTest {

    @Autowired
    MessageCodeService messageCodeService;

    @Test
    public void Test(){
        String code = messageCodeService.generateMessageCode("181250043@smail.nju.edu.cn");
        messageCodeService.sendMessageCode("181250043@smail.nju.edu.cn", code);
    }
}
