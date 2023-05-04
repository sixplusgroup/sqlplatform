package com.example.sqlexercise.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
//@EnableCaching
public class RedisConfig {

//    @Bean
//    public CacheManager cacheManager(RedisConnectionFactory factory){
//        return RedisCacheManager.builder(factory).build();
//    }

    @Bean
    public RedisTemplate<Integer, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<Integer, Object> template = new RedisTemplate<>();
        //关联
        template.setConnectionFactory(factory);
        //设置key的序列化方式
//        template.setKeySerializer();
        //设置value的序列化方式
//        template.setValueSerializer();
        return template;
    }

}
