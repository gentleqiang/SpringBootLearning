package com.gentleman.model.mapper;

import com.gentleman.model.entity.Appendix;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AppendixMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Appendix record);

    int insertSelective(Appendix record);

    Appendix selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Appendix record);

    int updateByPrimaryKey(Appendix record);

    List<Appendix> getAppdendixList(@Param("moduleType")String moduleType,
                                    @Param("recordId")Integer recordId,
                                    @Param("realUrl")String realUrl);

    List<Appendix> getAppdendixListV2(@Param("moduleType")String moduleType,
                                    @Param("recordId")Integer recordId);
}