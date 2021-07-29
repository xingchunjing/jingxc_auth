package com.jingxc.sever.controller;

import com.jingxc.sever.aop.WhiteList3;
import com.jingxc.sever.interceptor.WhiteList1;
import com.jingxc.sever.resolver.WhiteList2;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
public class AuthTokenController {

    @GetMapping("/test/filter")
    public String testFilter(){
        log.info("进入success方法>>>>>>>>>>>>>>>>>>>>");
        return "{\"result\":1,\"msg\":\"success\"}";
    }

    @GetMapping("/test/interceptor/true")
    @WhiteList1(values = true)
    public String testInterceptorTrue(){
        log.info("进入success方法>>>>>>>>>>>>>>>>>>>>");
        return "{\"result\":1,\"msg\":\"success\"}";
    }

    @GetMapping("/test/interceptor/false")
    @WhiteList1(values = false)
    public String testInterceptorFalse(){
        log.info("进入success方法>>>>>>>>>>>>>>>>>>>>");
        return "{\"result\":1,\"msg\":\"success\"}";
    }

    @GetMapping("/test/resolver")
    public String testResolver(@WhiteList2(value = "token") String cha){
        log.info("进入success方法>>>>>>>>>>>>>>>>>>>>");
        return "{\"result\":1,\"msg\":\"success\"}";
    }

    @GetMapping("/test/aop")
    @WhiteList3()
    public String testAop(String token){
        log.info("进入success方法>>>>>>>>>>>>>>>>>>>>");
        return "{\"result\":1,\"msg\":\"success\"}";
    }

    @GetMapping("/error")
    @WhiteList1(values = true)
    public String error(){
        log.info("进入error方法>>>>>>>>>>>>>>>>>>>>");
        return "{\"result\":-1,\"msg\":\"error\"}";
    }


}
