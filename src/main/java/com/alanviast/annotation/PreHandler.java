package com.alanviast.annotation;

import com.alanviast.handler.RemotePreHandler;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PreHandler {

    Class<? extends RemotePreHandler> value();
}
