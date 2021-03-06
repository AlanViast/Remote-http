package com.alanviast.annotation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PreHandlers {

    PreHandler[] value();
}
