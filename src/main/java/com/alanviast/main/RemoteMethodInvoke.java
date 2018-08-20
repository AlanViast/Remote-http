package com.alanviast.main;

import com.alanviast.handler.HandlerFactory;
import com.alanviast.util.JsonUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * @author AlanViast
 */
public class RemoteMethodInvoke implements InvocationHandler {

    private HandlerFactory factory = new HandlerFactory();

    public static <T> T generate(Class<T> tClass) {
        InvocationHandler invocationHandler = new RemoteMethodInvoke();
        return (T) Proxy.newProxyInstance(invocationHandler.getClass().getClassLoader(), new Class[]{tClass}, invocationHandler);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        // pre handler

        // doSomething
        String response = factory.handler(method, args);
        return JsonUtils.prase(response, method.getReturnType());
        // after handler
    }

    public static void main(String[] args) {
        ImRemoteMethod imRemoteMethod = RemoteMethodInvoke.generate(ImRemoteMethod.class);
        Map response = imRemoteMethod.get2();
        System.out.println(response.get("ErrorCode"));
    }
}
