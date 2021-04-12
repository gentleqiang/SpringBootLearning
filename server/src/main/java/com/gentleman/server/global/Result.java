package com.gentleman.server.global;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 封装统一返回结果类
 */
public class Result {

    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 数据
     */
    private Object data;

    public Result() {
    }

    public Result(Boolean success, Integer code, String msg, Object data) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static Result defineError(BusinessException businessException){
        return new Result().setSuccess(false).setCode(businessException.getErrorCode())
                .setMsg(businessException.getErrorMsg()).setData(null);
    }

    public static Result otherError(ErrorEnum errorEnum){
        return new Result().setSuccess(false).setCode(errorEnum.getCode())
                .setMsg(errorEnum.getMsg()).setData(null);
    }

    public Boolean getSuccess() {
        return success;
    }

    public Result setSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public Result setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public Result setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getData() {
        return data;
    }

    public Result setData(Object data) {
        this.data = data;
        return this;
    }
}
