package com.example.sqlexercise.controller;

import com.example.sqlexercise.service.MessageCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private MessageCodeService messageCodeService;

    @GetMapping("/api/signup/getMessageCode/{email}")
    public String sendMessageCode(@PathVariable String email){
        return messageCodeService.generateTextMessageCode(email);
    }

    @PostMapping("/api/modifyinfo")
    public String modifyInfo(){
        return "1";
    }
}
