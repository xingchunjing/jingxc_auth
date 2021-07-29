package com.jingxc.sever.resolver;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface WhiteList2 {

    String value() default "";

    boolean required() default false;

    String defaultValue() default "test";

}
