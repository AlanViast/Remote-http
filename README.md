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

```java
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

```json
{"ActionStatus":"FAIL","ErrorCode":70032,"ErrorInfo":""}
{"ActionStatus":"FAIL","ErrorCode":70032,"ErrorInfo":""}
```

4. 通过注解设置Header

```java
@RemoteMethod(value = "https://console.tim.qq.com/v4/sns/friend_update?usersig=xxx&identifier=admin&sdkappid=88888888&random=99999999&contenttype=json", method = RequestMethod.POST)
@Header(name = "test", value = "test2")
Map post(@RequestBody Map map, @Param("test2") String test2);
```

5. 通过提供对应的Handler设置参数


```java
public class PreHanderTest implements RemotePreHandler {

    @Override
    public void handler(RequestHandler requestHandler) {
        requestHandler.addHeader(Long.toString(System.currentTimeMillis()), "testHeader");
    }

}
```

```java
@RemoteMethod(value = "https://console.tim.qq.com/v4/sns/friend_update?usersig=xxx&identifier=admin&sdkappid=88888888&random=99999999&contenttype=json", method = RequestMethod.POST)
@PreHandler(PreHanderTest.class)
Map post(@RequestBody Map map, @Param("test2") String test2);
```

6. 通过Manager对象设置请求头

```java
RemoteApiManager remoteApiManager = RemoteApiManager.getInstance();
remoteApiManager.addHeader("globalTestHeader", "test12324");

ImRemoteMethod imRemoteMethod = remoteApiManager.generate(ImRemoteMethod.class);
```

> [retrofit](https://square.github.io/retrofit/)


### TODO

- [ ] 全局容器, 处理请求认证之类的Header, 不需要handler每次去设置.
- [ ] 接口定义对应的Domain注解, 方法中对应uri即刻