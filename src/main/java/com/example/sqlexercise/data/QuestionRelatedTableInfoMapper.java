package com.example.sqlexercise.data;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface QuestionRelatedTableInfoMapper {

    /**
     * 查跟某大题相关的表列信息
     */
    List<Map<String, Object>> selectTableInfoByMainId(@Param("mainId") int mainId);

}
