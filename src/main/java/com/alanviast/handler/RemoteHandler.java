package com.alanviast.handler;

import com.alanviast.entity.RequestContainer;
import org.apache.http.client.fluent.Response;

import java.io.IOException;

/**
 * @author AlanViast
 */
public interface RemoteHandler {

    /**
     * 处理对应的方法
     *
     * @param requestContainer 请求参数
     * @return 返回请求响应对象
     * @throws IOException HTTP请求的异常
     */
    Response handler(RequestContainer requestContainer) throws IOException;

}
