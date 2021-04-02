package com.gentleman.server.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * @author 一粒尘埃
 * @date 2021/2/20/22:18
 */
@Component
public class RedisCache {

    public static final String prefixCacheKey = "sb:cache:redis:%s";

    private static RedisCache redisCache;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /*@Autowired
    private RedisTemplate redisTemplate;*/

    private RedisCache(){}

    public StringRedisTemplate getStringRedisTemplate() {
        return stringRedisTemplate;
    }

    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /*public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
*/
    @PostConstruct
    public void init(){
        redisCache = new RedisCache();
        redisCache.setStringRedisTemplate(stringRedisTemplate);
        /*redisCache.setRedisTemplate(redisTemplate);
        redisCache.getRedisTemplate().setKeySerializer(new StringRedisSerializer());
        redisCache.getRedisTemplate().setValueSerializer(new JdkSerializationRedisSerializer());
        redisCache.getRedisTemplate().setHashKeySerializer(new StringRedisSerializer());
        redisCache.getRedisTemplate().setHashValueSerializer(new JdkSerializationRedisSerializer());*/
        redisCache.getStringRedisTemplate().setKeySerializer(new StringRedisSerializer());
        redisCache.getStringRedisTemplate().setValueSerializer(new JdkSerializationRedisSerializer());
        redisCache.getStringRedisTemplate().setHashKeySerializer(new StringRedisSerializer());
        redisCache.getStringRedisTemplate().setHashValueSerializer(new StringRedisSerializer());
    }

    /**
     * 自增
     * @param id
     * @param incyNum
     */
    public static void incy(Integer id,Long incyNum){
        String key = String.format(prefixCacheKey,String.valueOf(id));
        redisCache.getStringRedisTemplate().opsForValue().increment(key,incyNum);
    }

    public static String get(String key){
        return redisCache.getStringRedisTemplate().opsForValue().get(key);
    }


    /**
     * 设置Hash
     * @param key
     * @param Hk
     * @param Hv
     */
    public static Boolean setHash(String key,String Hk,String Hv){
        HashOperations<String,String,String> hashOperations = redisCache.getStringRedisTemplate().opsForHash();
        return hashOperations.putIfAbsent(key,Hk,Hv);
    }

    /**
     * 获取Hash
     * @param key
     * @param Hk
     * @return
     * @throws Exception
     */
    public static String getHash(String key,String Hk){
        HashOperations<String,String,String> hashOperations = redisCache.getStringRedisTemplate().opsForHash();
        return hashOperations.get(key,Hk);
    }

    /**
     * hash 自增
     * @param key
     * @param Hk
     * @param incrNum
     */
    public static int hIncrBy(String key,String Hk,Long incrNum){
        HashOperations<String,String,String> hashOperations = redisCache.getStringRedisTemplate().opsForHash();
        return hashOperations.increment(key,Hk,incrNum).intValue();
    }

    /**
     * 获取Hk总数
     * @param key
     * @return
     */
    public static Long getHashLen(String key){
        HashOperations<String,String,String> hashOperations = redisCache.getStringRedisTemplate().opsForHash();
        return hashOperations.size(key);
    }

    /**
     * 获取Hk Hv
     * @param key
     * @return
     */
    public static Map<String,String> getHashHkHv(String key){
        HashOperations<String,String,String> hashOperations = redisCache.getStringRedisTemplate().opsForHash();
        return hashOperations.entries(key);
    }

    /**
     * list - lpush key value [value...]
     * @param key
     * @param list
     */
    public static void setList(String key,List<String> list){
        ListOperations<String, String> listOperations = redisCache.getStringRedisTemplate().opsForList();
        listOperations.leftPushAll(key,list);
    }

    /**
     * list -lrange key start end
     * @param key
     * @return
     */
    public static List<String> getListByKey(String key){
        ListOperations<String, String> listOperations = redisCache.getStringRedisTemplate().opsForList();
        return listOperations.range(key,0,-1);
    }

    /**
     * list = llen key
     */
    public static Long getListLen(String key){
        ListOperations<String, String> listOperations = redisCache.getStringRedisTemplate().opsForList();
        return listOperations.size(key);
    }

}
