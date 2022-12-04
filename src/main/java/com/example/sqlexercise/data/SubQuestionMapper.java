package com.example.sqlexercise.data;

import com.example.sqlexercise.po.SubQuestion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SubQuestionMapper {

    List<SubQuestion> selectByMainId(@Param("mainId") int mainId);

    SubQuestion selectBySubId(@Param("subId") int subId);
}