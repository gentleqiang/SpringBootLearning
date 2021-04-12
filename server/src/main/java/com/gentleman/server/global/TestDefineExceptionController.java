package com.gentleman.server.global;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestDefineExceptionController {

    @RequestMapping(value = "query",method = RequestMethod.GET)
    public Result query(){
        throw new BusinessException(ErrorEnum.Not_found.getCode(),ErrorEnum.Not_found.getMsg());
    }
}
