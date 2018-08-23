package com.alanviast.util;

import com.alanviast.annotation.Param;
import com.alanviast.annotation.RequestBody;

import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author AlanViast
 */
public class ParameterUtils {


    /**
     * 将Post的参数解析成Map
     *
     * @param parameters 参数反射对象
     * @param args       具体参数值
     * @return 解析后的Map
     */
    public static Map<String, Object> parsePostParameter(Parameter[] parameters, Object[] args) {

        Map<String, Object> paramMap = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            Parameter parameter = parameters[i];
            if (parameter.isAnnotationPresent(Param.class)) {
                Param param = parameter.getAnnotation(Param.class);
                paramMap.put(param.value(), args[i]);
            } else if (parameter.isAnnotationPresent(RequestBody.class)) {
                Map<String, Object> subMap = JsonUtils.toMap(args[i]);
                paramMap.putAll(subMap);
            } else {
                // TODO 没有名字的参数
            }
        }
        return paramMap;
    }
}
