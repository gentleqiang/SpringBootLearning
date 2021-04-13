package com.gentleman.server.ifelse;

import com.gentleman.api.response.BaseResponse;
import com.gentleman.api.response.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private PayService payService;

    @RequestMapping(value = "ifElse/test",method = RequestMethod.GET)
    public BaseResponse test(){
        BaseResponse response = new BaseResponse(StatusCode.Success);
        String code = "tengxun";
        payService.get(code).toPay();
        return response;
    }
}
