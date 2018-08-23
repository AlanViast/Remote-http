package com.alanviast.handler;

import com.alanviast.entity.RequestContainer;
import com.alanviast.util.JsonUtils;
import org.apache.http.client.fluent.Response;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author AlanViast
 */
public class RemoteMethodInvoke implements InvocationHandler {

    private HandlerFactory handlerFactory;

    public RemoteMethodInvoke(HandlerFactory handlerFactory) {
        this.handlerFactory = handlerFactory;
    }

    public <T> T generate(Class<T> tClass) {
        // TODO 验证接口是否有注解
        InvocationHandler invocationHandler = new RemoteMethodInvoke(this.handlerFactory);
        return (T) Proxy.newProxyInstance(invocationHandler.getClass().getClassLoader(), new Class[]{tClass}, invocationHandler);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        // pre handler
        // TODO 封装成具体的请求对象
        RequestContainer requestContainer = new RequestContainer();
        requestContainer.setProxyMethod(method);

        // doSomething
        Response response = handlerFactory.handler(requestContainer);
        return JsonUtils.parse(response.returnContent().asString(), method.getReturnType());
        // after handler
    }
}
