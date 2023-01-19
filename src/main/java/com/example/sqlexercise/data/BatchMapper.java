package com.example.sqlexercise.data;

import com.example.sqlexercise.po.Batch;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface BatchMapper {

    void insert(Batch batch);

    Batch selectById(@Param("id") String id);

    /**
     * 查某用户总提交次数
     */
    int selectSubmitTimesByUserId(@Param("userId") String userId);

    /**
     * 查某用户某题的全部提交记录
     */
    List<Object> selectSubmitRecord(@Param("userId") String userId,
                                    @Param("mainId") int mainId,
                                    @Param("subId") int subId);

    /**
     * 查某用户最近 n 次提交记录
     */
    List<Map<String, Object>> selectRecentSubmits(@Param("userId") String userId,
                                                  @Param("n") int n);
}