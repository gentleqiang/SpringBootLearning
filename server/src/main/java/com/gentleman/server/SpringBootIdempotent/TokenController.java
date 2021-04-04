package com.gentleman.server.SpringBootIdempotent;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gentleman.model.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 一粒尘埃
 * @date 2021/4/4/14:22
 */

@RestController
public class TokenController {

    private static final Logger log = LoggerFactory.getLogger(TokenController.class);

    private static final String prefix = "token/";

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping(method = RequestMethod.GET,value = prefix+"get")
    public String getToken()throws Exception{
        User user = new User(1,"一粒尘埃","12356");
        String value = objectMapper.writeValueAsString(user);

        String token = tokenUtils.generateToken(value);
        return token;
    }

    @RequestMapping(method = RequestMethod.POST,value = prefix+"valid")
    public String validToken(@RequestHeader(value = "token")String token)throws Exception{
        User user = new User(1,"一粒尘埃","12356");
        String value = objectMapper.writeValueAsString(user);

        Boolean isExist = tokenUtils.validToken(token,value);
        return isExist ? "正常调用" : "重复调用" ;
    }

}
