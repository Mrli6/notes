# 1 执行流程

![image-20210826135114390](D:\面向工作学习\JavaWeb\5 Filter.assets\image-20210826135114390.png)





# 2 开发步骤



##  2.1 导包





## 2.2 编写类实现Filter接口

```java
public class Filter implements javax.servlet.Filter {
    // 初始化，在web服务器启动时就执行
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("初始化");
    }

    // 这里面的所有代码在特定请求下都会执行
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("utf-8");
        servletResponse.setCharacterEncoding("utf-8");
        servletResponse.setContentType("text/html;charset=utf-8");

        System.out.println("执行前");
        filterChain.doFilter(servletRequest, servletResponse);//如果不写这段代码，程序会停在这里
        System.out.println("执行后");
    }

    //销毁，在web服务器关闭时就执行
    @Override
    public void destroy() {
        System.out.println("销毁");
    }
}
```





## 2.3 web.xml配置servlet

```xml
<filter>
    <filter-name>filter</filter-name>
    <filter-class>com.kuang.filter.Filter</filter-class>
</filter>
<filter-mapping>
    <filter-name>filter</filter-name>
    <url-pattern>/filter/*</url-pattern>
</filter-mapping>
```







# 应用

用户登录成功后，进入成功界面。复制这个成功界面的url，返回登录界面，输入url，能进入成功界面。这时就需要过滤器来解决这个问题





