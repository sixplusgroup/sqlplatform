package com.example.sqlexercise.data;

import com.example.sqlexercise.po.PassRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface PassRecordMapper {

    void create(PassRecord passRecord);

}
