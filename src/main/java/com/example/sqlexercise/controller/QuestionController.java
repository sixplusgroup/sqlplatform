package com.example.sqlexercise.controller;

import com.example.sqlexercise.service.QuestionService;
import com.example.sqlexercise.vo.DraftVO;
import com.example.sqlexercise.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/api/question/getSubQuestion/{mainId}")
    public ResponseVO getSubQuestion(@PathVariable("mainId") int mainId){
        return ResponseVO.success(questionService.getSubQuestionByMainId(mainId));
    }

    @GetMapping("/api/question/get_main_question_by_page")
    public ResponseVO getMainQuestionsByPage(Integer page, Integer pageSize) {
        if (page < 1) {
            return ResponseVO.failure("页码应当从1开始！");
        }
        return ResponseVO.success(questionService.getMainQuestionsByPage(page, pageSize));
    }

    @PostMapping("/api/question/save_draft")
    public ResponseVO saveDraft(@RequestBody DraftVO draftVO) {
        return questionService.saveDraft(draftVO);
    }

    @GetMapping("/api/question/get_draft")
    public ResponseVO getDraft(String userId, Integer mainId, Integer subId) {
        return questionService.getDraft(userId, mainId, subId);
    }

}
