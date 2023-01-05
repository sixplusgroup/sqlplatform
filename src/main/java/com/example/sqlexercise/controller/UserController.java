package com.example.sqlexercise.controller;

import com.example.sqlexercise.po.PassRecord;
import com.example.sqlexercise.service.QuestionService;
import com.example.sqlexercise.service.UserService;
import com.example.sqlexercise.vo.ResponseVO;
import com.example.sqlexercise.vo.SignVO;
import com.example.sqlexercise.vo.UserVO;

import com.example.sqlexercise.service.MessageCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final MessageCodeService messageCodeService;
    private final UserService userService;

    @Autowired
    public UserController(MessageCodeService messageCodeService, UserService userService) {
        this.messageCodeService = messageCodeService;
        this.userService = userService;
    }

    /**
     * 发邮箱验证码
     */
    @GetMapping("/api/signup/sendMessageCode/{email}")
    public ResponseVO sendMessageCode(@PathVariable String email) {
        String code = messageCodeService.generateMessageCode();
        ResponseVO responseVO = messageCodeService.sendMessageCode(email, code);
        return responseVO;
    }

    /**
     * 注册
     */
    @PostMapping("/api/signup")
    public ResponseVO signUp(@RequestBody UserVO userVO) {
        return userService.signUp(userVO);
    }

    /**
     * 登录
     */
    @PostMapping("/api/signin")
    public ResponseVO signIn(@RequestBody SignVO signVO) {
        return userService.signIn(signVO);
    }

    /**
     * 忘记密码重置
     * @param userVO 只需传email, code, password, passwordConfirmation四个参数
     */
    @PostMapping("/api/reset_password")
    public ResponseVO resetPassword(@RequestBody UserVO userVO) {
        return userService.resetPassword(userVO);
    }

    @GetMapping("/api/get_records")
    public ResponseVO getRecords(String userId) {
        List<PassRecord> res = userService.getRecords(userId);
        return ResponseVO.success(res);
    }

    /**
     * 修改用户信息
     */
    @PostMapping("/api/modify_info")
    public ResponseVO modifyInfo(@RequestBody UserVO userVO) {
        return userService.modifyInfo(userVO);
    }

    /**
     * 获取用户信息
     */
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
        Object statistic = userService.getStatistic(userId);
        return ResponseVO.success(statistic);
    }

    /**
     * 获取用户收藏的题目
     *
     * @return JSON
     */
    @GetMapping("/api/user/stars")
    public ResponseVO getStars(String userId) {
        Object res = userService.getStars(userId);
        return ResponseVO.success(res);
    }

    /**
     * 获取用户最近20次提交记录
     *
     * @return JSON
     */
    @GetMapping("/api/user/recent_submits")
    public ResponseVO getRecentSubmits(String userId) {
        Object res = userService.getRecentSubmits(userId, 20);
        return ResponseVO.success(res);
    }
}
