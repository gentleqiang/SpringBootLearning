package com.gentleman.server.controller;

import com.gentleman.api.response.BaseResponse;
import com.gentleman.api.response.StatusCode;
import com.gentleman.server.exception.GlobalException;
import org.springframework.web.bind.annotation.*;

/**
 * @author 一粒尘埃
 * @date 2021/1/16/15:03
 */
@RestController
public class ExceptionController {

    private static final String prefix = "/exception";

    @RequestMapping(value = prefix+"/global/{id}",method = RequestMethod.GET)
    public BaseResponse golad(@PathVariable Integer id)throws Exception{
        if(null == id || id <= 0){
            throw new GlobalException(StatusCode.InvaliadParam.getMsg());
        }
        return new BaseResponse(StatusCode.Success);
    }
}
