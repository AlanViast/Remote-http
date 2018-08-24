package com.alanviast.remote;

import com.alanviast.handler.RemoteMethodInvoke;

import java.nio.charset.Charset;

/**
 * @author AlanViast
 */
public class RemoteApiManager {

    private RemoteMethodInvoke remoteMethodInvoke = new RemoteMethodInvoke();


    /**
     * 生成代理方法
     *
     * @param tClass 代理接口
     */
    public <T> T generate(Class<T> tClass) {
        return remoteMethodInvoke.generate(tClass);
    }

    /**
     * 直接获取一个远程调用实例
     *
     * @param tClass 代理方法
     */
    public static <T> T generateInstance(Class<T> tClass) {
        return new RemoteMethodInvoke().generate(tClass);
    }

    /**
     * 获取一个远程调用管理器
     */
    public static RemoteApiManager getInstance() {
        return new RemoteApiManager();
    }


    /**
     * 给请求动态添加请求头
     *
     * @param key   Header Key
     * @param value Header Value
     */
    public void addHeader(String key, String value) {
        remoteMethodInvoke.preRequest(requestContainer -> requestContainer.header(key, value));
    }

    /**
     * 设置响应编码
     */
    public void setCharset(Charset charset) {
        remoteMethodInvoke.preRequest(requestContainer -> requestContainer.setCharset(charset));
    }
}
