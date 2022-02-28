# 什么是AOP？



**在不修改源代码的方式下，在主干功能里添加其它功能**



例子说明：

![image-20210806174704236](C:\Users\94125\AppData\Roaming\Typora\typora-user-images\image-20210806174704236.png)





# 底层原理



**使用动态代理实现。动态代理有两种情况：有接口、无接口**



## 情况一、有接口，使用JDK动态代理



***创建接口的实现类代理对象，在代理对象里面增强UserDaoImpl功能**

![image-20210806175649982](C:\Users\94125\AppData\Roaming\Typora\typora-user-images\image-20210806175649982.png)











## 情况二、无接口，使用CGLIB动态代理



**原始方式是通过创建子类重写父类方法实现功能增强。**

**动态代理是创建当前类的子类的代理对象实现**

![image-20210806180216528](C:\Users\94125\AppData\Roaming\Typora\typora-user-images\image-20210806180216528.png)









# 实现JDK动态代理



使用 java.lang 包下的 Proxy类的 newProxyInstance方法创建 代理对象

该方法有三个参数。

第一个参数：类加载器。

第二参数：增强方法所在类的接口，支持多个接口。

第三参数：实现接口 InvocationHandler，创建代理对象，写增强的方法



（1）创建接口，定义方法

```java
public interface UserDao {
    public int add(int a, int b);
    public String update(String id);
}
```



（2）创建接口实现类，实现方法

```java
public class UserDaoImpl implements UserDao {
    @Override
    public int add(int a, int b) {
        System.out.println("add方法执行了...");
        return a+b;
    }

    @Override
    public String update(String id) {
        return id;
    }
}
```



（3）使用Proxy类创建接口代理对象

```java
public class JDKProxy {
    public static void main(String[] args) {
        //创建接口实现类代理对象
        Class[] interfaces = {UserDao.class};
        /*  使用匿名内部类
            Proxy.newProxyInstance(JDKProxy.class.getClassLoader(), interfaces, new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    return null;
                }
            });
        */
        UserDao userDaoImpl = new UserDaoImpl();
        UserDao userDao = (UserDao) Proxy.newProxyInstance(JDKProxy.class.getClassLoader(), interfaces, new UserDaoProxy(userDaoImpl));
        int res = userDao.add(1,2);
        System.out.println("result = "+res);
    }
}

//创建代理对象代码
class UserDaoProxy implements InvocationHandler {

    //把实现类通过 有参构造 传递进来
    private Object obj;
    public UserDaoProxy(Object obj){
        this.obj = obj;
    }

    //增强的逻辑
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //方法之前
        System.out.println("方法之前执行..."+method.getName()+":传递参数..."+ Arrays.toString(args));

        //执行被增强的方法
        Object res = method.invoke(obj, args);


        //方法之后
        System.out.println("方法之后执行..."+obj);

        return res;
    }
}
```



**执行结果**

```
方法之前执行...add:传递参数...[1, 2]
add方法执行了...
方法之后执行...com.atguigu.spring.UserDaoImpl@1d44bcfa
result = 3
```







# AOP术语

**连接点**

*类里面哪些方法可以被增强，这些方法称为连接点



**切入点**

*实际被真正增强的方法，称为切入点



**通知（增强）**

*实际增加的逻辑部分称为通知，在登录方法中增加权限判断，权限判断就是通知

*通知有多种类型

（1）前置通知（在增加的方法之前执行）

（2）后置通知（之后执行）

（3）环绕通知（前后都执行）

（4）异常通知（出异常再执行）

（5）最终通知（相当于try catch中的finally）



**切面**

*是动作，把通知应用到切入点的过程称为切面







# Spring实现AOP操作的准备

```
Spring框架一般基于 AspectJ 实现 AOP操作

什么是 AspectJ ？

AspectJ 不是Spring组成部分，是独立AOP框架，一般把AspectJ和Spring框架一起使用，进行AOP操作
```





**引入依赖**

```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-aop</artifactId>
    <version>5.3.9</version>
</dependency>

<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-aspects</artifactId>
    <version>5.3.9</version>
</dependency>

<dependency>
    <groupId>cglib</groupId>
    <artifactId>cglib</artifactId>
    <version>3.2.4</version>
</dependency>

<dependency>
    <groupId>aopalliance</groupId>
    <artifactId>aopalliance</artifactId>
    <version>1.0</version>
</dependency>

<dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjweaver</artifactId>
    <version>1.6.8</version>
</dependency>
```







**切入点表达式**

（1）作用：知道哪个类里面的哪个方法进行增强

（2）语法结构：

```
execution([权限修饰符][返回类型][类全路径][方法名称]([参数列表]))
```



举例一：对com.atguigu.dao.BookDao类里面的add方法进行增强

```
execution(* com.atguigu.dao.BookDao.add(..))
```

举例二：对com.atguigu.dao.BookDao类里面的所有方法增强

```
execution(* com.atguigu.dao.BookDao.*(..))
```

举例三：对com.atguigu.dao包里所有类的所有方法增强

```
execution(* com.atguigu.dao.*.*(..))
```





# 请注意下面方式

下面的方式都是 基于自定义的api，没有办法在增强类中使用被增强方法的属性等信息，具有局限性

建议观看

