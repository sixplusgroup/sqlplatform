package com.example.sqlexercise.data;

import com.example.sqlexercise.po.MainQuestion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MainQuestionMapper {

    MainQuestion getByMainId(@Param("mainId") int mainId);


}
