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

    private final MessageCodeService messageCodeService;
    private final UserService userService;

    @Autowired
    public UserController(MessageCodeService messageCodeService, UserService userService) {
        this.messageCodeService = messageCodeService;
        this.userService = userService;
    }

    @GetMapping("/api/signup/sendMessageCode/{email}")
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

    @GetMapping("/api/get_records")
    public ResponseVO getRecords(Integer userId) {
        return ResponseVO.success(userService.getRecords(userId));
    }

    @PostMapping("/api/modifyinfo")
    public String modifyInfo(){
        return "1";
    }
}
