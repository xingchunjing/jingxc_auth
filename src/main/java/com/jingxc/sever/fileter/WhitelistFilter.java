package com.jingxc.sever.fileter;

import com.jingxc.sever.util.RedisCacheUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import java.io.IOException;

@Log4j2
public class WhitelistFilter implements javax.servlet.Filter {

//    @Autowired
//    private RedisCacheUtil redisCacheUtil;



    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 初始化后被调用一次
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        /**
         * 使用spring上下文applicationContext可以取到bean。过滤器是servlet规范中定义的，并不归spring容器管理，也无法直接注入spring中的bean（会报错）
         */
        BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
        RedisCacheUtil redisCacheUtil= (RedisCacheUtil) factory.getBean("redisCacheUtil");


        String token = request.getParameter("token");
        String isStartWhiteListFunction = redisCacheUtil.getValue("IS_START_WHITE_LIST_FUNCTION");
        if("1".equals(isStartWhiteListFunction)){
            boolean exists = redisCacheUtil.exists(token);
            if(!exists){
                log.info("进入拦截，token检验失败，跳转自定义失败方法");
                throw new RuntimeException("token校验异常");
                //request.getRequestDispatcher("/error").forward(request,response);//跳转自己指定的url
                //return;

            }
        }

        chain.doFilter(request, response); //进入请求的url
    }

    @Override
    public void destroy() {
        // 被销毁时调用一次
    }
}
