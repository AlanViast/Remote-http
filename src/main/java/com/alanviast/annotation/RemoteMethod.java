package com.alanviast.annotation;

import com.alanviast.entity.RequestDataType;
import com.alanviast.entity.RequestMethod;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RemoteMethod {

    /**
     * 请求URL
     */
    String value();

    /**
     * 请求方法
     */
    RequestMethod method() default RequestMethod.GET;

    RequestDataType dataType() default RequestDataType.APPLICATION_JSON;
}
