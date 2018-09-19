package com.alanviast.handler;

public class PreHanderTest implements RemotePreHandler {

    @Override
    public void handler(RequestHandler requestHandler) {
        requestHandler.addHeader(Long.toString(System.currentTimeMillis()), "testHeader");
    }

}
