package com.jingxc.sever.aop;

import java.util.Arrays;

import com.jingxc.sever.util.RedisCacheUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Log4j2
public class WhitelistAopAspect {

    @Autowired
    private RedisCacheUtil redisCacheUtil;

    @Pointcut("@annotation(com.jingxc.sever.aop.WhiteList3)")
    public void controllerAspect() {

    }

    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint) {

        try {
            String name = joinPoint.getSignature().getName();
            Object[] args = joinPoint.getArgs();
            String token = String.valueOf(args[0]);
            log.warn("----进入方法" + name + ",----方法参数token为：" + token);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Around("controllerAspect()")
    public void doAfter(JoinPoint joinPoint) {

        try {
            String name = joinPoint.getSignature().getName();
            Object[] args = joinPoint.getArgs();
            String token = String.valueOf(args[0]);
            log.warn("----进入方法" + name + ",----方法参数token为：" + token);
            String isStartWhiteListFunction = redisCacheUtil.getValue("IS_START_WHITE_LIST_FUNCTION");
            if("1".equals(isStartWhiteListFunction)){
                boolean exists = redisCacheUtil.exists(token);
                if(!exists){
                    throw new RuntimeException("token校验失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterReturning(pointcut = "controllerAspect()", returning = "returnValue")
    public void doAfter(JoinPoint joinPoint, Object returnValue) {

        try {
            String name = joinPoint.getSignature().getName();
            log.warn("----方法" + name + "执行完成,----方法返回参数为：" + (returnValue == null ? "" : returnValue.toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterThrowing(value = "controllerAspect()", throwing = "e")
    public void doAfter(JoinPoint joinPoint, Exception e) {

        try {
            String name = joinPoint.getSignature().getName();
            log.warn("----方法" + name + "抛出异常,----异常信息为：" + e.getMessage());
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

}
