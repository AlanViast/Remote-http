package com.alanviast.annotation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(value = Headers.class)
public @interface Header {

    String name();

    String value();
}
