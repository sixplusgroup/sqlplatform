package org.example.util.vo;

import lombok.Data;

/**
 * @author shenyichen
 * @date 2022/3/29
 **/
@Data
public class GetScoreVO {
    Integer mainId;
    Integer subId;
    String studentSql;
    float maxScore;
}
