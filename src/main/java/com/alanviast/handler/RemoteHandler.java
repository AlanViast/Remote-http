package com.alanviast.handler;

/**
 * @author AlanViast
 */
public interface RemoteHandler {

    /**
     * 处理对应的方法
     *
     * @param requestHandler 请求容器
     */
    void handler(RequestHandler requestHandler);

}
