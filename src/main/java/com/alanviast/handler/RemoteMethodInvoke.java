package com.alanviast.handler;

import com.alanviast.annotation.Param;
import com.alanviast.annotation.Query;
import com.alanviast.annotation.RemoteMethod;
import com.alanviast.annotation.RequestBody;
import com.alanviast.entity.RequestContainer;
import com.alanviast.util.JsonUtils;
import org.apache.http.client.fluent.Response;
import org.apache.http.util.Asserts;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.function.Consumer;

/**
 * @author AlanViast
 */
public class RemoteMethodInvoke implements InvocationHandler {

    private HandlerFactory handlerFactory;

    public RemoteMethodInvoke() {
        this.handlerFactory = new HandlerFactory();
    }

    private Consumer<RequestContainer> preRequestConsumer = requestContainer -> {
    };

    public void preRequest(Consumer<RequestContainer> consumer) {
        preRequestConsumer.andThen(consumer);
    }

    public <T> T generate(Class<T> tClass) {
        // TODO 验证接口是否有注解
        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{tClass}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        // pre handler
        // 封装成具体的请求对象
        RemoteMethod remoteMethod = method.getAnnotation(RemoteMethod.class);
        Asserts.check(null != remoteMethod && !remoteMethod.value().isEmpty(), "not a remote invoke method");

        RequestContainer requestContainer = new RequestContainer();
        requestContainer.setProxyMethod(method);
        requestContainer.setUrl(remoteMethod.value());
        requestContainer.setMethod(remoteMethod.method());

        // 把注解的参数都封装到requestContainer中
        this.putParam(method, args, requestContainer);

        // 前置处理器执行
        this.preRequestConsumer.accept(requestContainer);
        Response response = handlerFactory.handler(requestContainer);
        return JsonUtils.parse(response.returnContent().asString(requestContainer.getCharset()), method.getReturnType());
        // after handler
    }

    /**
     * 封装具体请求, 把body还有query格式化到Request里面
     *
     * @param method           代理方法
     * @param args             代理方法的参数
     * @param requestContainer 请求容器
     */
    private void putParam(Method method, Object[] args, RequestContainer requestContainer) {
        Parameter[] parameters = method.getParameters();

        for (int i = 0; i < parameters.length; i++) {

            // TODO 如果不是原声类型要将他解析成具体的Map再全部放入
            Parameter parameter = parameters[i];
            if (parameter.isAnnotationPresent(Param.class)) {
                Param param = parameter.getAnnotation(Param.class);
                requestContainer.query(param.value(), args[i]);

            } else if (parameter.isAnnotationPresent(RequestBody.class)) {
                requestContainer.getBody().putAll(JsonUtils.toMap(args[i]));

            } else if (parameter.isAnnotationPresent(Query.class)) {
                requestContainer.getBody().putAll(JsonUtils.toMap(args[i]));

            } else {
                // 暂时不处理没有注解的参数
            }

        }
    }

}
