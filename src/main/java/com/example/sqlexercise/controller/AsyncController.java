package com.example.sqlexercise.controller;

import com.example.sqlexercise.serviceImpl.MyAsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AsyncController {

    private final MyAsyncService asyncService;

    @Autowired
    public AsyncController(MyAsyncService asyncService){
        this.asyncService = asyncService;
    }

    @GetMapping("/async-test")
    public String asyncTest(){
        System.out.println(Thread.currentThread().getName()+"---> enter async-test controller!");
        asyncService.myAsyncMethod();
        System.out.println(Thread.currentThread().getName()+"---> end of async-test controller!");
        return "hello";
    }

}
