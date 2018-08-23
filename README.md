### 简易版HTTP远程调用


1. 定义一个接口
```java
public interface ImRemoteMethod {

    @RemoteMethod("https://console.tim.qq.com/v4/sns/friend_update?usersig=xxx&identifier=admin&sdkappid=88888888&random=99999999&contenttype=json")
    Map get(@Param("random") int random);


    @RemoteMethod(value = "https://console.tim.qq.com/v4/sns/friend_update?usersig=xxx&identifier=admin&sdkappid=88888888&random=99999999&contenttype=json", method = RequestMethod.POST)
    Map post(@RequestBody Map map, @Param("test2") String test2);
}
```

2. 调用该方法

```
public class RemoteMethodInvokeTest {

    @Test
    public void generate() {
        ImRemoteMethod imRemoteMethod = RemoteApiManager.generateInstance(ImRemoteMethod.class);
        Map response = imRemoteMethod.get(111);
        System.out.println(JsonUtils.format(response));

        response = imRemoteMethod.post(Collections.singletonMap("test", "test2"), "hello");
        System.out.println(JsonUtils.format(response));
    }
}
```

3. 输出结果

```
{"ActionStatus":"FAIL","ErrorCode":70032,"ErrorInfo":""}
{"ActionStatus":"FAIL","ErrorCode":70032,"ErrorInfo":""}
```

> [retrofit](https://square.github.io/retrofit/)