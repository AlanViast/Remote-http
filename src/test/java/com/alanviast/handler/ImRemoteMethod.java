package com.alanviast.handler;

import com.alanviast.annotation.*;
import com.alanviast.entity.RequestDataType;
import com.alanviast.entity.RequestMethod;

import java.util.Map;

public interface ImRemoteMethod {


    @RemoteMethod("https://console.tim.qq.com/v4/sns/friend_update?usersig=xxx&identifier=admin&sdkappid=88888888&random=99999999&contenttype=json")
    Map get(@Param("random") int random);


    @RemoteMethod(value = "https://console.tim.qq.com/v4/sns/friend_update?usersig=xxx&identifier=admin&sdkappid=88888888&random=99999999&contenttype=json",
            method = RequestMethod.POST, dataType = RequestDataType.APPLICATION_FORM_URLENCODED)
    @PreHandler(PreHanderTest.class)
    @PreHandler(PreHanderTest.class)
    @Header(name = "test", value = "test2")
    @Header(name = "test", value = "test2")
    Map post(@RequestBody Map map, @Param("test2") String test2);

}
