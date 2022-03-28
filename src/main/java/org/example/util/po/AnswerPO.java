package org.example.util.po;

import lombok.Data;

/**
 * @author shenyichen
 * @date 2022/3/28
 **/
@Data
public class AnswerPO {
    Integer id;
    Integer main_id;
    Integer sub_id;
    String answer;

    public AnswerPO(Integer id, Integer main_id, Integer sub_id, String answer) {
        this.id = id;
        this.main_id = main_id;
        this.sub_id = sub_id;
        this.answer = answer;
    }
}
