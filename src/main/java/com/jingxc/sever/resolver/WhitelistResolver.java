package com.jingxc.sever.resolver;

import com.jingxc.sever.util.RedisCacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

public class WhitelistResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private RedisCacheUtil redisCacheUtil;
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        //如果函数包含我们的自定义注解，那就走resolveArgument()函数
        return methodParameter.hasParameterAnnotation(WhiteList2.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {
        System.out.println("TestArgumentResolver:resolveArgument");
        WhiteList2 params = methodParameter.getParameterAnnotation(WhiteList2.class);
        String paramName = params.value();
        if (paramName == null) {
            paramName = methodParameter.getParameterName();
        }
        //简单的案例：如果客户端未传值，就设置默认值
        String res = String.valueOf(nativeWebRequest.getNativeRequest(HttpServletRequest.class).getParameter(paramName));
        String isStartWhiteListFunction = redisCacheUtil.getValue("IS_START_WHITE_LIST_FUNCTION");
        if("1".equals(isStartWhiteListFunction)){
            boolean exists = redisCacheUtil.exists(res);
            if(!exists){
                throw new RuntimeException("token校验失败");
            }
        }
        return res == null ? params.defaultValue() : res;
    }
}
