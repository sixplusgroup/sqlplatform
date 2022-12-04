package com.example.sqlexercise.service;

import com.example.sqlexercise.po.PassRecord;
import com.example.sqlexercise.vo.ResponseVO;
import com.example.sqlexercise.vo.SignVO;
import com.example.sqlexercise.vo.UserVO;

import java.util.List;

public interface UserService {

    ResponseVO signUp(UserVO userVO);

    ResponseVO signIn(SignVO signVO);

    List<PassRecord> getRecords(Integer userId);
}
