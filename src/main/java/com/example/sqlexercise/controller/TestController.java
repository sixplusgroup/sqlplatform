package com.example.sqlexercise.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/index")
    public String index(){
        return "Start to develop project SQL Exercise!";
    }
}
