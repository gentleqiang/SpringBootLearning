package com.gentleman.api.response;

/**
 * @author 一粒尘埃
 * @date 2020/9/25/17:57
 */
public enum StatusCode {
    Success(1,"成功"),
    Fail(0,"失败"),
    InvaliadParam(-1,"非法参数"),
    ;
    private Integer code;
    private String  msg;

    StatusCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
