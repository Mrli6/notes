```xml
<!-- 过滤静态资源 -->
<mvc:default-servlet-handler />
```

在**配有拦截器**的ssm框架中即使使用了以上代码，但是静态资源还是被拦截。需要在拦截器配置中增加以下代码

```xml
<mvc:exclude-mapping path="/css/**"/>
<mvc:exclude-mapping path="/images/**"/>
<mvc:exclude-mapping path="/js/**"/>
```

