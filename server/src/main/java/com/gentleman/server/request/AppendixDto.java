package com.gentleman.server.request;

import java.io.Serializable;
import java.util.List;

/**
 * @author 一粒尘埃
 * @date 2021/1/4/22:02
 */
public class AppendixDto implements Serializable {

    private String moudelType;

    private Integer recordId;

    private String appendixIds;

    public String getMoudelType() {
        return moudelType;
    }

    public void setMoudelType(String moudelType) {
        this.moudelType = moudelType;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public void setAppendixIds(String appendixIds) {
        this.appendixIds = appendixIds;
    }

    public String getAppendixIds() {
        return appendixIds;
    }
}
