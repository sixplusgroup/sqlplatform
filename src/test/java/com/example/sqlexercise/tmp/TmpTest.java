package com.example.sqlexercise.tmp;

import com.example.sqlexercise.controller.QuestionController;
import com.example.sqlexercise.data.MainQuestionMapper;
import com.example.sqlexercise.data.UserMapper;
import com.example.sqlexercise.vo.ResponseVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
public class TmpTest {

    @Autowired
    private QuestionController questionController;
    @Autowired
    private MainQuestionMapper mainQuestionMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    void test() {
        for (int i = 1; i <= 3; i++) {
            ResponseVO res = questionController.getMainQuestionsByPage(i, 5);
            System.out.println(res);
        }
    }
}
