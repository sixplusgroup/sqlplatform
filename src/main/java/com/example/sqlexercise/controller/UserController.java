package com.example.sqlexercise.controller;

import com.example.sqlexercise.service.UserService;
import com.example.sqlexercise.vo.ResponseVO;
import com.example.sqlexercise.vo.SignVO;
import com.example.sqlexercise.vo.UserVO;

import com.example.sqlexercise.service.MessageCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private MessageCodeService messageCodeService;
    @Autowired
    private UserService userService;

    @GetMapping("/api/signup/getMessageCode/{email}")
    public ResponseVO sendMessageCode(@PathVariable String email){
        return messageCodeService.sendMessageCode(email, messageCodeService.generateMessageCode(email));
    }

    @PostMapping("/api/signup")
    public ResponseVO signUp(@RequestBody UserVO userVO){
        return userService.signUp(userVO);
    }

    @PostMapping("/api/signin")
    public ResponseVO signIn(@RequestBody SignVO signVO){
        return userService.signIn(signVO);
    }

    @PostMapping("/api/modifyinfo")
    public String modifyInfo(){
        return "1";
    }
}
