package com.gentleman.server.controller;

import com.gentleman.api.response.BaseResponse;
import com.gentleman.api.response.StatusCode;
import com.gentleman.server.exception.GlobalException;
import com.google.common.collect.Maps;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @author 一粒尘埃
 * @date 2021/1/16/15:14
 */
@ControllerAdvice
public class GlobalSystemAdvice {

    @ExceptionHandler(GlobalException.class)
    @ResponseBody
    public BaseResponse globalSystemException(Exception e, HttpServletRequest request){
        BaseResponse response = new BaseResponse(StatusCode.Fail);
        HashMap hashMap = Maps.newHashMap();
        hashMap.put("url",request.getRequestURI());
        hashMap.put("exceptionMsg",e.getMessage());
        response.setData(hashMap);
        return response;
    }
}
