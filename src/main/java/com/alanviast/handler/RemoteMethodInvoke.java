package com.alanviast.handler;

import com.alanviast.annotation.*;
import com.alanviast.entity.RequestMethod;
import com.alanviast.handler.impl.HttpClientFluentRequestHandler;
import com.alanviast.util.JsonUtils;
import com.alanviast.util.StringUtils;
import com.alanviast.util.SuppressWarningType;
import com.alanviast.util.UrlUtils;
import org.apache.http.util.Asserts;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;

/**
 * @author AlanViast
 */
public class RemoteMethodInvoke implements InvocationHandler {

    private RequestHandler defaultRequestHandle = new HttpClientFluentRequestHandler();

    @SuppressWarnings(SuppressWarningType.UNCHECKED)
    public <T> T generate(Class<T> tClass) {
        // TODO 验证接口是否有注解
        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{tClass}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RequestHandler requestHandler = new HttpClientFluentRequestHandler();
        // 设置全局参数
        defaultRequestHandle.extendRequestHandler(requestHandler);

        // 封装成具体的请求对象
        RemoteMethod remoteMethod = method.getAnnotation(RemoteMethod.class);
        Asserts.check(null != remoteMethod && !remoteMethod.value().isEmpty(), "not a remote invoke method");

        String targetUrl = UrlUtils.formatUrl(this.getDomain(method), remoteMethod.value());
        requestHandler.setMethod(remoteMethod.method(), targetUrl, remoteMethod.dataType());

        // 封装Header注解
        this.putHeader(method, requestHandler);
        // 把注解的参数都封装到requestContainer中
        this.putParam(remoteMethod.method(), method, args, requestHandler);
        // 前置处理器执行
        this.preHandler(method, requestHandler);

        return JsonUtils.parse(requestHandler.execute(), method.getReturnType());
        // after handler
    }

    /**
     * 获取对应的 RemoteApi 的 domain 值
     *
     * @return domain值
     */
    private String getDomain(Method method) {
        Class targetInterface = method.getDeclaringClass();
        if (targetInterface.isAnnotationPresent(RemoteApi.class)) {
            RemoteApi remoteDomain = (RemoteApi) targetInterface.getAnnotation(RemoteApi.class);
            return remoteDomain.domain();
        }
        return StringUtils.EMPTY;
    }

    public RequestHandler getDefaultRequestHandle() {
        return defaultRequestHandle;
    }

    public void addHeader(String key, String value) {
        defaultRequestHandle.addHeader(key, value);
    }


    private void preHandler(Method method, RequestHandler requestHandler) throws IllegalAccessException, InstantiationException {
        for (PreHandler preHandler : method.getAnnotationsByType(PreHandler.class)) {
            Class<? extends RemotePreHandler> clazz = preHandler.value();
            clazz.newInstance().handler(requestHandler);
        }
    }


    /**
     * 解析请求方法上的Header
     *
     * @param method         被调用的方法
     * @param requestHandler 请求容器
     */
    private void putHeader(Method method, RequestHandler requestHandler) {
        for (Header header : method.getAnnotationsByType(Header.class)) {
            requestHandler.addHeader(header.name(), header.value());
        }
    }

    /**
     * 封装具体请求, 把body还有query格式化到Request里面
     *
     * @param requestMethod  请求的方法类型
     * @param method         代理方法
     * @param args           代理方法的参数
     * @param requestHandler 请求容器
     */
    private void putParam(RequestMethod requestMethod, Method method, Object[] args, RequestHandler requestHandler) {
        Parameter[] parameters = method.getParameters();

        for (int i = 0; i < parameters.length; i++) {

            // TODO 如果不是原声类型要将他解析成具体的Map再全部放入
            Parameter parameter = parameters[i];
            if (parameter.isAnnotationPresent(Param.class)) {
                Param param = parameter.getAnnotation(Param.class);

                // Get 请求放在QueryMap中, 其他的放Body
                if (RequestMethod.GET.equals(requestMethod)) {
                    requestHandler.addQuery(param.value(), args[i]);
                } else {
                    requestHandler.addParameter(param.value(), args[i]);
                }

            } else if (parameter.isAnnotationPresent(RequestBody.class)) {
                JsonUtils.toMap(args[i]).forEach(requestHandler::addParameter);

            } else if (parameter.isAnnotationPresent(Query.class)) {
                JsonUtils.toMap(args[i]).forEach(requestHandler::addQuery);
            } else if (parameter.isAnnotationPresent(Header.class)) {
                // 添加请求头
                Header annotation = parameter.getAnnotation(Header.class);
                requestHandler.addHeader(annotation.name(), args[i].toString());
            }
        }
    }

}
