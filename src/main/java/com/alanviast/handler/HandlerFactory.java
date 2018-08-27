package com.alanviast.handler;

import com.alanviast.entity.RequestContainer;
import com.alanviast.util.JsonUtils;
import com.alanviast.util.ParameterUtils;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.StringEntity;

import java.io.IOException;

/**
 * @author AlanViast
 */
public class HandlerFactory implements RemoteHandler {

    @Override
    public Response handler(RequestContainer requestContainer) throws IOException {
        Request request = this.buildMethod(requestContainer);
        // 添加请求头
        requestContainer.getHeaders().forEach(request::addHeader);
        System.out.println(JsonUtils.format(requestContainer.getHeaders()));
        return request.execute();
    }


    private Request buildMethod(RequestContainer requestContainer) {
        String url = requestContainer.getUrl();

        String bodyString;
        switch (requestContainer.getRequestDataType()) {
            case APPLICATION_FORM_URLENCODED:
                bodyString = ParameterUtils.formatFormEncode(requestContainer.getBody());
                break;
            case APPLICATION_JSON:
            default:
                bodyString = JsonUtils.format(requestContainer.getBody());
        }

        StringEntity stringEntity = new StringEntity(bodyString, requestContainer.getCharset());
        stringEntity.setContentType(requestContainer.getRequestDataType().getValue());
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
