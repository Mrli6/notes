```
https://www.bilibili.com/video/BV1Vf4y127N5?p=53&spm_id_from=pageDriver
```

# 1 介绍

- Spring5添加的新模块，用于web开发，功能与SpringMVC类似，Webflux根据响应式编程应运而生。
- 传统web框架，如SpringMVC，是基于Servlet容器。Webflux是一种异步非阻塞的框架(Servlet3.1以后才支持)，核心基于Reactor的相关API实现
- 特点：
  -  非阻塞式：在有限资源下，提高系统吞吐量和伸缩性
  - 函数式编程：使用Java8函数式编程方式实现路由请求

- 比较SpringMVC

![image-20210830143707976](D:\面向工作学习\主流框架\Spring\6、Spring5新特性\Webflux.assets\image-20210830143707976.png)

都可以使用注解方式开发，都运行在Tomcat等容器中

Spring命令式编程，Webflux异步响应式编程







# 2 响应式编程(Reactor实现)



## 2.1 什么是响应式编程

a=b+c，b、c以后的变化会影响a



## 2.2 操作

- Ractor有两个核心类，Mono和Flux，这两个类都实现了接口
- Publisher，提供丰富操作符。Flux对象实现发布者，返回N个元素；Mono实现发布者，返回0或者1个元素。
- Flux和Mono都是数据流的发布者，使用Flux和Mono都可以发出三种数据信号：元素值，错误信号，完成信号
- 错误信号和完成信号都代表终止信号，终止信号用于告诉订阅者数据流结束了。错误信号终止数据流同时把错误信息传递给订阅者
- 只有订阅才能发送数据流



## 2.3 代码演示

引入依赖

```xml
<dependency>
    <groupId>io.projectreactor</groupId>
    <artifactId>reactor-core</artifactId>
    <version>3.4.9</version>
</dependency>
```



```java
public class TestReactor {
    public static void main( String[] args ) {
        // just方法直接声明
        Flux.just(1,2,3,4);
        Mono.just(1);

        // 只有订阅了才能发送
        Flux.just(1,2,3,4).subscribe(System.out::print);
        Mono.just(1).subscribe(System.out::print);

        //其他方法
        Integer[] array = {1,2,3,4};
        Flux.fromArray(array);

        List<Integer> list = Arrays.asList(array);
        Flux.fromIterable(list);

        Stream<Integer> stream = list.stream();
        Flux.fromStream(stream);
    }
}
```



## 2.4 操作符



### 2.4.1 map 元素映射为新元素





### 2.4.2 flatMap 元素映射为流

将小数据流合并成大数据流，我的理解是封装







# 3 Webflux执行流程和核心API

SpringWebflux基于Reactor，默认使用容器是Netty，Netty是高性能NIO（异步非阻塞）的框架









# 4 基于注解编程模型

引入依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-webflux</artifactId>
</dependency>
```



配置端口号

```properties
server.port=8081
```



创建实体类

```java
// 实体类
public class User {
    private String username;
    private String gender;
    private int id;

    public User(String username, String gender, int id) {
        this.username = username;
        this.gender = gender;
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
```



创建接口

```java
// 用户接口操作
public interface UserServiceDao {
    // 根据id查询用户
    Mono<User> getUserById(int id);

    // 查询所有用户
    Flux<User> getAllUser();

    // 添加用户
    Mono<Void> saveUserInfo(Mono<User> user);
}
```



实现接口

```java
public class UserServiceImpl implements UserServiceDao {

    // 创建map集合存储数据
    private final Map<Integer, User> users = new HashMap<>();

    public UserServiceImpl(){
        this.users.put(1, new User("lucy", "nan", 20));
        this.users.put(2, new User("mary", "nv", 30));
        this.users.put(3, new User("jack", "nan", 50));
    }


    @Override
    public Mono<User> getUserById(int id) {
        return Mono.justOrEmpty(this.users.get(id));
    }

    @Override
    public Flux<User> getAllUser() {
        return Flux.fromIterable(this.users.values());
    }

    @Override
    public Mono<Void> saveUserInfo(Mono<User> userMono) {
        return userMono.doOnNext(person -> {
            // 向map集合里放值
            int id = users.size() + 1;
            users.put(id, person);
        }).thenEmpty(Mono.empty());
    }
}
```



编写controller



```
https://www.bilibili.com/video/BV1Vf4y127N5?p=58
```









# 5 基于函数式编程模型

- 需要手动初始化服务器
- 有两个接口：RouterFunction（实现路由功能，请求转发给对应的 handler）和
  HandlerFunction（处理请求生成响应的函数）。



```
https://www.bilibili.com/video/BV1Vf4y127N5?p=59&spm_id_from=pageDriver
```



































