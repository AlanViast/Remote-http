package com.alanviast.handler;

import com.alanviast.annotation.RemoteGet;
import com.alanviast.annotation.RemotePost;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author AlanViast
 */
public class HandlerFactory implements RemoteHandler {


    private Map<String, RemoteHandler> remoteHandlerMap = new HashMap<>();

    public HandlerFactory() {
        remoteHandlerMap.put(RemotePost.class.getName(), new RemotePostHandler());
        remoteHandlerMap.put(RemoteGet.class.getName(), new RemoteGetHandler());
    }

    @Override
    public String handler(Method method, Object[] args) throws IOException {

        Annotation[] annotations = method.getDeclaredAnnotations();

        for (Annotation annotation : annotations) {
            String annotationName = annotation.annotationType().getName();
            if (remoteHandlerMap.containsKey(annotationName)) {
                return remoteHandlerMap.get(annotationName).handler(method, args);
            }
        }

        return null;
    }

}
