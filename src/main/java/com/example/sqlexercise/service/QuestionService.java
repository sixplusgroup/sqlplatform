package com.example.sqlexercise.service;

import com.example.sqlexercise.po.MainQuestion;
import com.example.sqlexercise.po.SubQuestion;
import com.example.sqlexercise.vo.DraftVO;
import com.example.sqlexercise.vo.MainQuestionVO;

import java.util.List;

public interface QuestionService {

    String getSchemaNameByMainId(int mainId);

    String getSchemaConstructorByMainId(int mainId);

    List<SubQuestion> getSubQuestionByMainId(int mainId);

    SubQuestion getSubQuestionBySubId(int subId);

    MainQuestion getMainQuestionByMainId(int mainId);

    /**
     * 分页查询mainQuestion
     *
     * @param page     页数
     * @param pageSize 每页数据量
     * @return MainQuestionVO集合
     */
    List<MainQuestionVO> getMainQuestionsByPage(int page, int pageSize);

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
     * 获取单个subQuestion对于某用户的状态
     */
    Object getStateOf(String userId, Integer mainId, Integer subId);

    /**
     * 查某个subQuestion是否被收藏
     *
     * @return 已收藏 or 未收藏
     */
    String getStarOrNot(String userId, Integer mainId, Integer subId);

    /**
     * 查询某用户对于某subQuestion的提交记录，包含通过和未通过的，且返回的记录列表按时间先后排序，越近的提交记录越靠前
     */
    List<Object> getSubmitRecord(String userId, Integer mainId, Integer subId);
}
