package com.example.sqlexercise.service;

import com.example.sqlexercise.vo.ResponseVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MessageCodeServiceTest {

    @Autowired
    private MessageCodeService messageCodeService;

    @Test
    void generateMessageCode() {
        String res = messageCodeService.generateMessageCode();
        System.out.println(res);
        Assertions.assertNotNull(res);
    }

    @Test
    void sendMessageCode() {
        String code = "a1b2c3";
        ResponseVO res = messageCodeService.sendMessageCode(ConstantsOfTest.USER_EMAIL, code);
        Assertions.assertEquals("success", res.getRes());
    }
}
