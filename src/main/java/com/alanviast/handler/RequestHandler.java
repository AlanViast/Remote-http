package com.alanviast.handler;

import com.alanviast.entity.RequestDataType;
import com.alanviast.entity.RequestMethod;

/**
 * 请求接口方法
 *
 * @author AlanViast
 */
public interface RequestHandler {

    /**
     * 设置请求的方法
     *
     * @param requestMethod   请求方法类型 GET, POST
     * @param url             请求的URL
     * @param requestDataType 请求主体的类型
     */
    void setMethod(RequestMethod requestMethod, String url, RequestDataType requestDataType);

    /**
     * 添加请求的参数, URL中的参数
     *
     * @param key   查询参数key
     * @param value 查询参数value
     */
    void addQuery(String key, Object value);

    /**
     * 添加请求的Body参数
     *
     * @param key   请求参数key
     * @param value 请求参数value
     */
    void addParameter(String key, Object value);

    /**
     * 添加请求头
     *
     * @param key   请求头key
     * @param value 请求头value
     */
    void addHeader(String key, String value);

    /**
     * 将当前的 Query Body 以及Header传递给传进来的对象
     *
     * @param requestHandler 请求
     */
    void extendRequestHandler(RequestHandler requestHandler);

    /**
     * 执行请求方法
     *
     * @return 返回response body
     */
    String execute();
}
