package com.example.sqlexercise.service;

import com.example.sqlexercise.vo.UserVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void test1(){
        UserVO userVO = new UserVO();
        userVO.setName("何文兵");
        userVO.setEmail("181250043@smail.nju.edu.cn");
        userVO.setCode("4m24n6");
        userVO.setPassword("123456");
        userVO.setPasswordComfirmation("123456");
        userService.signUp(userVO);
    }
}
