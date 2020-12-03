package com.abab.common.annotation;

import java.lang.annotation.*;

/**
 * @author alex
 * @Date: Created in 2020/12/03 11:50
 * @IDE: Intellij Idea 2020.3
 * @Description: 是否验证用户注解
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUser {

    //默认是拦截的，值为false时放行
    boolean value() default true;
}