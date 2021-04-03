package com.gentleman.server.ApiSign;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

/**
 * @author 一粒尘埃
 * @date 2021/4/3/7:51
 */
@Configuration
public class RedisConfiguration {


    /*@Bean
    public JedisConnectionFactory jedisConnectionFactory(){
        return new JedisConnectionFactory();
    }


    *//**
     * 支持存储对象
     * @return
     *//*
    @Bean
    public RedisTemplate<String,String> redisTemplate(){

       RedisTemplate<String,String> redisTemplate = new RedisTemplate();
       redisTemplate.setConnectionFactory(jedisConnectionFactory());

       Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
       ObjectMapper objectMapper = new ObjectMapper();
       objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
       objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

       redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
       return redisTemplate;
    }
*/

}
