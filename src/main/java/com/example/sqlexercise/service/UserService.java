package com.example.sqlexercise.service;

import com.example.sqlexercise.po.PassRecord;
import com.example.sqlexercise.vo.ResponseVO;
import com.example.sqlexercise.vo.SignVO;
import com.example.sqlexercise.vo.UserVO;

import java.util.List;

public interface UserService {

    /**
     * 注册
     */
    ResponseVO signUp(UserVO userVO);

    /**
     * 登录
     */
    ResponseVO signIn(SignVO signVO);

    List<PassRecord> getRecords(String userId);

    /**
     * 获取用户信息
     */
    UserVO getUserInfo(String userId);

    /**
     * 修改用户信息
     */
    ResponseVO modifyInfo(UserVO userVO);

    /**
     * 获取某一用户收藏的题目列表
     */
    Object getStars(String userId);

    /**
     * 获取用户做题统计数据，包括已通过题目数、提交未通过题目数、未开始题目数、提交总次数、提交通过次数、提交通过率
     */
    Object getStatistic(String userId);

    /**
     * 获取用户最近 n 条提交记录
     */
    Object getRecentSubmits(String userId, int n);

    /**
     * 根据邮箱验证码重置密码
     * @param userVO 只包含email, code, password, passwordConfirmation四个参数
     */
    ResponseVO resetPassword(UserVO userVO);
}
