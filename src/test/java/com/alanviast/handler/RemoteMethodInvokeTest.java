package com.alanviast.handler;

import com.alanviast.remote.RemoteApiManager;
import com.alanviast.util.JsonUtils;
import org.junit.Test;

import java.util.Collections;
import java.util.Map;

public class RemoteMethodInvokeTest {

    @Test
    public void generate() {
        RemoteApiManager remoteApiManager = RemoteApiManager.getInstance();
        remoteApiManager.addHeader("globalTestHeader", "test12324");

        ImRemoteMethod imRemoteMethod = remoteApiManager.generate(ImRemoteMethod.class);
        Map response = imRemoteMethod.get(111);
        System.out.println(JsonUtils.format(response));

        response = imRemoteMethod.post(Collections.singletonMap("test", "test2"), "hello");
        System.out.println(JsonUtils.format(response));
    }
}