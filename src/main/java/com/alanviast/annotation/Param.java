package com.alanviast.annotation;

import java.lang.annotation.*;


/**
 * 参数注解
 */
@Documented
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Param {

    String value();

}
