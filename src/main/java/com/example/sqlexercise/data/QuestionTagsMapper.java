package com.example.sqlexercise.data;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface QuestionTagsMapper {

    /**
     * 查一个小题的标签
     */
    List<Integer> selectTagBySubId(@Param("subId") Integer subId);

    /**
     * 查一个大题下所有小题的标签
     */
    List<Integer> selectTagByMainId(@Param("mainId") Integer mainId);

}
