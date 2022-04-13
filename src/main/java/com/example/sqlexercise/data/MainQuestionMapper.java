package com.example.sqlexercise.data;

import com.example.sqlexercise.po.MainQuestion;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MainQuestionMapper {

    MainQuestion getByMainId(int mainId);


}
