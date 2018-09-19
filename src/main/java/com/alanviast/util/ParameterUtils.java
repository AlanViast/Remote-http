package com.alanviast.util;

import com.alanviast.entity.RequestDataType;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author AlanViast
 */
public class ParameterUtils {


    /**
     * application/x-www-form-urlencoded 参数格式化方法
     *
     * @param param 需要格式化的参数
     * @return 格式化后的请求参数
     */
    public static String formatFormEncode(Map<String, Object> param) {
        List<NameValuePair> nameValuePairs = param.entrySet().stream()
                .map(item -> new BasicNameValuePair(item.getKey(), item.getValue().toString())).collect(Collectors.toList());
        return URLEncodedUtils.format(nameValuePairs, StandardCharsets.UTF_8);
    }

    /**
     * 格式化请求的主体内容
     *
     * @param requestDataType 请求类型
     * @param parameter       参数类型
     * @return 返回主体内容
     */
    public static String formatBody(RequestDataType requestDataType, Map<String, Object> parameter) {
        switch (requestDataType) {
            case APPLICATION_FORM_URLENCODED:
                return ParameterUtils.formatFormEncode(parameter);
            case APPLICATION_JSON:
                return JsonUtils.format(parameter);
            default:
                // 暂时不支持的类型
                throw new RuntimeException("not support request data type!");
        }
    }

}
