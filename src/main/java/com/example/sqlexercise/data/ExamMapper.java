package com.example.sqlexercise.data;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author shenyichen
 * @date 2022/9/20
 **/
@Repository
@Mapper
public interface ExamMapper {
    String getQuestionsById(@Param("id") String id);
}
