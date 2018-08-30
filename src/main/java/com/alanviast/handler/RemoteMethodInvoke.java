package com.alanviast.handler;

import com.alanviast.annotation.*;
import com.alanviast.entity.RequestContainer;
import com.alanviast.entity.RequestMethod;
import com.alanviast.util.JsonUtils;
import org.apache.http.client.fluent.Response;
import org.apache.http.util.Asserts;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;

/**
 * @author AlanViast
 */
public class RemoteMethodInvoke implements InvocationHandler {

    private HandlerFactory handlerFactory;

    public RemoteMethodInvoke() {
        this.handlerFactory = new HandlerFactory();
    }

    @SuppressWarnings("unchecked")
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
        requestContainer.setRequestDataType(remoteMethod.dataType());

        // 封装Header注解
        this.putHeader(method, requestContainer);
        // 把注解的参数都封装到requestContainer中
        this.putParam(method, args, requestContainer);
        // 前置处理器执行
        this.preHadler(method, requestContainer);
        Response response = handlerFactory.handler(requestContainer);
        return JsonUtils.parse(response.returnContent().asString(requestContainer.getCharset()), method.getReturnType());
        // after handler
    }

    private void preHadler(Method method, RequestContainer requestContainer) throws IllegalAccessException, InstantiationException {
        for (PreHandler preHandler : method.getAnnotationsByType(PreHandler.class)) {
            Class<? extends RemotePreHandler> clazz = preHandler.value();
            clazz.newInstance().handler(requestContainer);
        }
    }


    private void putHeader(Method method, RequestContainer requestContainer) {
        for (Header header : method.getAnnotationsByType(Header.class)) {
            requestContainer.header(header.name(), header.value());
        }
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

                // Get 请求放在QueryMap中, 其他的放Body
                if (RequestMethod.GET.equals(requestContainer.getMethod())) {
                    requestContainer.query(param.value(), args[i]);
                } else {
                    requestContainer.body(param.value(), args[i]);
                }

            } else if (parameter.isAnnotationPresent(RequestBody.class)) {
                requestContainer.getBody().putAll(JsonUtils.toMap(args[i]));

            } else if (parameter.isAnnotationPresent(Query.class)) {
                requestContainer.getQueryMap().putAll(JsonUtils.toMap(args[i]));
            }
        }
    }

}
