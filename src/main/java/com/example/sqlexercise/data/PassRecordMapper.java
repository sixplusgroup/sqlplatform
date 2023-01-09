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

    /**
     * 查某用户提交通过记录
     */
    List<PassRecord> selectByUserId(@Param("userId") String userId);

    /**
     * 查某用户提交通过次数
     */
    int selectPassTimesByUserId(String userId);

}