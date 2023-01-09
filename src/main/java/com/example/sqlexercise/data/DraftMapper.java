package com.example.sqlexercise.data;

import com.example.sqlexercise.po.Draft;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface DraftMapper {

    Draft select(@Param("userId") String userId,
                 @Param("mainId") int mainId,
                 @Param("subId") int subId);

    int insert(Draft draft);

    int update(Draft draft);
}
