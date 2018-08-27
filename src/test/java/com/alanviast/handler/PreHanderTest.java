package com.alanviast.handler;

import com.alanviast.entity.RequestContainer;

public class PreHanderTest implements RemotePreHandler {

    @Override
    public void handler(RequestContainer requestContainer) {
        requestContainer.header(Long.toString(System.currentTimeMillis()), "testHeader");
    }

}
