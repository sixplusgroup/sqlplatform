package com.example.sqlexercise.data;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

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

    /**
     * 查询每个标签分别有多少道小题
     */
    List<Map<String, Object>> selectTotalSubQuestionNumOfEachTag();

    /**
     * 查某用户在每个标签对应的小题中通过了多少道
     */
    List<Map<String, Object>> selectPassedSubQuestionNumOfEachTag(@Param("userId") String userId);

}
