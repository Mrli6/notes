# Spring有两种类型的Bean



#### 普通Bean：在配置文件中定义bean类型就是返回类型

```xml
<bean id="books" class="com.atguigu.spring.Books">
	<property name="booklist" ref="booklist"></property>
</bean>
```

定义bean类型为Books类型

```java
ApplicationContext context = new ClassPathXmlApplicationContext("testbean.xml");
Books books = context.getBean("books",Books.class);
```

返回的对象类型也是Books类型





#### 工厂Bean（FactoryBean）：在配置文件中定义bean类型可以和返回类型不同

创建类，让这个类作为工厂Bean，实现接口FactoryBean及其方法

```java
public class MyFactoryBean implements FactoryBean<Courses> {
    @Override
    public Courses getObject() throws Exception {
        Courses courses = new Courses();
        courses.setCname("abc");
        return courses;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
```

```xml
<bean id="myFactoryBean" class="com.atguigu.spring.MyFactoryBean">
</bean>
```

```java
public void test() {
    ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
    Courses course = context.getBean("myFactoryBean",Courses.class);

    System.out.println(course);
}
```











# Bean作用域

Spring里面，bean实例有两种，单实例和多实例。默认情况下是单实例

#### 单实例

```xml
<bean id="books" class="com.atguigu.spring.Books">
</bean>
```

```java
@Test
public void testBooks() {
    ApplicationContext context = new ClassPathXmlApplicationContext("Books.xml");
    Books book1 = context.getBean("books", Books.class);
    Books book2 = context.getBean("books", Books.class);

    System.out.println(book1);
    System.out.println(book2);
}
//输出结果相同，是单实例对象
```

#### 多实例

使用bean标签里的scope属性完成单多实例的设置

单实例：`scope="singleton"`   加载配置文件时就创建了对象

多实例：`scope="prototype"`   每调用一次`getBean()`，创建一个对象

还有`scope="request"` 、`scope="session"`、 `scope="application"`，这三个作用域仅适用于web的Spring WebApplicationContext环境

```xml
<bean id="books" class="com.atguigu.spring.Books" scope="prototype">
</bean>
```

```java
@Test
public void testBooks() {
    ApplicationContext context = new ClassPathXmlApplicationContext("Books.xml");
    Books book1 = context.getBean("books", Books.class);
    Books book2 = context.getBean("books", Books.class);

    System.out.println(book1);
    System.out.println(book2);
}
//输出结果不同，是多实例对象
```











# Bean生命周期（五步）

（1）通过构造器创建bean实例（无参构造）

（2）为bean的属性设置值和对其它bean引用（set方法）

（3）调用bean的初始化的方法（需要进行配置初始化的方法）

（4）bean可以使用了（执行getBean()方法，对象获取到了）

（5）当容器关闭时，手动调用bean的销毁方法（需要进行配置销毁的方法）

```java
public class Orders {
    private String oname;

    public Orders() {
        System.out.println("第一步 执行无参构造方法创建bean实例");
    }

    public void setOname(String oname) {
        this.oname = oname;
        System.out.println("第二步 调用set方法设置属性值");
    }

    public void initMethod(){
        System.out.println("第三步 执行初始化方法");
    }

    public void destoryMethod(){
        System.out.println("第五步 执行销毁方法");
    }
}
```

```xml
<bean id="orders" class="com.atguigu.spring.Orders" init-method="initMethod" destroy-method="destoryMethod">
    <property name="oname" value="电脑"></property>
</bean>
```

```java
@Test
public void testBeanLife(){
    ApplicationContext context = new ClassPathXmlApplicationContext("BeanLife.xml");
    //或者用 ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("BeanLife.xml");
    Orders orders = context.getBean("orders", Orders.class);
    System.out.println("第四步 获取创建bean实例对象");

    System.out.println(orders);

    ((ClassPathXmlApplicationContext) context).close();
}
```









