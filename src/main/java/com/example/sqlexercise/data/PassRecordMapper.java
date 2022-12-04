package com.example.sqlexercise.data;

import com.example.sqlexercise.po.PassRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface PassRecordMapper {

    void insert(PassRecord passRecord);

    List<PassRecord> selectByUserId(@Param("userId") Integer userId);

}