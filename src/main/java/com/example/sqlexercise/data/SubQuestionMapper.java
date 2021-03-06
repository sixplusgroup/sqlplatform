package com.example.sqlexercise.data;

import com.example.sqlexercise.po.SubQuestion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SubQuestionMapper {

    SubQuestion getBySubId(@Param("subId") int subId);


}
