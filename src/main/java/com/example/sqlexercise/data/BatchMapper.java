package com.example.sqlexercise.data;

import com.example.sqlexercise.po.Batch;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface BatchMapper {

    void create(Batch batch);

    Batch getById(@Param("id") String id);

}
