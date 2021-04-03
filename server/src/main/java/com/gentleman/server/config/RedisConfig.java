package com.gentleman.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author 一粒尘埃
 * @date 2021/2/21/18:47
 */
@Configuration
public class RedisConfig {

    @Autowired
    private RedisConnectionFactory connectionFactory;

    @Bean
    public RedisTemplate redisTemplate(){
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(){
        StringRedisTemplate stringStringRedisTemplate = new StringRedisTemplate();
        stringStringRedisTemplate.setConnectionFactory(connectionFactory);
        stringStringRedisTemplate.setKeySerializer(new StringRedisSerializer());
        stringStringRedisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return stringStringRedisTemplate;
    }
}
