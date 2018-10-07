package com.alanviast.remote;

import com.alanviast.handler.RemoteMethodInvoke;
import com.alanviast.handler.RequestHandler;

/**
 * @author AlanViast
 */
public class RemoteApiManager {

    private RemoteMethodInvoke remoteMethodInvoke = new RemoteMethodInvoke();

    private RemoteApiManager() {
    }


    private RequestHandler getRequestHandle() {
        return remoteMethodInvoke.getDefaultRequestHandle();
    }

    /**
     * 设置请求头
     *
     * @param key   请求头key
     * @param value 请求头value
     */
    public void addHeader(String key, String value) {
        getRequestHandle().addHeader(key, value);
    }

    /**
     * 设置全局请求参数
     *
     * @param key   KeyName
     * @param value Value
     */
    public void addParameter(String key, String value) {
        getRequestHandle().addQuery(key, value);
    }


    /**
     * 获取一个远程调用管理器
     */
    public static RemoteApiManager getInstance() {
        return new RemoteApiManager();
    }

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

}
