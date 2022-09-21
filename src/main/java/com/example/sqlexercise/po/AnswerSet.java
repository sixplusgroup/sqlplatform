package com.example.sqlexercise.po;

import lombok.Data;

import java.util.Date;

/**
 * @author shenyichen
 * @date 2022/9/20
 **/
@Data
public class AnswerSet {
    Integer id;
    Integer mainId;
    Integer subId;
    String answer;
    Date createdAt;
    Date updatedAt;
    Integer state;
}
