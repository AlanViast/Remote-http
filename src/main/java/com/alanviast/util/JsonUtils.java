package com.alanviast.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;

import java.io.IOException;
import java.util.Map;

/**
 * @author AlanViast
 */
public class JsonUtils {

    private static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
    }

    /**
     * 将JSON字符串转换成对象
     *
     * @param jsonString JSON字符串
     * @param tClass     对应的转换类
     * @param <T>        目标类型
     * @return JavaBean对象
     */
    public static <T> T parse(String jsonString, Class<T> tClass) {
        try {
            return objectMapper.readValue(jsonString, tClass);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 将 数据对象解析成 Map对象
     *
     * @param obj 需要解析的Bean
     * @return 返回转换后的Map对象
     */
    public static <T> Map<String, Object> toMap(T obj) {
        try {
            MapType mapType = objectMapper.getTypeFactory().constructMapType(Map.class, String.class, Object.class);
            return objectMapper.readValue(objectMapper.writeValueAsBytes(obj), mapType);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static <T> String format(T data) {
        try {
            return objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

    }
}
