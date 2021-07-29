package com.jingxc.sever.config;

import com.jingxc.sever.interceptor.WhitelistInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

//@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

    @Bean
    public WhitelistInterceptor myInterceptor() {
        return new WhitelistInterceptor();
    }



    @Override
    //此方法用来专门注册一个拦截器
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(myInterceptor())
                .addPathPatterns("/**")
                .order(1);
    }


}
