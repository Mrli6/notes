# 整合JUnit4



引入依赖

```xml
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.12</version>
    <scope>test</scope>
</dependency>
```



```java
@RunWith(SpringJUnit4ClassRunner.class)	//单元测试框架
@ContextConfiguration("classpath:account.xml")	//加载配置文件
public class JTest4{
    @Autowired		//创建对象
    private UserService userService;
    
    @Test
    public void test1(){
        userService.accountMoney();
    }
}
```







# 整合JUnit5



引入依赖

```xml
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-api</artifactId>
    <version>5.3.2</version>
    <scope>test</scope>
</dependency>
```



```java
@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:account.xml")	//加载配置文件
public class JTest5{
    @Autowired		//创建对象
    private UserService userService;
    
    @Test
    public void test1(){
        userService.accountMoney();
    }
}
```

简化

```java
@SpringJUnitConfig(locations="classpath:account.xml")
public class JTest5{
    @Autowired		//创建对象
    private UserService userService;
    
    @Test
    public void test1(){
        userService.accountMoney();
    }
}
```

