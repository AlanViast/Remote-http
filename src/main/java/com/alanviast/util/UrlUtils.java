package com.alanviast.util;

import org.apache.http.client.utils.URIBuilder;

import java.net.URISyntaxException;
import java.util.Map;

/**
 * URL 工具类
 *
 * @author AlanViast
 */
public class UrlUtils {


    /**
     * 根据URL以及参数集格式化
     *
     * @param url       对应的url, 如 http://www.baidu.com
     * @param parameter 参数Map
     * @return 格式化后的url http://www.baidu.com?a=10&b=10
     */
    public static String formatUrl(String url, Map<String, Object> parameter) {
        try {
            URIBuilder uriBuilder = new URIBuilder(url);
            parameter.forEach((key, value) -> uriBuilder.addParameter(key, value.toString()));
            return uriBuilder.build().toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 路径格式化
     *
     * @param url          域名
     * @param resourcePath 资源名
     * @return
     */
    public static String formatUrl(String url, String resourcePath) {
        try {
            URIBuilder uriBuilder = new URIBuilder(url);
            uriBuilder.setPath(uriBuilder.getPath() + resourcePath);

            return uriBuilder.build().toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}
