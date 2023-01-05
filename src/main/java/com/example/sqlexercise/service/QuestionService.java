package com.example.sqlexercise.service;

import com.example.sqlexercise.po.MainQuestion;
import com.example.sqlexercise.po.SubQuestion;
import com.example.sqlexercise.vo.DraftVO;

import java.util.List;
import java.util.Map;

public interface QuestionService {

    String getSchemaNameByMainId(int mainId);

    String getSchemaConstructorByMainId(int mainId);

    /**
     * 获取某一大题下所有小题信息
     */
    List<Map<String, Object>> getSubQuestionByMainId(int mainId);

    SubQuestion getSubQuestionBySubId(int subId);

    /**
     * 获取某一大题信息
     */
    Map<String, Object> getMainQuestionByMainId(int mainId);

    /**
     * 分页查询mainQuestion
     *
     * @param userId    userId
     * @param pageSize  每页数据量
     * @param page      页数
     * @return MainQuestionVO集合
     */
    List<Map<String, Object>> getMainQuestionsByPage(String userId, int pageSize, int page);

    /**
     * 保存某题的草稿
     *
     * @return message of success or fail
     */
    String saveDraft(DraftVO draftVO);

    /**
     * 获取某题保存的草稿
     *
     * @return DraftVO，若不存在则为null
     */
    DraftVO getDraft(String userId, Integer mainId, Integer subId);

    /**
     * 收藏一道subQuestion
     *
     * @return message of success or fail
     */
    String star(String userId, Integer mainId, Integer subId);

    /**
     * 取消收藏一道subQuestion
     *
     * @return message of success or fail
     */
    String unStar(String userId, Integer mainId, Integer subId);

    /**
     * 获取某用户对某一个subQuestion是否收藏、是否通过
     */
    Object getIsStarredAndStateOf(String userId, Integer mainId, Integer subId);

    /**
     * 查询某用户对于某subQuestion的提交记录，包含通过和未通过的，且返回的记录列表按时间先后排序，越近的提交记录越靠前
     */
    List<Object> getSubmitRecord(String userId, Integer mainId, Integer subId);
}
