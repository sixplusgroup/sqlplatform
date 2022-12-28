package com.example.sqlexercise.data;

import com.example.sqlexercise.po.AnswerSet;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author shenyichen
 * @date 2022/9/20
 **/
@Repository
@Mapper
public interface AnswerSetMapper {
    AnswerSet getByAnswer(AnswerSet answerSet);

    void create(AnswerSet answerSet);

    int updateStatus(AnswerSet answerSet);
}
