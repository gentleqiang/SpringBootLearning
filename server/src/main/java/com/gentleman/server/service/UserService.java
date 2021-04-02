package com.gentleman.server.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gentleman.model.entity.User;
import com.gentleman.model.mapper.UserMapper;
import com.gentleman.server.util.TimeUtil;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author 一粒尘埃
 * @date 2021/2/19/10:27
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private Environment environment;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public User getUserInfo(Integer id){
        User user = userMapper.selectByPrimaryKey(id);
        return Optional.ofNullable(user).orElse(null);
    }

    public User getUserInfoV2(Integer id)throws Exception{
        String key = String.format(environment.getProperty("redis.user.info.key"),id);
        if(stringRedisTemplate.hasKey(key)){
            String value = stringRedisTemplate.opsForValue().get(key);
            return objectMapper.readValue(value,User.class);
        }
        User user = userMapper.selectByPrimaryKey(id);
        if(null != user) {
            stringRedisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(user));
        }
        return user;
    }


    //TODO key如果一直存在，会对服务器一直有压力，随意要设置过期时间
    //TODO 缓存雪崩：出现的原因就是key大面积同时过期，使得大批量请求落到了数据库，给数据库造成压力，
    //     解决方法：设置key的过期时间随意些。例如：Randoms.nextLong，一共两个参数，就在这两个时间区内取值（左包含，右不开）
    //TODO 缓存穿透：是因为请求的非法行，没有在缓存中，接着去查数据库，数据库中也没有，但是大批量的请求还的
    //去查数据库，给数据库造成压力：解决方法：没有在缓存中，也没有在数据库中，也把它存到缓存中，不过，value值设置为空字符串
    //而我们再次去查缓存时，我们对value进行非空判断，不为空，才将它转换我们需要的对象。



    //TODO 主动推送
    public void modifyCache(Integer id)throws Exception{
        String key = String.format(environment.getProperty("redis.user.info.key"),id);
        User user = userMapper.selectByPrimaryKey(id);
        if(null != user){
            Long expire = RandomUtils.nextLong(20,30);
            stringRedisTemplate.opsForValue().set(key,objectMapper.writeValueAsString(user)
            ,expire, TimeUnit.SECONDS);
        }

    }

    //TODO 利用hash缓存用户信息
    public User getUserInfoByHash(Integer id)throws Exception{
        String key = environment.getProperty("redis.user.hash.key");
        HashOperations<String,String,String> hashOperations = redisTemplate.opsForHash();
        User user  = null;
        if(hashOperations.hasKey(key,String.valueOf(id))){
            String value = hashOperations.get(key,String.valueOf(id));
            if(StringUtils.isNotBlank(value)){
                user = objectMapper.readValue(value,User.class);
            }
            return user;
        }
        user = userMapper.selectByPrimaryKey(id);
        if(null != user){
            hashOperations.putIfAbsent(key,String.valueOf(id),objectMapper.writeValueAsString(user));
        }else {
            hashOperations.putIfAbsent(key,String.valueOf(id),"");
        }
        return user;
    }


    public User getUserInfoV5(Integer userId)throws Exception{
        final String key = environment.getProperty("redis.user.info.hash.key");
        HashOperations<String,String,String> hashOperations = redisTemplate.opsForHash();
        User user = null;
        if(hashOperations.hasKey(key,String.valueOf(userId))){
            String value  = hashOperations.get(key,String.valueOf(userId));
            if(StringUtils.isNotBlank(value)){
                user = objectMapper.readValue(value,User.class);
            }
            return user;
        }
        user = userMapper.selectByPrimaryKey(userId);
        if(null != user){
            hashOperations.putIfAbsent(key,String.valueOf(userId),objectMapper.writeValueAsString(user));
        }else {
            hashOperations.putIfAbsent(key,String.valueOf(userId),"");
        }
        return user;























    }


}
