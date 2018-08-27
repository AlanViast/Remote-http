package com.alanviast.handler;

import com.alanviast.entity.RequestContainer;
import com.alanviast.util.JsonUtils;

public class PreHanderTest implements RemotePreHandler {

    @Override
    public void handler(RequestContainer requestContainer) {
        requestContainer.header("testHeader", "testHeader");
        System.out.println(JsonUtils.format(requestContainer.getBody()));
    }

}
