package com.example.sqlexercise.data;

import com.example.sqlexercise.po.Cache;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CodeMapper {

    Cache getCacheByEmail(@Param("email") String email);

    int create(Cache cache);

    int update(Cache cache);

    int deleteByEmail(String email);
}
