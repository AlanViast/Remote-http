package com.alanviast.handler;

/**
 * @author AlanViast
 * header处理类
 */
public interface RemotePreHandler {


    /**
     * 预处理类, 处理所有请求的Header或者参数
     *
     * @param requestHandler 请求容器
     */
    void handler(RequestHandler requestHandler);
}
