package com.alanviast.annotation;


import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RemoteApi {

    String domain() default "";
}