# Bean生命周期（七步）

（1）通过构造器创建bean实例（无参构造）

（2）为bean的属性设置值和对其它bean引用（set方法）

**（3）把bean实例传递给bean后置处理器的方法（postProcessBeforeInitialization）**

（4）调用bean的初始化的方法（需要进行配置初始化的方法）

**（5）把bean实例传递给bean后置处理器的方法（postProcessAfterInitialization）**

（6）bean可以使用了（执行getBean()方法，对象获取到了）

（7）当容器关闭时，手动调用bean的销毁方法（需要进行配置销毁的方法）

```java
//创建一个类，实现接口BeanPostProcessor，这就是bean后置处理器
public class MyBeanPost implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("在初始化之前进行后置处理");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("在初始化之后进行后置处理");
        return bean;
    }
}
```

```xml
<!-- 配置后置处理器
	 对当前配置文件中的所有Bean实例有效
-->
<bean id="myBeanPost" class="com.atguigu.spring.MyBeanPost">
</bean>
```











# 什么是Bean管理？



***Bean管理指的是两个操作：Spring创建对象、Spring注入属性**











# Bean管理操作(基于xml)

## 	一、创建对象

在Spring配置文件中，使用bean标签并在里面添加对应属性，就可以实现对象创建。**创建对象时，默认执行无参构造方法**

```xml
<bean id="" class=""></bean>
```

### 	bean标签常用属性

id属性：对象唯一标识

class属性：类全路径（包路径+类名）

name属性：与id属性一样，但可以加特殊符号



## 	二、注入属性（手动装配）

***先创建对象再注入属性**



### 1、注入字符串类型属性

#### 第一种（set方法注入）

```java
public class Book {
    private  String bname;
    private  String bauthor;

    public void setBname(String bname) {
        this.bname = bname;
    }
    public void setBauthor(String bauthor) {
        this.bauthor = bauthor;
    }
    
}
```

```xml
<bean id="book" class="com.xxx.Book">
	<property name="bname" value="易筋经"></property>
    <property name="bauthor" value="达摩"></property>
</bean>
```

name：类里面的属性名称

value：向属性注入的值



#### 第二种（有参构造方法注入）

```java
public class Orders {
    private String oname;
    private String address;

    public Orders(String oname, String address) {
        this.oname = oname;
        this.address = address;
    }
}
```

```xml
<bean id="orders" class="com.xxx.Orders">
    <constructor-arg name="oname" value="computer"></constructor-arg>
    <!--给第1个参数赋值-->
    <constructor-arg index="0" value="computer"></constructor-arg>
    <constructor-arg name="address" value="beijing"></constructor-arg>
</bean>
```



#### 第三种（p名称空间注入，set方法的简化）

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:p="http://www.springframework.org/schema/p"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
        <bean id="book" class="com.xxx.Book" p:bname="九阳神功" p:bauthor="无名氏">
        </bean>
</beans>
```





### 2、注入其它类型属性

#### 2.1 注入null值

```xml
<!-- 注入null值 -->
<property name="book">
	<null/>
</property>
```



#### 2.2 注入特殊符号

```xml
<!-- 注入<<南京>> -->
<property name="book" value="&lt;&lt;南京&gt;&gt;"></property>
```

或者

```xml
<property name="book">
	<value><![CDATA[<<南京>>]]></value>
</property>
```



#### 2.3 注入外部Bean

**例子：有两个类service和dao，在service中调用dao里的方法**

```java
package dao;
public interface UserDao {
    public void update();
}

package dao;
public class UserDaoImpl implements UserDao {
    public void update(){
        System.out.println("dao update......");
    }
}

package service;
public interface UserService {
    public void add();
}

package service;
import dao.UserDao;
public class UserServiceImpl implements UserService {
    private UserDao userDao;

