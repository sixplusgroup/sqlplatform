package com.example.sqlexercise.data;

import com.example.sqlexercise.po.Batch;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface BatchMapper {

    void insert(Batch batch);

    Batch selectById(@Param("id") String id);

    /**
     * 查某用户总提交次数
     */
    int selectSubmitTimesByUserId(String userId);
}