```
https://www.bilibili.com/video/BV1WE411d7Dv?p=20&spm_id_from=pageDriver
```

视频中的方式是 基于spring原生api的，每个通知都有对应的接口，只需创建实现这些接口就行，这种方式可以获取被增强方法的属性等信息，具有更高的功能性。







# 基于AspectJ实现AOP操作（注解方式）

（1）创建类，在类里面定义方法

```java
//被增强的类
@Component
public class User {
    public void add(){
        System.out.println("add...");
    }
}
```



（2）创建增强类（编写增强逻辑）

```java
//增强类
@Component
@Aspect
public class UserProxy {
    //前置通知
    @Before(value = "execution(* aspectj.User.add())")
    public void before(){
        System.out.println("before...");
    }

    //后置通知（返回通知）
    @AfterReturning(value = "execution(* aspectj.User.add())")
    public void afterReturning(){
        System.out.println("afterReturning...");
    }

    //环绕通知
    @Around(value = "execution(* aspectj.User.add())")
    public void around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("环绕之前...");
        //执行被增强的方法
        proceedingJoinPoint.proceed();
        System.out.println("环绕之后...");
    }

    //异常通知
    @AfterThrowing(value = "execution(* aspectj.User.add())")
    public void afterThrowing(){
        System.out.println("afterThrowing...");
    }

    //最终通知
    @After(value = "execution(* aspectj.User.add())")
    public void after(){
        System.out.println("after...");
    }
}
```



（3）进行通知的配置

```java
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                        http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
                        http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd">

    <!-- 开启注解扫描 -->
    <context:component-scan base-package="aspectj"></context:component-scan>

    <!--开启Aspectj，生成代理对象-->
    <aop:aspectj-autoproxy></aop:aspectj-autoproxy>

</beans>
```



（4）测试

```java
@Test
public void test(){
    ApplicationContext context = new ClassPathXmlApplicationContext("proxy.xml");
    User user = context.getBean("user", User.class);
    user.add();
}
```



## 细节：

### 1、相同的切入点抽取

使用注解@Pointcut

```java
//增强类
@Component
@Aspect		//生成代理对象
public class UserProxy {
    //抽取相同的切入点表达式
    @Pointcut(value = "execution(* aspectj.User.add())")
    public void commonPoint(){}

    //前置通知
    @Before(value = "commonPoint()")
    public void before(){
        System.out.println("before...");
    }

    //后置通知（返回通知）
    @AfterReturning(value = "commonPoint()")
    public void afterReturning(){
        System.out.println("afterReturning...");
    }

    //环绕通知
    @Around(value = "commonPoint()")
    public void around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("环绕之前...");
        //执行被增强的方法
        proceedingJoinPoint.proceed();
        System.out.println("环绕之后...");
    }

    //异常通知
    @AfterThrowing(value = "commonPoint()")
    public void afterThrowing(){
        System.out.println("afterThrowing...");
    }

    //最终通知
    @After(value = "commonPoint()")
    public void after(){
        System.out.println("after...");
    }
}
```









### 2、有多个增强类对同一个方法进行增强，设置增强类优先级

在增强类上面添加注解@Order(值)，值越小，优先级越高

```java
@Component
@Aspect
@Order(1)
public class PersonProxy {
    @Before(value = "execution(* aspectj.User.add())")
    public void before(){
        System.out.println("person before...");
    }
}
```

```java
//增强类
@Component
@Aspect
@Order(3)
public class UserProxy {
    //抽取相同的切入点表达式
    @Pointcut(value = "execution(* aspectj.User.add())")
    public void commonPoint(){}

    //前置通知
    @Before(value = "commonPoint()")
    public void before(){
        System.out.println("before...");
    }
}
```











### 3、完全注解开发

```java
@Configuration
@ComponentScan(basePackages = "aspectj")            //开启注解扫描
@EnableAspectJAutoProxy(proxyTargetClass = true)   //生成代理对象
public class configAop {}
```

```java
//测试
@Test
public void Test(){
    ApplicationContext context = new AnnotationConfigApplicationContext(configAop.class);
    User user = context.getBean("user", User.class);
    user.add();
}
```









# 基于AspectJ实现AOP操作（xml文件）

（1）创建被增强类和增强类

```java
//被增强类
public class Book {
    public void buy(){
        System.out.println("buy...");
    }
}
```

```java
//增强类
public class BookProxy {
    public void before(){
        System.out.println("before...");
    }
}
```



（2）配置文件

```java
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                        http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd">

    <!--创建对象-->
    <bean id="book" class="proxyxml.Book"></bean>
    <bean id="bookProxy" class="proxyxml.BookProxy"></bean>

    <!--配置aop增强-->
    <aop:config>
        <!--切入点-->
        <aop:pointcut id="p" expression="execution(* proxyxml.Book.buy())"/>

        <!--配置切面-->
        <aop:aspect ref="bookProxy">
            <!--增强作用在具体的方法上-->
            <aop:before method="before" pointcut-ref="p"></aop:before>
        </aop:aspect>
    </aop:config>
</beans>
```



（3）测试

```java
@Test
public void xmlTest(){
    ApplicationContext context = new ClassPathXmlApplicationContext("xmlproxy.xml");
    Book book = context.getBean("book", Book.class);
    book.buy();
}
```





