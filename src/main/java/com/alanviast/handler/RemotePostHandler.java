package com.alanviast.handler;

import com.alanviast.annotation.RemotePost;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author AlanViast
 */
public class RemotePostHandler implements RemoteHandler {

    @Override
    public String handler(Method method, Object[] args) throws IOException {

        RemotePost remotePost = method.getAnnotation(RemotePost.class);
        String requestUrl = String.format(remotePost.value(), args);

        Response response = Request.Post(requestUrl).execute();

        System.out.println(response.returnContent().asString());
        return response.returnContent().asString();
    }
}
