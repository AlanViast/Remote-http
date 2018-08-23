package com.alanviast.handler;

import com.alanviast.entity.RequestContainer;
import com.alanviast.util.JsonUtils;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.StringEntity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @author AlanViast
 */
public class HandlerFactory implements RemoteHandler {

    @Override
    public Response handler(RequestContainer requestContainer) throws IOException {
        return this.buildMethod(requestContainer).execute();
    }


    private Request buildMethod(RequestContainer requestContainer) throws UnsupportedEncodingException {
        String url = requestContainer.getUrl();

        String bodyString = JsonUtils.format(requestContainer.getBody());
        StringEntity stringEntity = new StringEntity(bodyString, requestContainer.getCharset());
        switch (requestContainer.getMethod()) {
            case GET:
                return Request.Get(url);
            case POST:
                return Request.Post(url).body(stringEntity);
            default:
                throw new RuntimeException("not support remote invoke method");
        }
    }

}
