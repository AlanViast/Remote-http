package com.alanviast.handler;

import com.alanviast.remote.RemoteApiManager;
import com.alanviast.util.JsonUtils;
import org.junit.Test;

import java.util.Map;

public class RemoteMethodInvokeTest {

    @Test
    public void generate() {
        ImRemoteMethod imRemoteMethod = RemoteApiManager.generateInstance(ImRemoteMethod.class);
        Map response = imRemoteMethod.get(111);
        System.out.println(JsonUtils.format(response));
    }
}