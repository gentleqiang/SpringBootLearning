package com.gentleman.server.ifelse;

import org.springframework.stereotype.Service;


@PayCode(code = "ali",name = "支付宝支付方式")
@Service
public class AliPay implements Pay{
    @Override
    public void toPay() {
        System.out.println("==========阿里支付");
    }
}
