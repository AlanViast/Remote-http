package com.alanviast.handler;

import com.alanviast.annotation.RemoteGet;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;

import java.io.IOException;
import java.lang.reflect.Method;

public class RemoteGetHandler implements RemoteHandler {

    @Override
    public String handler(Method method, Object[] args) throws IOException {

        RemoteGet remotePost = method.getAnnotation(RemoteGet.class);
        String requestUrl = String.format(remotePost.value(), args);

        Response response = Request.Get(requestUrl).execute();

        return response.returnContent().asString();
    }

}
