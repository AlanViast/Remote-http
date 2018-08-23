package com.alanviast.remote;

import com.alanviast.handler.HandlerFactory;
import com.alanviast.handler.RemoteMethodInvoke;

/**
 * @author AlanViast
 */
public class RemoteApiManager {


    private HandlerFactory factory = new HandlerFactory();
    private RemoteMethodInvoke remoteMethodInvoke = new RemoteMethodInvoke(factory);

    public <T> T generate(Class<T> tClass) {
        return remoteMethodInvoke.generate(tClass);
    }

}
