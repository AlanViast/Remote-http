package com.alanviast.main;

import com.alanviast.annotation.RemoteGet;

import java.util.Map;

public interface ImRemoteMethod {


    @RemoteGet("https://console.tim.qq.com/v4/sns/friend_add?usersig=xxx&identifier=admin&sdkappid=88888888&random=%s&contenttype=json")
    Map get(int random);


    @RemoteGet("https://console.tim.qq.com/v4/sns/friend_update?usersig=xxx&identifier=admin&sdkappid=88888888&random=99999999&contenttype=json")
    Map get2();
}
