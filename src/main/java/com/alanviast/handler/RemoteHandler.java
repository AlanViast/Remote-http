package com.alanviast.handler;

import java.io.IOException;
import java.lang.reflect.Method;

public interface RemoteHandler {


    String handler(Method method, Object[] args) throws IOException;

}
