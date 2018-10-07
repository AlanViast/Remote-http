package com.alanviast.handler.impl;

import com.alanviast.entity.RequestDataType;
import com.alanviast.entity.RequestMethod;
import com.alanviast.handler.RequestHandler;
import com.alanviast.util.ParameterUtils;
import com.alanviast.util.UrlUtils;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.StringEntity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * 默认的请求类
 *
 * @author AlanViast
 */
public class HttpClientFluentRequestHandler implements RequestHandler {

    private RequestMethod requestMethod = RequestMethod.GET;
    private RequestDataType requestDataType = RequestDataType.APPLICATION_JSON;

    private String url = null;
    private Map<String, Object> queryParameter = new HashMap<>();
    private Map<String, Object> bodyParameter = new HashMap<>();
    private Map<String, String> headers = new HashMap<>();

    @Override
    public void setMethod(RequestMethod requestMethod, String url, RequestDataType requestDataType) {
        this.requestMethod = requestMethod;
        this.url = url;
        this.requestDataType = requestDataType;
    }

    @Override
    public void addQuery(String key, Object value) {
        queryParameter.put(key, value);
    }

    @Override
    public void addParameter(String key, Object value) {
        bodyParameter.put(key, value);
    }

    @Override
    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    @Override
    public void extendRequestHandler(RequestHandler requestHandler) {
        queryParameter.forEach(requestHandler::addQuery);
        bodyParameter.forEach(requestHandler::addParameter);
        headers.forEach(requestHandler::addHeader);
    }

    @Override
    public String execute() {
        try {
            Request request = buildRequest();
            if (request == null) {
                throw new RuntimeException("method and url not setting");
            }
            headers.forEach(request::addHeader);

            Response response = request.execute();
            return response.returnContent().asString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Request buildRequest() {
        try {
            Request request;
            String targetUrl = UrlUtils.formatUrl(this.url, queryParameter);
            switch (requestMethod) {
                case GET:
                    request = Request.Get(targetUrl);
                    break;
                case POST:
                    request = Request.Post(targetUrl).body(new StringEntity(ParameterUtils.formatBody(this.requestDataType, bodyParameter)));
                    break;
                case PATCH:
                    request = Request.Patch(targetUrl).body(new StringEntity(ParameterUtils.formatBody(this.requestDataType, bodyParameter)));
                    break;
                case PUT:
                    request = Request.Put(targetUrl).body(new StringEntity(ParameterUtils.formatBody(this.requestDataType, bodyParameter)));
                    break;
                case DELETE:
                    request = Request.Delete(targetUrl);
                    break;
                default:
                    request = Request.Get(targetUrl);
                    break;
            }
            return request;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
