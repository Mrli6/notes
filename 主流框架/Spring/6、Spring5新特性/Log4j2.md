# Spring实现日志

（1）引入jar包

```xml
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-api</artifactId>
    <version>2.12.1</version>
</dependency>

<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-core</artifactId>
    <version>2.12.1</version>
</dependency>

<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-slf4j-impl</artifactId>
    <version>2.12.1</version>
    <scope>test</scope>
</dependency>

<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>1.7.30</version>
</dependency>
```



（2）创建log4j2.xml文件

```
注意： 文件名字固定为 log4j2
```

```xml
<?xml version="1.0" encoding="UTF-8"?>

<!--
    日志级别优先级：OFF < FATAL < ERROR < WARN < INFO < DEBUG < TRACE < ALL
    Configuration后面的status用于设置log4j2自身内部的信息输出，可以不设置。
    当设置为TRACE时，可以看到log4j2内部各种详细输出
-->
<configuration status="INFO">
    <!--先定义所有的appender-->
    <appenders>
        <!--输出日志信息到控制台-->
        <console name="Console" target="SYSTEM_OUT">
            <!--控制日志输出的格式-->
            <PatternLayout pattern="%d{yyyy-MM-dd HH:mm:ss.SSS} [%t] %-5level %logger{36} -%msg%n"/>
        </console>
    </appenders>

    <!--
        定义logger，只有定义了logger并引入appender，appender才会生效
        root：用于指定项目的根日志，如果没有单独指定logger，则会使用root作为默认的日志输出
    -->
    <loggers>
        <root level="info">
            <appender-ref ref="Console"/>
        </root>
    </loggers>
</configuration>
```







# 手动实现日志

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserLog {

    private static final Logger log = LoggerFactory.getLogger(UserLog.class);

    public static void main(String[] args) {
        log.info("hello");
        log.warn("hello");
    }
}
```







