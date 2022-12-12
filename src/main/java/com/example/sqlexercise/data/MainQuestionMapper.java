package com.example.sqlexercise.data;

import com.example.sqlexercise.po.MainQuestion;
import com.example.sqlexercise.vo.MainQuestionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface MainQuestionMapper {

    MainQuestion selectById(@Param("mainId") int mainId);

    List<Map<String, Object>> selectByPage(@Param("from") int from, @Param("size") int size);

    Integer countTotal();

}