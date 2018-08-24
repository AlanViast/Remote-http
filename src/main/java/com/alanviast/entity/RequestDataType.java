package com.alanviast.entity;

/**
 * @author AlanViast
 */
public enum RequestDataType {

    /**
     * 键值对方式提交数据
     */
    APPLICATION_FORM_URLENCODED("application/x-www-form-urlencoded"),

    /**
     * 数据以JSON形式提交
     */
    APPLICATION_JSON("application/json"),


    MULTIPART_FORM_DATA("multipart/form-data");

    private String value;

    RequestDataType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
