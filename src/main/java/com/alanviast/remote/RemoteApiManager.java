package com.alanviast.remote;

import com.alanviast.handler.RemoteMethodInvoke;

import java.nio.charset.Charset;

/**
 * @author AlanViast
 */
public class RemoteApiManager {

    private RemoteMethodInvoke remoteMethodInvoke = new RemoteMethodInvoke();

    public void setCharset(Charset charset) {
        remoteMethodInvoke.preRequest(requestContainer -> requestContainer.setCharset(charset));
    }

    public <T> T generate(Class<T> tClass) {
        return remoteMethodInvoke.generate(tClass);
    }

    public static <T> T generateInstance(Class<T> tClass) {
        return new RemoteMethodInvoke().generate(tClass);
    }

    public static RemoteApiManager getInstance() {
        return new RemoteApiManager();
    }

}