    public void setUserDao(UserDao userDao){
        this.userDao = userDao;
    }
    public void add(){
        System.out.println("service add......");
        userDao.update();
    }
}
```

```xml
<bean id="userServiceImpl" class="service.UserServiceImpl">
    <!-- 注入UserDao对象
         name属性：类里面的属性名称
         ref属性： 创建的UserDao对象的bean标签的id值
    -->
    <property name="userDao" ref="userDaoImpl"></property>
</bean>

<bean id="userDaoImpl" class="dao.UserDaoImpl"></bean>
```

```java
public class TestSpring5{
   @Test
   public void testBean(){
        ApplicationContext context = new ClassPathXmlApplicationContext("testbean.xml");
        UserService userService = context.getBean("userServiceImpl",UserService.class);

        userService.add();
    }
}
```



#### 2.4 注入内部Bean

```java
package study.first.spring;
public class Dept {
    private String dname;

    public void setDname(String dname) {
        this.dname = dname;
    }

    @Override
    public String toString() {
        return dname ;
    }
}

package study.first.spring;
public class Emp {
    private String ename;
    private String gender;
    private Dept dept;

    public void setEname(String ename) {
        this.ename = ename;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public void setDept(Dept dept) {
        this.dept = dept;
    }

    public void test(){
        System.out.println(this.ename+"::"+this.gender + "::"+dept);
    }
}
```

```xml
<bean id="emp" class="study.first.spring.Emp">
    <property name="ename" value="jack"></property>
    <property name="gender" value="man"></property>
    <property name="dept" >
        <bean id="dept" class="study.first.spring.Dept">
            <property name="dname" value="安保部"></property>
        </bean>
    </property>
</bean>
```

```java
public class TestSpring5{
    @Test
    public void testBean(){
        ApplicationContext context = new ClassPathXmlApplicationContext("testExtendBean.xml");
        Emp emp = context.getBean("emp", Emp.class);

        emp.test();
    }
}
```



#### 2.5 级联赋值

```xml
<bean id="boss" class="study.first.spring.Emp">
    <property name="ename" value="tom"></property>
    <property name="gender" value="man"></property>
    <property name="dept" ref="dept"></property>
</bean>

<bean id="dept" class="study.first.spring.Dept">
    <property name="dname" value="董事局"></property>
</bean>
```

或者

```xml
<bean id="emp" class="study.first.spring.Emp">
    <property name="ename" value="jack"></property>
    <property name="gender" value="man"></property>
    <property name="dept" ref="dept"></property>
    <property name="dept.dname" value="技术部"></property>
</bean>
<bean id="dept" class="study.first.spring.Dept"></bean>
```

这种方式的赋值必须要有get方法



#### 2.6 注入集合

##### 2.6.1 注入数组类型属性

```xml
<property name="courses">
    <array>
        <value>math</value>
        <value>chinese</value>
        <value>english</value>
    </array>
</property>
```



##### 2.6.2 注入List集合类型属性

```xml
<property name="list">
    <list>
        <value>100</value>
        <value>100</value>
        <value>100</value>
    </list>
</property>
```



##### 2.6.3 注入Map集合类型属性

```xml
<property name="map">
    <map>
        <entry key="java" value="JAVA"></entry>
        <entry key="python" value="PYTHON"></entry>
    </map>
</property>
```



##### 2.6.4 注入Set集合类型属性

```xml
<property name="set">
    <set>
        <value>MySQL</value>
        <value>Redis</value>
    </set>
</property>
```



##### 2.6.5 在集合里注入对象

```xml
<bean id="stu" class="com.atguigu.spring.Stu">
    <property name="coursesList">
        <list>
            <ref bean="math"></ref>
            <ref bean="chinese"></ref>
        </list>
    </property>
</bean>

<bean id="math" class="com.atguigu.spring.Courses">
    <property name="cname" value="math"></property>
</bean>

<bean id="chinese" class="com.atguigu.spring.Courses">
    <property name="cname" value="chinese"></property>
</bean>
```



##### 2.6.6 提取集合注入中的公共部分

第一步、先在配置文件中引入util名称空间

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:util="http://www.springframework.org/schema/util"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                           http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util.xsd">

</beans>
```

第二步、提取公共部分

```xml
<util:list id="booklists">
    <value>易筋经</value>
    <value>六脉神剑</value>
    <value>九阳神功</value>
</util:list>
```

第三步、赋值

```xml
<bean id="books" class="com.atguigu.spring.Books">
	<property name="booklist" ref="booklists"></property>
</bean>
```









## 三、注入属性（自动装配）

**将一个 Bean 注入其他 Bean 的 Property 中**

根据指定装配规则（属性名称或者属性类型），Spring自动将匹配的属性值进行注入。不需要用property手动注入



### 1、byName方式

```xml
<!--
    根据属性名字自动装配，注入值bean的id必须与类属性名字一样
-->
<bean id="emp" class="study.spring.autowire.Emp" autowire="byName">
</bean>

<bean id="dept" class="study.spring.autowire.Dept">
</bean>
```



### 2、byType方式

```xml
<!--
	根据属性类型自动装配，相同类型的bean只能出现一次，否则报错
-->
<bean id="emp" class="study.spring.autowire.Emp" autowire="byType">
</bean>

<bean id="dept" class="study.spring.autowire.Dept">
</bean>

<!--
	如果加上下面的代码，就报错。因为相同类型的bean出现了两次
-->
<bean id="dept1" class="study.spring.autowire.Dept">
</bean>
```









## 四、外部属性文件

前提：引入德鲁伊连接池jar包

```xml
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid</artifactId>
    <version>1.1.9</version>
</dependency>
```



### 1、直接配置数据库连接池

```xml
<bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
    <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"></property>
    <property name="url" value="jdbc:mysql://localhost:3306/userDb?serverTimezone=UTC"></property>
    <property name="username" value="root"></property>
    <property name="password" value="123"></property>
</bean>
```







### 2、引入外部属性文件配置数据库连接池

**第一	创建外部属性文件 jdbc.properties**

```properties
prop.driverClass=com.mysql.cj.jdbc.Driver
prop.url=jdbc:mysql://localhost:3306/userDb?serverTimezone=UTC
prop.username=root
prop.password=123
```

**第二	把外部properties属性文件引入到spring配置文件中**

​	**（1）引入context名称空间**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                          http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">

</beans>
```

​	**（2）使用标签引入外部属性文件**

```xml
<!-- 引入外部属性文件 -->
<context:property-placeholder location="jdbc.properties"></context:property-placeholder>

<!-- 配置连接池 -->
<bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
    <property name="driverClassName" value="${prop.driverClass}"></property>
    <property name="url" value="${prop.url}"></property>
    <property name="username" value="${prop.username}"></property>
    <property name="password" value="${prop.password}"></property>
</bean>
```













# Bean管理操作(基于注解)

什么是注解？

注解是代码的特殊标记。格式：@注解名称(属性名称=属性值，属性名称=属性值)

注解可以作用在类上面、方法上面、属性上面

使用注解的目的：简化xml配置



**准备工作：**

一、先引入依赖

```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-aop</artifactId>
    <version>5.3.9</version>
</dependency>
```

二、开启组件扫描

```xml
<!-- 先引入context名称空间 -->


<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                           http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">

