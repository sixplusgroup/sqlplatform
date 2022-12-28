package com.example.sqlexercise.po;

import lombok.Data;

@Data
public class QuestionState {
    private Integer id;
    private String userId;
    private Integer mainId;
    private Integer subId;
    /**
     * 是否被收藏，true-已收藏，false-未收藏
     */
    private Boolean isStarred;
    /**
     * 题目状态，0-未开始，1-提交未通过，2-已通过
     */
    private Integer state;

    public QuestionState(String userId, Integer mainId, Integer subId, Boolean isStarred, Integer state) {
        this.userId = userId;
        this.mainId = mainId;
        this.subId = subId;
        this.isStarred = isStarred;
        this.state = state;
    }

    public QuestionState(Integer id, String userId, Integer mainId, Integer subId, Boolean isStarred, Integer state) {
        this.id = id;
        this.userId = userId;
        this.mainId = mainId;
        this.subId = subId;
        this.isStarred = isStarred;
        this.state = state;
    }
}
