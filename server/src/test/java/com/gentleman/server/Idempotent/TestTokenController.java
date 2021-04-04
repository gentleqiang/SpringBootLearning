package com.gentleman.server.Idempotent;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author 一粒尘埃
 * @date 2021/4/4/14:42
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestTokenController {

    private static final Logger log = LoggerFactory.getLogger(TestTokenController.class);

    @Autowired
    private WebApplicationContext webApplicationContext;


    @Test
    public void interfaceIdempotent()throws Exception{

        //初始化MOCK MVC
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        //验证获取token接口
        String token = mockMvc.perform(MockMvcRequestBuilders.get("/token/get")
                                      .accept(MediaType.TEXT_HTML))
                                      .andReturn()
                                      .getResponse().getContentAsString();

        log.info("获取的token串：{}",token);

        //循环五次进行调用
        for (int i = 0; i < 5; i++) {

            String result = mockMvc.perform(MockMvcRequestBuilders.post("/token/valid")
                    .header("token",token)
                    .accept(MediaType.TEXT_HTML))
                    .andReturn()
                    .getResponse().getContentAsString();

            log.info("第{}次调用,校验的结果：{}",i+1,result);
        }
    }



}
