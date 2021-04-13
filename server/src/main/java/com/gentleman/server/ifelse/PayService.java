package com.gentleman.server.ifelse;

import com.google.common.collect.Maps;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PayService implements ApplicationListener<ContextRefreshedEvent> {

    private static Map<String,Pay> payMap = null;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        /*实现了ApplicationListener接口，就可以在onApplicationEvent方法中拿到ApplicationContext
        的实例，我们再获取打了payCode注解的类，放到一个map中，key就是payCode注解中的code，value就是一个实例*/
       ApplicationContext applicationContext =  contextRefreshedEvent.getApplicationContext();
       Map<String,Object>  beansWithAnnotation =  applicationContext.getBeansWithAnnotation(PayCode.class);

       if(beansWithAnnotation != null){
           payMap = Maps.newHashMap();
           beansWithAnnotation.forEach((key,value)->{
               String codeType = value.getClass().getAnnotation(PayCode.class).code();
               payMap.put(codeType, (Pay) value);
           });
       }
    }
    public Pay get(String code){
        return payMap.get(code);
    }
}
