package com.example.sqlexercise.service;

import com.example.sqlexercise.vo.ResponseVO;
import com.example.sqlexercise.vo.SignVO;
import com.example.sqlexercise.vo.UserVO;

public interface UserService {

    ResponseVO signUp(UserVO userVO);

    ResponseVO signIn(SignVO signVO);
}
