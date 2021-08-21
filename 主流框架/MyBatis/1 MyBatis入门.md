# 概述

```
持久层技术解决方案：

​		JDBC技术：Connection、PreparedStatement、ResultSet

​		Spring的JdbcTemplate：对jdbc的简单封装

​		Apache的DBUtils：和JdbcTemplate很像，也是对jdbc的简单封装


以上都不是框架
		JDBC是规范
		Spring的JdbcTemplate和Apache的DBUtils都只是工具类
```



```
mybatis是一个基于java的持久层框架，内部封装了jdbc，使开发者只需要关注sql语句本身。
它使用了ORM思想实现了结果集的封装

ORM：Object Relational Mapping 对象关系映射
	把数据库表和实体类及实体类的属性对应起来，让我们可以操作实体类就实现操作数据库表

```





# 环境搭建

```
第一步：创建maven工程(quickstart)并导入坐标
第二步：创建实体类
第三步：创建dao接口
第四步：创建Mybatis的主配置文件
第五步：创建映射配置文件
```



（1）pom.xml引入依赖

```xml
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.11</version>
      <scope>test</scope>
    </dependency>
    <dependency>
      <groupId>org.mybatis</groupId>
      <artifactId>mybatis</artifactId>
      <version>3.4.5</version>
    </dependency>
    <dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
      <version>5.1.6</version>
    </dependency>
    <dependency>
      <groupId>log4j</groupId>
      <artifactId>log4j</artifactId>
      <version>1.2.12</version>
    </dependency>
```





（2）在java.com.mybatis包中创建文件夹domain，创建User实体类

```java
public class User implements Serializable {
    private Integer id;
    private String username;
    private Date birthday;
    private String sex;
    private String address;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public Date getBirthday() {
        return birthday;
    }
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", birthday=" + birthday +
                ", sex='" + sex + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
```



（3）在java.com.mybatis包中创建文件夹dao，创建接口IUserDao

```java
/**
 * 用户的持久层接口
 */
public interface IUserDao {

    /**
     * 查询所有数据
     * @return
     */
    List<User> findAll();
}
```



（4）在resources文件中创建SqlMapConfig.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">


<!-- mybatis的主配置文件 -->
<configuration>
    <!-- 配置环境 -->
    <environments default="mysql">
        <!-- 配置mysql的环境-->
        <environment id="mysql">
            <!-- 配置事务的类型-->
            <transactionManager type="JDBC"></transactionManager>
            <!-- 配置数据源（连接池） -->
            <dataSource type="POOLED">
                <!-- 配置连接数据库的4个基本信息 -->
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/user_db?serverTimezone=UTC"/>
                <property name="username" value="root"/>
                <property name="password" value="123"/>
            </dataSource>
        </environment>
    </environments>

    <!-- 指定映射配置文件的位置，映射配置文件指的是每个dao独立的配置文件 -->
    <mappers>
        <mapper resource="com/mybatis/dao/IUserDao.xml"/>
    </mappers>
</configuration>
```



（5）在resources中创建com/mybatis/dao，并在目录里创建IUserDao.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">


<!-- 映射配置文件 -->
<mapper namespace="com.mybatis.dao.IUserDao">
    <!--配置查询所有数据, resultType属性是将结果集封装为User-->
    <select id="findAll" resultType="com.mybatis.domain.User">
        select * from user
    </select>
</mapper>
```



### 注意事项

​		一、在Mybatis中，持久层的操作接口名称和映射文件叫做Mapper。

​				IUserDao 和 IUserMapper 是一样的。

​		二、在idea中创建目录的时候，它和包是不一样的

​				包在创建时：com.mybatis.dao是三级目录

​				目录在创建时：com.mybatis.dao是一级目录

​		三、mybatis的映射配置文件位置必须和dao接口的包结构相同

​		四、映射配置文件的mapper标签namespace属性的取值必须是dao接口的全限定类名(包名+类名)

​		五、映射配置文件的操作配置(select、update...)，id属性的取值必须是dao接口的方法名

```
当遵从了第三、四、五点之后，在开发中无须再写dao的实现类，剩下的工作由mybatis完成
```









# 入门案例

```
第一步：读取配置文件
第二步：创建SqlSessionFactory工厂
第三步：创建SqlSession
第四步：创建Dao接口的代理对象
第五步：执行dao中的方法
第六步：释放资源
```



（1）在resources中创建log4j.properties

```properties
# Set root category priority to INFO and its only appender to CONSOLE.
#log4j.rootCategory=INFO, CONSOLE            debug   info   warn error fatal
log4j.rootCategory=debug, CONSOLE, LOGFILE

# Set the enterprise logger category to FATAL and its only appender to CONSOLE.
log4j.logger.org.apache.axis.enterprise=FATAL, CONSOLE

# CONSOLE is set to be a ConsoleAppender using a PatternLayout.
log4j.appender.CONSOLE=org.apache.log4j.ConsoleAppender
log4j.appender.CONSOLE.layout=org.apache.log4j.PatternLayout
log4j.appender.CONSOLE.layout.ConversionPattern=%d{ISO8601} %-6r [%15.15t] %-5p %30.30c %x - %m\n

# LOGFILE is set to be a File appender using a PatternLayout.
log4j.appender.LOGFILE=org.apache.log4j.FileAppender
log4j.appender.LOGFILE.File=d:\axis.log
log4j.appender.LOGFILE.Append=true
log4j.appender.LOGFILE.layout=org.apache.log4j.PatternLayout
log4j.appender.LOGFILE.layout.ConversionPattern=%d{ISO8601} %-6r [%15.15t] %-5p %30.30c %x - %m\n
```



