package com.gentleman.server.global;

public enum ErrorEnum {
    SUCCESS(200,"成功"),
    Not_found(404,"未找到该资源"),
    NO_AUTH(401,"未登录"),
    ;
    private Integer code;

    private String msg;

    ErrorEnum(){

    }
    ErrorEnum(Integer code, String msg) {
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
