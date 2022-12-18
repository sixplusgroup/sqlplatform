package com.example.sqlexercise.controller;

import com.example.sqlexercise.service.QuestionService;
import com.example.sqlexercise.vo.DraftVO;
import com.example.sqlexercise.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//处理获取题目信息等非sql请求
@RestController
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/api/question/getMainQuestion/{mainId}")
    public ResponseVO getMainQuestion(@PathVariable("mainId") int mainId) {
        return ResponseVO.success(questionService.getMainQuestionByMainId(mainId));
    }

    @GetMapping("/api/question/getSubQuestion/{mainId}")
    public ResponseVO getSubQuestion(@PathVariable("mainId") int mainId) {
        return ResponseVO.success(questionService.getSubQuestionByMainId(mainId));
    }

    /**
     * 分页查询mainQuestion
     *
     * @param page     页数
     * @param pageSize 每页数据量
     * @return MainQuestionVO集合
     */
    @GetMapping("/api/question/get_main_question_by_page")
    public ResponseVO getMainQuestionsByPage(String userId, Integer page, Integer pageSize) {
        if (page < 1) {
            return ResponseVO.failure("页码应当从1开始！");
        }
        return ResponseVO.success(questionService.getMainQuestionsByPage(userId, pageSize, page));
    }

    /**
     * 保存某题的草稿
     *
     * @return message of success or fail
     */
    @PostMapping("/api/question/save_draft")
    public ResponseVO saveDraft(@RequestBody DraftVO draftVO) {
        String res = questionService.saveDraft(draftVO);
        return ResponseVO.success(res);
    }

    /**
     * 获取某题保存的草稿
     *
     * @return DraftVO，若不存在则为null
     */
    @GetMapping("/api/question/get_draft")
    public ResponseVO getDraft(String userId, Integer mainId, Integer subId) {
        DraftVO res = questionService.getDraft(userId, mainId, subId);
        if (res == null) {
            return ResponseVO.failure("未保存过草稿");
        }
        return ResponseVO.success(res);
    }

    /**
     * 收藏一道subQuestion
     *
     * @return message of success or fail
     */
    @GetMapping("/api/question/star")
    public ResponseVO star(String userId, Integer mainId, Integer subId) {
        String res = questionService.star(userId, mainId, subId);
        return ResponseVO.success(res);
    }

    /**
     * 取消收藏一道subQuestion
     *
     * @return message of success or fail
     */
    @GetMapping("/api/question/unStar")
    public ResponseVO unStar(String userId, Integer mainId, Integer subId) {
        String res = questionService.unStar(userId, mainId, subId);
        return ResponseVO.success(res);
    }

    /**
     * 查询某用户对某subQuestion是否收藏、是否通过
     */
    @GetMapping("/api/question/isStarred_and_state")
    public ResponseVO getIsStarredAndStateOf(String userId, Integer mainId, Integer subId) {
        Object res = questionService.getIsStarredAndStateOf(userId, mainId, subId);
        return ResponseVO.success(res);
    }

    /**
     * 查询某用户对于某subQuestion的提交记录，包含通过和未通过的，且返回的记录列表按时间先后排序，越近的提交记录越靠前
     */
    @GetMapping("/api/question/submit_record")
    public ResponseVO getSubmitRecord(String userId, Integer mainId, Integer subId) {
        List<Object> submitRecord = questionService.getSubmitRecord(userId, mainId, subId);
        return ResponseVO.success(submitRecord);
    }

}
