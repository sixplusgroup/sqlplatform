package com.example.sqlexercise.controller;

import com.example.sqlexercise.service.QuestionService;
import com.example.sqlexercise.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

//处理获取题目信息等非sql请求
@RestController
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/api/question/getMainQuestion/{mainId}")
    public ResponseVO getMainQuestion(@PathVariable("mainId") int mainId){
        return ResponseVO.success(questionService.getMainQuestionByMainId(mainId));
    }

    @GetMapping("/api/question/getSubQuestion/{subId}")
    public ResponseVO getSubQuestion(@PathVariable("subId") int subId){
        return ResponseVO.success(questionService.getSubQuestionBySubId(subId));
    }
}
