package com.example.sqlexercise.controller;

import com.example.sqlexercise.service.QuestionService;
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
    public ResponseVO sendMessageCode(@PathVariable String email) {
        return messageCodeService.sendMessageCode(email, messageCodeService.generateMessageCode(email));
    }

    @PostMapping("/api/signup")
    public ResponseVO signUp(@RequestBody UserVO userVO) {
        return userService.signUp(userVO);
    }

    @PostMapping("/api/signin")
    public ResponseVO signIn(@RequestBody SignVO signVO) {
        return userService.signIn(signVO);
    }

    @GetMapping("/api/get_records")
    public ResponseVO getRecords(Integer userId) {
        return ResponseVO.success(userService.getRecords(userId));
    }

    @PostMapping("/api/modify_info")
    public ResponseVO modifyInfo(@RequestBody UserVO userVO) {
        return userService.modifyInfo(userVO);
    }

    @GetMapping("/api/get_user_info")
    public ResponseVO getUserInfo(String userId) {
        return ResponseVO.success(userService.getUserInfo(userId));
    }

    /**
     * 获取用户做题数据
     *
     * @return JSON，包括已通过题目数、提交未通过题目数、未开始题目数、提交总次数、提交通过次数、提交通过率
     */
    @GetMapping("/api/user/statistic")
    public ResponseVO getStatistic(String userId) {
        return null;
    }

    /**
     * 获取用户收藏的题目
     *
     * @return JSON，[{main_id: xx, main_question.title: xx, sub_id: xx}, {}, {}, ...]
     */
    @GetMapping("/api/user/stars")
    public ResponseVO getStars(String userId) {
        Object res = userService.getStars(userId);
        return ResponseVO.success(res);
    }
}
