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

    /**
     * 分页查大题数据
     */
    List<Map<String, Object>> selectByPage(@Param("userId") String userId,
                                           @Param("from") int from,
                                           @Param("size") int size);

    /**
     * 查大题总数
     */
    Integer countTotal();

    /**
     * 分页查包含某些标签的大题数据
     */
    List<Map<String, Object>> selectByPageFilterByTags(@Param("userId") String userId,
                                                       @Param("from") int from,
                                                       @Param("size") int size,
                                                       @Param("tags") List<Integer> tags);
}