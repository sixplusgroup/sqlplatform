package com.example.sqlexercise.data;

import com.example.sqlexercise.po.SubQuestion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface SubQuestionMapper {

    /**
     * 查询某一大题下所有小题信息
     */
    List<Map<String, Object>> selectByMainId(@Param("mainId") int mainId);

    SubQuestion selectBySubId(@Param("subId") int subId);

    /**
     * 查小题总数
     */
    int countTotal();

    /**
     * 查所有小题信息
     */
    List<SubQuestion> selectAll();

}