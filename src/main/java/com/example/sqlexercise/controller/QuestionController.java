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

    /**
     * 获取某大题信息
     */
    @GetMapping("/api/question/getMainQuestion/{mainId}")
    public ResponseVO getMainQuestion(@PathVariable("mainId") int mainId) {
        return ResponseVO.success(questionService.getMainQuestionByMainId(mainId));
    }

    /**
     * 获取某一大题下所有小题信息
     */
    @GetMapping("/api/question/getSubQuestion/{mainId}")
    public ResponseVO getSubQuestion(@PathVariable("mainId") int mainId) {
        return ResponseVO.success(questionService.getSubQuestionByMainId(mainId));
    }

    /**
     * 分页查询mainQuestion
     *
     * @param userId   userId
     * @param page     页数
     * @param pageSize 每页数据量
     */
    @GetMapping("/api/question/get_main_question_by_page")
    public ResponseVO getMainQuestionsByPage(@RequestParam String userId,
                                             @RequestParam Integer page,
                                             @RequestParam Integer pageSize) {
        if (page < 1) {
            return ResponseVO.failure("页码应当从1开始！");
        }
        return ResponseVO.success(questionService.getMainQuestionsByPage(userId, pageSize, page));
    }

    /**
     * 根据标签筛选mainQuestion，并分页返回
     *
     * @param userId   userId
     * @param page     页数
     * @param pageSize 每页数据量
     * @param tags     筛选条件，标签列表
     */
    @GetMapping("/api/question/get_main_question_by_page_filter_by_tags")
    public ResponseVO getMainQuestionsByPageFilterByTags(@RequestParam String userId,
                                                         @RequestParam Integer page,
                                                         @RequestParam Integer pageSize,
                                                         @RequestParam(value = "tags[]") List<String> tags) {
        if (page < 1) {
            return ResponseVO.failure("页码应当从1开始！");
        }
        return ResponseVO.success(questionService.getMainQuestionsByPageFilterByTags(userId, pageSize, page, tags));
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
    public ResponseVO getDraft(@RequestParam String userId,
                               @RequestParam Integer mainId,
                               @RequestParam Integer subId) {
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
    public ResponseVO star(@RequestParam String userId,
                           @RequestParam Integer mainId,
                           @RequestParam Integer subId) {
        String res = questionService.star(userId, mainId, subId);
        return ResponseVO.success(res);
    }

    /**
     * 取消收藏一道subQuestion
     *
     * @return message of success or fail
     */
    @GetMapping("/api/question/unStar")
    public ResponseVO unStar(@RequestParam String userId,
                             @RequestParam Integer mainId,
                             @RequestParam Integer subId) {
        String res = questionService.unStar(userId, mainId, subId);
        return ResponseVO.success(res);
    }

    /**
     * 查询某用户对某subQuestion是否收藏、是否通过
     */
    @GetMapping("/api/question/isStarred_and_state")
    public ResponseVO getIsStarredAndStateOf(@RequestParam String userId,
                                             @RequestParam Integer mainId,
                                             @RequestParam Integer subId) {
        Object res = questionService.getIsStarredAndStateOf(userId, mainId, subId);
        return ResponseVO.success(res);
    }

    /**
     * 查询某用户对于某subQuestion的提交记录，包含通过和未通过的，且返回的记录列表按时间先后排序，越近的提交记录越靠前
     */
    @GetMapping("/api/question/submit_record")
    public ResponseVO getSubmitRecord(@RequestParam String userId,
                                      @RequestParam Integer mainId,
                                      @RequestParam Integer subId) {
        List<Object> submitRecord = questionService.getSubmitRecord(userId, mainId, subId);
        return ResponseVO.success(submitRecord);
    }

}
