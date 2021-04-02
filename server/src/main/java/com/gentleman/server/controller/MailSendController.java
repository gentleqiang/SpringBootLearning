package com.gentleman.server.controller;

import com.gentleman.api.response.BaseResponse;
import com.gentleman.api.response.StatusCode;
import com.gentleman.server.request.MailSendRequest;
import com.gentleman.server.service.MailService;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author 一粒尘埃
 * @date 2020/12/28/17:33
 */
@RestController
@RequestMapping("mail")
public class MailSendController {

    private static final Logger log = LoggerFactory.getLogger(MailSendController.class);

    @Autowired
    private MailService mailService;

    @RequestMapping(value = "sendSimpleEmail",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse sendSimpleEmail(@RequestBody @Valid MailSendRequest request, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new BaseResponse(StatusCode.InvaliadParam);
        }
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {
            log.info("开始发送简单邮件========");
            mailService.sendSimpleEmail(request.getSubject(),request.getContent(), StringUtils.split(request.getTos(),","));
        }catch (Exception e){
            e.getStackTrace();
            response = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    /**
     * 发送简单文本带附件的邮件
     * @param request
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "sendAttachmentEmail",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse sendAttachmentEmail(@RequestBody @Valid MailSendRequest request, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new BaseResponse(StatusCode.InvaliadParam);
        }
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {
            log.info("发送简单文本带附件的邮件========");
            mailService.sendAttachmentEmail(request.getSubject(),request.getContent(), StringUtils.split(request.getTos(),","));
        }catch (Exception e){
            e.getStackTrace();
            response = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }
}
