package com.jingxc.sever.config;

import com.jingxc.sever.resolver.WhitelistResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

//@Configuration
public class ResolverConfiguration implements WebMvcConfigurer {

    @Bean
    WhitelistResolver whitelistResolver(){
        return  new WhitelistResolver();
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(whitelistResolver());
        System.out.println(argumentResolvers);
    }
}
