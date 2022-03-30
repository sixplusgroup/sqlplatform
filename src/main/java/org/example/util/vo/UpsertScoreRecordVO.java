package org.example.util.vo;

import lombok.Data;

/**
 * @author shenyichen
 * @date 2022/3/30
 **/
@Data
public class UpsertScoreRecordVO {
    String studentId;
    String examId;
    Integer mainId;
    Integer subId;
    Float score;
}
