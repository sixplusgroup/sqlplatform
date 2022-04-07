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
    public void dateTest(){
        Date date = new Date();
        System.out.println(date);
        System.out.println(date.getTime());
        Date expiryDate = new Date(date.getTime()+5*60*1000);
        System.out.println(expiryDate);
        System.out.println(expiryDate.getTime());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(simpleDateFormat.format(date.getTime()));
        System.out.println(simpleDateFormat.format(expiryDate.getTime()));
    }
}
