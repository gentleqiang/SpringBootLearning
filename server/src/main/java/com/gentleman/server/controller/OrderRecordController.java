package com.gentleman.server.controller;

import com.gentleman.api.response.BaseResponse;
import com.gentleman.api.response.StatusCode;
import com.gentleman.model.entity.Appendix;
import com.gentleman.model.entity.OrderRecord;
import com.gentleman.model.mapper.AppendixMapper;
import com.gentleman.model.mapper.OrderRecordMapper;
import com.gentleman.model.mapper.OrderdetailMapper;
import com.gentleman.server.service.MailService;
import com.google.common.collect.Maps;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * @author 一粒尘埃
 * @date 2021/1/10/20:43
 */

@RestController
public class OrderRecordController {

    private static final Logger log = LoggerFactory.getLogger(OrderRecordController.class);

    private static final String prefix = "/orderRecord";

    @Autowired
    private OrderRecordMapper orderRecordMapper;

    @Autowired
    private AppendixMapper appendixMapper;

    @Autowired
    private Environment environment;

    @Autowired
    private MailService mailService;

    @RequestMapping(value = prefix+"/getList/{id}")
    public BaseResponse getList(@PathVariable Integer id){
        if(null == id || id <= 0){
            return new BaseResponse(StatusCode.InvaliadParam);
        }
        BaseResponse response = new BaseResponse(StatusCode.Success);
        HashMap rstMap = Maps.newHashMap();
        try {
            OrderRecord record = orderRecordMapper.selectByPrimaryKey(id);
            List<Appendix> appdendixs = appendixMapper.getAppdendixList("orderRecord",id,environment.getProperty("spring.location.root"));
            rstMap.put("record",record);
            rstMap.put("appdendixs",appdendixs);
            response.setData(rstMap);
        }catch (Exception e){
            return new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    /**
     * 定时发送带上传附件的邮件
     * @param id
     * @return
     */
    @RequestMapping(value = prefix+"/uploadMail/{id}")
    public BaseResponse sendUploadMail(@PathVariable Integer id){
        BaseResponse response = new BaseResponse(StatusCode.Success);
        if(null == id || id <= 0){
            return new BaseResponse(StatusCode.InvaliadParam);
        }
        try {
            OrderRecord record = orderRecordMapper.selectByPrimaryKey(id);
            if(null != record){
                String subject = "定时任务之发送带附件的邮件";
                String content = String.format("订单记录信息：订单编号=%s,订单类型=%s",record.getOrderNo(),record.getOrderType());

                //List<Appendix> appendices = appendixMapper.getAppdendixListV2("appendix",id);
                List<Appendix> appendices = appendixMapper.getAppdendixList("appendix",id,null);
                if(!CollectionUtils.isEmpty(appendices)){
                    mailService.sendUploadMail(subject,content,StringUtils.split(environment.getProperty("mail.send.upload.Mail"),","),appendices);
                }
            }

        }catch (Exception e){
            response = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

}
