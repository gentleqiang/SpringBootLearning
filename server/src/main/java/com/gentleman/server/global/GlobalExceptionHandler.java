package com.gentleman.server.global;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {


    /**
     * 处理自定义异常
     */
     @ExceptionHandler(value = BusinessException.class)
     public Result undefineError(BusinessException businessException){
         return Result.defineError(businessException);
     }

    /**
     * 处理其他异常
     */
    @ExceptionHandler(value = Exception.class)
    public Result otherError(ErrorEnum errorEnum){
        return Result.otherError(ErrorEnum.NO_AUTH);
    }
}
