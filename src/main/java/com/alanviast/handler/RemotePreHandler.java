package com.alanviast.handler;

/**
 * @author AlanViast
 * header处理类
 */
public interface RemotePreHandler {


    /**
     * 预处理类, 处理所有请求的Header或者参数.
     * 在构建参数到Request之前执行
     *
     * @param requestHandler 请求容器接口
     */
    void handler(RequestHandler requestHandler);
}
