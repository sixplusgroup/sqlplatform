package com.example.sqlexercise.data;

import com.example.sqlexercise.po.QuestionState;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface QuestionStateMapper {

    /**
     * 查询一条question state数据
     */
    QuestionState select(@Param("userId") String userId,
                         @Param("mainId") int mainId,
                         @Param("subId") int subId);

    /**
     * 更新一条question state数据的is_starred字段
     */
    int updateIsStarred(@Param("userId") String userId,
                        @Param("mainId") int mainId,
                        @Param("subId") int subId,
                        @Param("isStarred") boolean isStarred);

    /**
     * 更新一条question state数据的state字段
     */
    int updateState(@Param("userId") String userId,
                    @Param("mainId") int mainId,
                    @Param("subId") int subId,
                    @Param("state") int state);

    /**
     * 插入一条question state数
     */
    int insert(QuestionState questionState);

    /**
     * 查询某一用户收藏的题目
     */
    List<Map<String, Object>> selectStars(@Param("userId") String userId);

    boolean selectIsStarredOf(@Param("userId") String userId,
                              @Param("mainId") int mainId,
                              @Param("subId") int subId);

    /**
     * 查某用户提交未通过的题目数
     */
    int selectSubmittedButNotPassByUserId(String userId);

    /**
     * 查某用户已通过的题目数
     */
    int selectPassedByUserId(String userId);
}
