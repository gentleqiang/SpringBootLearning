package com.gentleman.server.controller;

import com.gentleman.api.response.BaseResponse;
import com.gentleman.api.response.StatusCode;
import com.gentleman.server.request.UserRequest;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author 一粒尘埃
 * @date 2020/11/3/14:52
 */
@RestController
public class ValitorController {

    private static final Logger log = LoggerFactory.getLogger(ValitorController.class);

    private static final String prefix = "valitor";

    /**
     * 使用工具类和Valia校验
     * @param request
     * @param result
     * @return
     */
    @RequestMapping(value = prefix+"/insert",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse insert(@RequestBody @Valid UserRequest request, BindingResult result){
        if(result.hasErrors()){
            List<ObjectError> errors = result.getAllErrors();
            StringBuilder stringBuilder = new StringBuilder();
            errors.stream().forEach(error->{
                stringBuilder.append(error.getDefaultMessage()).append("\n");
            });
            return new BaseResponse(StatusCode.InvaliadParam.getCode(),stringBuilder.toString());
        }
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {
            /*if(Strings.isNullOrEmpty(request.getName()) || request.getAge() == null || request.getSex() == null){
               return new BaseResponse(StatusCode.InvaliadParam);
            }*/
            log.info("【参数校验】userRequest:{}"+request);
        }catch (Exception e){
            response = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    /**
     * 使用自定义注解校验
     * @param request
     * @param result
     * @return
     */
    @RequestMapping(value = prefix+"/insertV2",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse insertV2(@RequestBody @Valid UserRequest request, BindingResult result){
        if(result.hasErrors()){
            List<ObjectError> errors = result.getAllErrors();
            StringBuilder stringBuilder = new StringBuilder();
            errors.stream().forEach(error->{
                stringBuilder.append(error.getDefaultMessage()).append("\n");
            });
            return new BaseResponse(StatusCode.InvaliadParam.getCode(),stringBuilder.toString());
        }
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {
            /*if(Strings.isNullOrEmpty(request.getName()) || request.getAge() == null || request.getSex() == null){
               return new BaseResponse(StatusCode.InvaliadParam);
            }*/
            log.info("【参数校验】userRequest:{}"+request);
        }catch (Exception e){
            response = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    @RequestMapping(value = prefix+"/forIn",method = RequestMethod.GET)
    public BaseResponse forIn(){
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {
            List<Integer> ids = Lists.newArrayList();
            ids.add(1);
            ids.add(2);
            ids.add(3);
            ids.add(4);
            log.info("【数据类型为List】="+ids);
            String idss = Joiner.on(",").join(ids);
            log.info("【数据类型为String 工具类】="+idss);

            log.info("=============================");
            StringBuilder stringBuilder = new StringBuilder();
            ids.stream().forEach(id->{
                stringBuilder.append(id).append(",");
            });
            log.info("【数据类型为String 自定义的】="+stringBuilder.toString().substring(0,stringBuilder.length()-1));
        }catch (Exception e){
            response = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }

        return response;
    }

}