    <!-- 开启组件扫描。只有开启了，spring才能找到注解
		如果扫描多个包，包之间使用逗号隔开
		或者扫描上层目录
	-->
    <context:component-scan base-package="com.maven.spring"></context:component-scan>
</beans>
```









## 一、创建对象

Spring针对Bean管理中创建Bean实例提供了四个注解：

@Component

@Service			一般应用于业务逻辑层和Service层

@Controller	   一般用于Web层

@Repository	  一般用于Dao持久层



### （1）@Component

```java
//(value = "userService)可以不写
//默认值是类名称 首字母小写
//UserService --- userService
@Component(value = "userService")		//等价于<bean id="userService" class="..."/>
public class UserService {
    public void add(){
        System.out.println("Service add ...");
    }
}
```





### （2）@Service

```java
@Service(value = "userService")
public class UserService {
    public void add(){
        System.out.println("Service add ...");
    }
}
```





### （3）@Controller

```java
@Controller(value = "userService")
public class UserService {
    public void add(){
        System.out.println("Service add ...");
    }
}
```





### （4）@Repository

```java
@Repository(value = "userService")
public class UserService {
    public void add(){
        System.out.println("Service add ...");
    }
}
```











## 二、组件扫描配置

```xml
<!--示例一
	use-default-filters="false" 表示不使用默认filter，自己配置filter
	context:include-filter	设置扫描哪些内容，示例是扫描Controller注解
-->
<context:component-scan base-package="com.xxx" use-default-filters="fasle">
    <context:include-filter type="annotation"
                            expression="org.springframework.stereotype.Controller"/>
</context:component-scan>
```



```xml
<!--示例二
	context:exclude-filter	设置哪些内容不扫描
-->
<context:component-scan base-package="com.xxx">
    <context:exclude-filter type="annotation"
                            expression="org.springframework.stereotype.Controller"/>
</context:component-scan>
```











## 三、注入属性

四个常用注解：

@Autowired	根据属性类型进行自动注入

@Qualifier	   根据属性名称进行自动注入，必须和@Autowired一起使用（因为一个接口有多个实现类，@Autowired不顶用）

@Resource	  既可以根据类型，也可以根据名称自动注入。javax中的包

@Value			 注入普通类型属性，如字符串



### （1）@Autowired

```java
public interface UserDao {
    public void add();
}
```

```java
@Repository
public class UserDaoImpl implements UserDao {
    @Override
    public void add() {
        System.out.println("Dao add...");
    }
}
```

```java
@Service
public class UserService {
    //不需要添加set方法
    @Autowired
    private UserDao userDao;

