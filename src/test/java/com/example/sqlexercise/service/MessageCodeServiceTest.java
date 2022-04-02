package com.example.sqlexercise.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MessageCodeServiceTest {

    @Autowired
    MessageCodeService messageCodeService;

    @Test
    void getMessageCode(){
        String res = messageCodeService.generateTextMessageCode("181250043@smail.nju.edu.cn");
        System.out.println(res);
    }

}
