package com.gentleman.server.controller;

import com.gentleman.api.response.BaseResponse;
import com.gentleman.api.response.StatusCode;
import com.gentleman.model.entity.Appendix;
import com.gentleman.model.mapper.AppendixMapper;
import com.gentleman.server.request.AppendixDto;
import com.gentleman.server.service.AppendixService;
import com.gentleman.server.service.WebOperationService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author 一粒尘埃
 * @date 2021/1/4/15:04
 */
@RestController
public class UploadController {
    private static final Logger log = LoggerFactory.getLogger(UploadController.class);

    private static final String prefix = "upload";

    @Autowired
    private AppendixService appendixService;

    @Autowired
    private Environment environment;

    @Autowired
    private WebOperationService webOperationService;


    @RequestMapping(value = prefix+"/appendix",method = RequestMethod.POST,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public BaseResponse upload(MultipartHttpServletRequest request){
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {
            String mouldType = request.getParameter("mouldType");
            MultipartFile file = request.getFile("fileName");

            if(StringUtils.isBlank(mouldType) || null == file){
                return response = new BaseResponse(StatusCode.InvaliadParam);
            }
            //上传文件
            AppendixDto dto = new AppendixDto();
            dto.setMoudelType(mouldType);
            String location = appendixService.upload(file,dto);

            //保存文件到数据库
            Appendix appendix = new Appendix();
            appendix.setName(file.getOriginalFilename());
            appendix.setModuleType(mouldType);
            appendix.setLocation(location);
            int aid = appendixService.addUpload(appendix);
            response.setData(aid);
        }catch (Exception e){
            e.getStackTrace();
            response = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    /**
     * 更新模板记录Id
     * @param dto
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = prefix+"/update",method=RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse modifyAppendix(@RequestBody @Valid AppendixDto dto, BindingResult bindingResult){
       BaseResponse response = new BaseResponse(StatusCode.Success);
       try {
           if(bindingResult.hasErrors()){
               return new BaseResponse(StatusCode.InvaliadParam);
           }
           appendixService.modifyModelType(dto);
       }catch (Exception e){
           e.getStackTrace();
           response = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
       }
       return response;
    }

    /**
     * 通用下载模块
     * @param id
     * @param response
     * @return
     */
    @RequestMapping(value = prefix+"/download/{id}",method = RequestMethod.GET)
    public @ResponseBody String download(@PathVariable Integer id, HttpServletResponse response)throws Exception{
        if(null == id || id <= 0 ){
            return null;
        }
        try {
            Appendix appendix = appendixService.selectById(id);
            if(null != appendix){
                String location = environment.getProperty("spring.location.root")+appendix.getLocation();
                InputStream inputStream = new FileInputStream(location);
                webOperationService.downloadFile(response,inputStream,appendix.getName());
            }
        }catch (Exception e){
            e.getStackTrace();
        }
        return null;
    }
}
