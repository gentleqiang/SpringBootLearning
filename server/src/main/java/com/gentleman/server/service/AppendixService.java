package com.gentleman.server.service;

import com.gentleman.model.entity.Appendix;
import com.gentleman.model.mapper.AppendixMapper;
import com.gentleman.server.request.AppendixDto;
import com.gentleman.server.util.TimeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

/**
 * @author 一粒尘埃
 * @date 2021/1/4/17:28
 */
@Service
public class AppendixService {

    @Autowired
    private Environment environment;

    @Autowired
    private AppendixMapper appendixMapper;

    public String upload(MultipartFile file, AppendixDto dto)throws Exception{
        if(null == file){
            throw new RuntimeException("文件名称为空");
        }
        //获取后缀
        String fileName = file.getOriginalFilename();
        String suffix = StringUtils.substring(fileName,fileName.lastIndexOf("."));

        //构造存储文件夹
        String dateFormat = TimeUtil.toDate(new Date());
        String filePath = environment.getProperty("spring.location.root")+
                File.separator+dto.getMoudelType()+File.separator+ dateFormat +
                File.separator;

        File fileUrl = new File(filePath);
        if(!fileUrl.exists()){
           fileUrl.mkdirs();
        }

        //构造最终文件名
        dateFormat = TimeUtil.toDateTime(new Date());
        String lastFileName = dateFormat+suffix;
        File lastFile = new File(fileUrl+File.separator+lastFileName);

        file.transferTo(lastFile);

        String location = File.separator+dto.getMoudelType()+ File.separator +dateFormat.substring(0,8) +File.separator+lastFileName;

        return location;
    }

    /**
     * 上传文件记录入库
     * @param
     */
    public Integer addUpload(Appendix appendix){
        appendix.setSize(1000L);
        appendix.setCreateTime(new Date());
        appendix.setUpdateTime(new Date());
        appendixMapper.insertSelective(appendix);
        return appendix.getId();
    }

    /**
     * 更新
     * @param dto
     */
    public void modifyModelType(AppendixDto dto) throws Exception{
        String[] aids = StringUtils.split(dto.getAppendixIds(),",");
        for (String aid : aids) {
            Appendix appendix = appendixMapper.selectByPrimaryKey(Integer.valueOf(aid));
            if(null == appendix){
                throw new RuntimeException("信息不存在");
            }
            appendix.setRecordId(dto.getRecordId());
            appendix.setUpdateTime(new Date());
            appendixMapper.updateByPrimaryKeySelective(appendix);
        }
    }

    public Appendix selectById(final Integer id){
        Appendix appendix = appendixMapper.selectByPrimaryKey(id);
        return Optional.ofNullable(appendix).orElse(null);
    }
}
