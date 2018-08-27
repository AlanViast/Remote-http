package com.alanviast.handler;

import com.alanviast.entity.RequestContainer;

/**
 * @author AlanViast
 * header处理类
 */
public interface RemotePreHandler {


    /**
     * 预处理类, 处理所有请求的Header或者参数
     *
     * @param requestContainer 请求容器
     */
    void handler(RequestContainer requestContainer);
}
