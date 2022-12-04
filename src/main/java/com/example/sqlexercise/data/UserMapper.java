package com.example.sqlexercise.data;

import com.example.sqlexercise.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {

    User selectById(@Param("id") String id);

    User selectByEmail(@Param("email") String email);

    void deleteById(@Param("id") String id);

    int insert(User user);
}