（2）编写测试代码

```java
/**
 * 入门案例
 */
public class MybatisTest {

    @Test
    public void Test1() throws Exception {
        //1.读取配置文件
        InputStream in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2.创建SqlSessionFactory工厂
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(in);
        //3.使用工厂生成SqlSession对象
        SqlSession session = factory.openSession();
        //4.使用SqlSession创建Dao接口的代理对象
        IUserDao userDao = session.getMapper(IUserDao.class);
        //5.使用代理对象执行方法
        List<User> users = userDao.findAll();
        for(User user : users){
            System.out.println(user);
        }
        //6.释放资源
        session.close();
        in.close();
    }
}
```



### 注意事项

​		不要忘记在映射配置中告知mybatis要封装到哪个实体类中

​		配置方式：指定实体类的全限定类名(包名+类名)







### 注解开发

新建maven-quickstart工程

```
将入门案例代码中的拷贝过来，移除IUserDao.xml，在dao接口的方法上使用@Select注解，并且指定SQL语句，同时需要在SqlMapConfig.xml中的mapper配置时，使用class属性指定dao接口的全限定类名。
```

（1）修改SqlMapConfig.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">


<!-- mybatis的主配置文件 -->
<configuration>
    <!-- 配置环境 -->
    <environments default="mysql">
        <!-- 配置mysql的环境-->
        <environment id="mysql">
            <!-- 配置事务的类型-->
            <transactionManager type="JDBC"></transactionManager>
            <!-- 配置数据源（连接池） -->
            <dataSource type="POOLED">
                <!-- 配置连接数据库的4个基本信息 -->
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/user_db?serverTimezone=UTC"/>
                <property name="username" value="root"/>
                <property name="password" value="123"/>
            </dataSource>
        </environment>
    </environments>

    <!-- 指定映射配置文件的位置，因为是注解开发，所以用class属性指定被注解的dao全限定类名 -->
    <mappers>
        <mapper class="com.mybatis.dao.IUserDao"/>
    </mappers>
</configuration>
```

（2）修改main/java/com/mybatis/dao中的IUserDao接口

```java
/**
 * 用户的持久层接口
 */
public interface IUserDao {

    /**
     * 查询所有数据
     * @return
     */
    @Select("select * from user")
    List<User> findAll();
}
```







### 明确

​			我们在实际开发中，越简便越好，所以都是采用不写dao实现类的方式，不管使用XML还是注解。

​			但是Mybatis是支持写dao实现类的。

（1）在dao下创建文件夹impl并编写userDaoImpl类

```java
public class UserDaoImpl implements IUserDao {

    private SqlSessionFactory factory;

    public UserDaoImpl(SqlSessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public List<User> findAll() {
        //1.使用工厂创建SqlSession对象
        SqlSession session = factory.openSession();
        //2.使用session执行查询数据方法，里面的字符串是IUserDao.xml里的namespace.id
        List<User> users = session.selectList("com.mybatis.dao.IUserDao.findAll");
        session.close();
        //3.返回查询结果
        return users;
    }
}
```

（2）编写测试代码

```java
public class MybatisTest {

    @Test
    public void Test1() throws Exception {
        //1.读取配置文件
        InputStream in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2.创建SqlSessionFactory工厂
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(in);
        //3.使用工厂创建dao对象
        IUserDao userDao = new UserDaoImpl(factory);
        //4.使用代理对象执行方法
        List<User> users = userDao.findAll();
        for(User user : users){
            System.out.println(user);
        }
        //5.释放资源
        in.close();
    }
}
```







### 设计模式分析

![image-20210819094136840](C:\Users\94125\AppData\Roaming\Typora\typora-user-images\image-20210819094136840.png)









# 自定义MyBatis框架



### 分析

```
https://www.bilibili.com/video/BV1Db411s7F5?p=11&spm_id_from=pageDriver
```



### 设计

在mybatis包上创建一个包design

（1）创建包io，实现一个类Resources

```java
/**
 * 使用类加载器读取配置文件的类
 */
public class Resources {
    /**
     * 根据传入的参数，获取一个字节输入流
     * @param filePath
     * @return Resources.class.getClassLoader().getResourceAsStream(filePath);
     */
    public static InputStream getResourceAsStream(String filePath){
        return Resources.class.getClassLoader().getResourceAsStream(filePath);
    }
}
```



（2）创建一个包sqlsession，实现接口SqlSessionFactory和类SqlSessionFactoryBuilder

```
https://www.bilibili.com/video/BV1Db411s7F5?p=13&spm_id_from=pageDriver
```







































