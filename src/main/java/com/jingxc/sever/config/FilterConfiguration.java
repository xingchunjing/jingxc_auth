package com.jingxc.sever.config;

import com.jingxc.sever.fileter.WhitelistFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class FilterConfiguration {

    @Bean
    public FilterRegistrationBean someFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new WhitelistFilter());
        registration.addUrlPatterns("/*");
        registration.setName("whitelistFilter");
        registration.setOrder(1); // 设置过滤器被调用的顺序
        return registration;
    }
}
