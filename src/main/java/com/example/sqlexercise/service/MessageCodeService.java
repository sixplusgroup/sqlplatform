package com.example.sqlexercise.service;

import com.example.sqlexercise.vo.ResponseVO;

public interface MessageCodeService {

    String generateMessageCode(String email);

    ResponseVO sendMessageCode(String email, String code);

}
