package com.gentleman.server.ifelse;

import org.springframework.stereotype.Service;


@PayCode(code = "jingdong",name = "京东支付方式")
@Service
public class JingDongPay implements Pay{
    @Override
    public void toPay() {
        System.out.println("==========京东支付");
    }
}
