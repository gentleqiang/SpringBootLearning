package com.gentleman.server.SpringBootIdempotent;

import org.jboss.resteasy.client.jaxrs.engines.PassthroughTrustManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author 一粒尘埃
 * @date 2021/4/3/8:52
 */
@Service
public class TokenUtils {

    private static final Logger log = LoggerFactory.getLogger(TokenUtils.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    /**
     * key前缀
     */
    public static final String IDEMPOTENT_TOKEN_PREFIX = "idempotent_token:";


    /**
     * 创建token
     * @return
     */
    public String generateToken(String value){

        /*生成随机值*/
        String token = UUID.randomUUID().toString().replace("-","");

        /*组装key*/
        final String key = IDEMPOTENT_TOKEN_PREFIX + token;

        /*存储到Redis中 过期时间为3分钟*/
        Boolean isExist =
                stringRedisTemplate.opsForValue().setIfAbsent(key,value,3, TimeUnit.MINUTES);
        return token;
    }

    /**
     * 校验Token
     * @return
     */
    public Boolean validToken(String token,String value){

        //lua表达式：保证操作的原子性 其中key[1]是key,key[2]是value
        //String script =
               // "if redis.call('get',KEYS[1]) == KEYS[2] then return redis.call('del',KEYS[1]) else return 0 end";
        //RedisScript<Long> redisScript = new DefaultRedisScript<>(script,Long.class);

        //组装系统前缀和Key
        String key = IDEMPOTENT_TOKEN_PREFIX + token;

        //执行LUA脚本
        //Long result = stringRedisTemplate.execute(redisScript, Arrays.asList(key,value));

        String  result= stringRedisTemplate.opsForValue().get(key);
        stringRedisTemplate.delete(key);
        log.info("校验结果：{}",result);

        //根据执行结果判断是否存在
        if( null != result && result.equals(value)){
            log.info("校验成功：key={} value={} result={}",key,value,result);
            return true;
        }
        log.info("校验失败：key={} value={} result={}",key,value,result);
        return false;
    }
}
