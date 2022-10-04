package com.example.sqlexercise.vo;

import lombok.Data;

/**
 * @author shenyichen
 * @date 2022/9/20
 **/
@Data
public class GetScoreVO {
    Integer mainId;
    Integer subId;
    String studentSql;
    Float maxScore;

    public GetScoreVO(Integer mainId, Integer subId, String studentSql, Float maxScore) {
        this.mainId = mainId;
        this.subId = subId;
        this.studentSql = studentSql;
        this.maxScore = maxScore;
    }
}

