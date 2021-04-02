package com.gentleman.server.controller;

import com.gentleman.api.response.BaseResponse;
import com.gentleman.api.response.StatusCode;
import com.gentleman.model.entity.User;
import com.gentleman.model.mapper.UserMapper;
import com.gentleman.server.request.UserRequest;
import com.gentleman.server.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author 一粒尘埃
 * @date 2021/2/19/10:22
 */
@RestController
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private static final String prefix = "user";

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @RequestMapping(value = prefix+"/detail/{userId}",method = RequestMethod.GET)
    public BaseResponse getUserInfo(@PathVariable Integer userId){
       BaseResponse response = new BaseResponse(StatusCode.Success);
       try {
           User user = userService.getUserInfoV2(userId);
           response.setData(user);
       }catch (Exception e){
           e.getStackTrace();
           response = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
       }
       return response;
    }

    /**
     * 主动推送缓存
     * @param request
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = prefix+"/insert/update/{userId}",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse modifyCache(@RequestBody @Validated UserRequest request, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
           return new BaseResponse(StatusCode.InvaliadParam);
        }
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {
            //TODO 更新数据库，更新缓存
           if(request.getId() != null && request.getId() > 0){
               User user = userMapper.selectByPrimaryKey(request.getId());
               if(null != user){
                   BeanUtils.copyProperties(request,user);
                   user.setUpdateTime(new Date());
                   userMapper.updateByPrimaryKeySelective(user);
                   userService.modifyCache(user.getId());
               }

           }
           //TODO 新增数据库，新增缓存
            User user = new User();
            BeanUtils.copyProperties(request,user);
            user.setUpdateTime(new Date());
            userMapper.insertSelective(user);
            userService.modifyCache(user.getId());
        }catch (Exception e){
            e.getStackTrace();
            response = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    //TODO 利用hash数据结构   一个大KEY value为map<String,String>
    @RequestMapping(value=prefix+"/hashInfo/{id}",method = RequestMethod.GET)
    public BaseResponse getHashInfo(@PathVariable Integer id){
        if(null == id || id <= 0){
            return new BaseResponse(StatusCode.InvaliadParam);
        }
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {
/*
            User user = userService.getUserInfoByHash(id);
*/
            User user = userService.getUserInfoV5(id);

            response.setData(user);
        }catch (Exception e){
            e.getStackTrace();
            response = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }







}
