package com.gentleman.server.controller;

import com.gentleman.api.response.BaseResponse;
import com.gentleman.api.response.StatusCode;
import com.gentleman.server.cache.RedisCache;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sun.awt.geom.AreaOp;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.prefs.BackingStoreException;

/**
 * @author 一粒尘埃
 * @date 2021/2/20/22:47
 */
@RestController
public class RedisController {

    private static final Logger log = LoggerFactory.getLogger(RedisController.class);

    private static final String prefix = "redis";

    /**
     * redis -string应用场景之incy 自增
     * @param id
     * @return
     */
    @RequestMapping(value = prefix+"/incy/{id}",method = RequestMethod.GET)
    public BaseResponse incy(@PathVariable Integer id){
       if(null == id || id <= 0){
          return new BaseResponse(StatusCode.InvaliadParam);
       }
       BaseResponse response = new BaseResponse(StatusCode.Success);
       try {
           RedisCache.incy(id,2L);
           String result = RedisCache.get(String.format(RedisCache.prefixCacheKey,String.valueOf(id)));
           response.setData(result);
       }catch (Exception e){
          e.getStackTrace();
          response = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
       }
       return response;
    }


    /**
     * redis -hash应用场景之购物车
     */
    @RequestMapping(value = prefix+"/byHash/{id}",method = RequestMethod.GET)
    public BaseResponse byHash(@PathVariable Integer id){
        if(null == id || id <= 0){
            return new BaseResponse(StatusCode.InvaliadParam);
        }
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {
            String key = String.format(RedisCache.prefixCacheKey,id);
            RedisCache.setHash(key,"111","1");
            RedisCache.setHash(key,"222","1");
            log.info("////////////////////////塞入购物车成功//////////////////");
            Long shopNum = RedisCache.getHashLen(key);
            log.info("///////////////////////商品数量////////////////////////"+shopNum);
            String Hv = RedisCache.getHash(key,"111");
            log.info("///////////////////指定商品信息///////////////////////"+Hv);
            int hIncrBy = RedisCache.hIncrBy(key,"111",1L);
            log.info("///////////////////自增成功///////////////////////"+hIncrBy);
            String incrHv = RedisCache.getHash(key,"111");
            log.info("///////////////////指定商品信息自增成功后///////////////////////"+incrHv);
            Map<String,String> resultMap = RedisCache.getHashHkHv(key);
            response.setData(resultMap);
        }catch (Exception e){
            e.getStackTrace();
            response = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    @RequestMapping(value = prefix+"/testList/{id}",method = RequestMethod.GET)
    public BaseResponse testList(@PathVariable Integer id){
        if(null == id || id <= 0){
            return new BaseResponse(StatusCode.InvaliadParam);
        }
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {
            String key = String.format(RedisCache.prefixCacheKey,id);
            List<String> list = Lists.newArrayList();
            list.add("石山码农");
            list.add("程序员实战基地");
            RedisCache.setList(key,list);
            List<String> cacheList = RedisCache.getListByKey(key);
            List<String> resultList = CollectionUtils.isEmpty(cacheList) ? Collections.emptyList()
               : cacheList ;
            Long keyLen = RedisCache.getListLen(key);
            Map<String,Object> resultMap = Maps.newHashMap();
            resultMap.put("resultList",resultList);
            resultMap.put("keyLen",keyLen);
            response.setData(resultMap);
        }catch (Exception e){
            e.getStackTrace();
            response = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

}
