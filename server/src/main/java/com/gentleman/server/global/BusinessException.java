package com.gentleman.server.global;

/**
 * 自定义异常封装类
 */
public class BusinessException extends RuntimeException{

    /**
     * 错误状态码
     */
    private Integer errorCode;

    /**
     * 错误状态描述
     */
    private String errorMsg;

    public BusinessException(){
    }

    public BusinessException(Integer errorCode,String errorMsg){
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
