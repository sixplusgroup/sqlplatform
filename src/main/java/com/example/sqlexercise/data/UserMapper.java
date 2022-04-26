package com.example.sqlexercise.data;

import com.example.sqlexercise.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {

    User findOneById(@Param("id") String id);

    User findOneByEmail(@Param("email") String email);

    void removeById(String id);

    int create(User user);
}