    public void add(){
        System.out.println("Service add ...");
        userDao.add();
    }
}
```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                           http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">

    <context:component-scan base-package="com.maven.spring"></context:component-scan>
</beans>
```

```java
@Test
public void testSpring() {
    ApplicationContext context = new ClassPathXmlApplicationContext("bean1.xml");
    UserService userService = context.getBean("userService", UserService.class);
    System.out.println(userService);
    userService.add();
}
```





### （2）@Qualifier

```java
@Service
public class UserService {
    //不需要添加set方法
    @Autowired
    @Qualifier(value = "userDaoImpl")
    private UserDao userDao;

    public void add(){
        System.out.println("Service add ...");
        userDao.add();
    }
}
```









### （3）@Resource

#### 根据类型注入

```java
@Service
public class UserService {
    //不需要添加set方法
    @Resource   //根据类型注入
    UserDao userDao;

    public void add(){
        System.out.println("Service add ...");
        userDao.add();
    }
}
```



#### 根据名称注入

```java
@Service
public class UserService {
    //不需要添加set方法
    @Resource(name = "userDaoImpl")   //根据名称注入，使用userDao报错
    UserDao userDao;

    public void add(){
        System.out.println("Service add ...");
        userDao.add();
    }
}
```





### （4）@Value

```java
@Value(value = "abc")
private String name;
```









## 四、完全注解开发

不再需要 xml 配置文件



（1）创建配置类，代替xml配置文件

```java
@Configuration  //配置类，代替xml配置文件
@ComponentScan(basePackages = "com.maven.spring")
public class SpringConfig {
    
}
```



（2）编写测试类

```java
public class TrySpringTest {
    @Test
    public void testSpring() {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        UserService userService = context.getBean("userService", UserService.class);
        System.out.println(userService);
        userService.add();
    }
}
```

