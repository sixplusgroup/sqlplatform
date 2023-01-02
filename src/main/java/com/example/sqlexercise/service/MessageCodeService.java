package com.example.sqlexercise.service;

import com.example.sqlexercise.vo.ResponseVO;

public interface MessageCodeService {

    String generateMessageCode();

    ResponseVO sendMessageCode(String email, String code);

}
