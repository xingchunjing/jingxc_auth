package com.jingxc.sever.interceptor;

import com.jingxc.sever.util.RedisCacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WhitelistInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisCacheUtil redisCacheUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        WhiteList1 whitelist = ((HandlerMethod) handler).getMethodAnnotation(WhiteList1.class);
        boolean values = whitelist.values();//通过 request 获取请求参数，通过 whitelist 变量获取注解参数
        if(values){
            String token = request.getParameter("token");
            String isStartWhiteListFunction = redisCacheUtil.getValue("IS_START_WHITE_LIST_FUNCTION");
            if("1".equals(isStartWhiteListFunction)){
                boolean exists = redisCacheUtil.exists(token);
                if(!exists){
                    response.setStatus(401);
                    return false;
                }
            }

        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 方法在Controller方法执行结束后执行
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 在view视图渲染完成后执行
    }
}
