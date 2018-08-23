package com.alanviast.entity;

import lombok.Data;
import org.apache.http.client.utils.URIBuilder;

import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author AlanViast
 * 存放每个请求的所有属性
 */
@Data
public class RequestContainer {

    private Method proxyMethod;


    private Charset charset = StandardCharsets.UTF_8;
    private String url;
    private RequestMethod method = RequestMethod.GET;
    private Map<String, Object> queryMap;
    private Map<String, Object> body;
    private Map<String, String> headers;


    public Map<String, Object> query(String key, Object value) {
        this.getQueryMap().put(key, value);
        return this.getQueryMap();
    }

    public Map<String, Object> body(String key, Object value) {
        this.getBody().put(key, value);
        return this.getBody();
    }

    public Map<String, String> header(String key, String value) {
        this.getHeaders().put(key, value);
        return this.getHeaders();
    }

    public Map<String, Object> getQueryMap() {
        this.queryMap = Optional.ofNullable(queryMap).orElse(new HashMap<>(10));
        return queryMap;
    }

    public Map<String, Object> getBody() {
        this.body = Optional.ofNullable(body).orElse(new HashMap<>(10));
        return body;
    }

    public Map<String, String> getHeaders() {
        this.headers = Optional.ofNullable(headers).orElse(new HashMap<>(10));
        return headers;
    }


    public String getUrl() {
        try {
            URIBuilder uriBuilder = new URIBuilder(this.url);
            this.getQueryMap().forEach((key, value) -> {
                uriBuilder.addParameter(key, value.toString());
            });
            return uriBuilder.build().toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}