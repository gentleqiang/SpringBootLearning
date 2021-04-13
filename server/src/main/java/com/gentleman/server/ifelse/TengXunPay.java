package com.gentleman.server.ifelse;

import org.springframework.stereotype.Service;

@PayCode(code = "tengxun",name = "腾讯支付方式")
@Service
public class TengXunPay implements Pay{

    @Override
    public void toPay() {
        System.out.println("==========腾讯支付");
    }
}
