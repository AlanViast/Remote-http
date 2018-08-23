package com.alanviast.handler;

import com.alanviast.annotation.RemoteMethod;
import com.alanviast.entity.RequestContainer;
import com.alanviast.util.JsonUtils;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.function.Consumer;

/**
 * @author AlanViast
 */
public class HandlerFactory implements RemoteHandler {


    private Consumer<RequestContainer> preRequestConsumer = requestContainer -> {
        // TODO 解析参数?
        System.out.println("--------preRequestConsumer--------");
    };

    public Consumer<RequestContainer> preRequest(Consumer<RequestContainer> consumer) {
        preRequestConsumer.andThen(consumer);
        return preRequestConsumer;
    }

    @Override
    public Response handler(RequestContainer requestContainer) throws IOException {

        // TODO 封装具体请求, 把body还有query格式化到Request里面
        //requestContainer.getRequest().
        this.preRequestConsumer.accept(requestContainer);

        Request request = this.buildMethod(requestContainer);
        return request.execute();
    }


    public Request buildMethod(RequestContainer requestContainer) throws UnsupportedEncodingException {
        RemoteMethod remoteMethod = requestContainer.getProxyMethod().getAnnotation(RemoteMethod.class);
        if (null == remoteMethod) {
            throw new RuntimeException("not a remote invoke method");
        }
        requestContainer.setUrl(remoteMethod.value());

        String url = requestContainer.getUrl();
        StringEntity stringEntity = new StringEntity(JsonUtils.format(requestContainer.getBody()));
        switch (remoteMethod.method()) {
            case GET:
                return Request.Get(url);
            case POST:
                return Request.Post(url).body(stringEntity);
            default:
                throw new RuntimeException("not support remote invoke method");
        }
    }

    public static void main(String[] args) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder("http://www.baidu.com/?test=test");
        uriBuilder.addParameter("test2", "adsf");
        System.out.println(uriBuilder.build().toString());
    }

}
