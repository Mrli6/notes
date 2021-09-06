# 1 特点：

- 和Servlet一样，用于动态Web技术
- JSP页面中可以嵌入Java代码，为用户提供动态数据





# 2 原理

- 浏览器向服务器发送请求，不管访问什么资源，其实都是在访问Servlet
-  JSP最终会被转换成为一个Java类
- tomcat中有work目录，jsp的java、class文件就在里面；当执行某一页面时，才在work目录中生成java、class文件

![image-20210825134704771](D:\面向工作学习\JavaWeb\2 JSP.assets\image-20210825134704771.png)





# 3 基础语法

```xml
<!-- Servlet依赖 -->
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>servlet-api</artifactId>
    <version>2.5</version>
    <scope>provided</scope>
</dependency>
<!-- JSP依赖 -->
<dependency>
    <groupId>javax.servlet.jsp</groupId>
    <artifactId>jsp-api</artifactId>
    <version>2.0</version>
    <scope>provided</scope>
</dependency>
<!-- JSTL表达式的依赖 -->
<dependency>
    <groupId>javax.servlet.jsp.jstl</groupId>
    <artifactId>jstl-api</artifactId>
    <version>1.2</version>
</dependency>
<!-- standard标签库 -->
<dependency>
    <groupId>taglibs</groupId>
    <artifactId>standard</artifactId>
    <version>1.1.2</version>
</dependency>
```



## 3.1 JSP表达式

```jsp
<%-- JSP表达式
    语法：<%= java变量或者表达式%>
	作用：将程序输出到客户端
--%>
<%= new java.util.Date()%>
```





## 3.2 JSP脚本片段

```jsp
<%-- JSP脚本片段
    语法：<% java代码 %>
--%>
<%
    int sum = 0;
    for (int i = 1; i <= 100; i++) {
        sum += i;
    }
    out.print("<h1> sum="+sum+"</h1>");
%>
```





## 3.3 JSP声明

```jsp
<%-- JSP声明
    语法：<%! %>
--%>
<%!
    static {
    System.out.println("loading");
}
%>
```

声明会被编译到java类中，其它的编译到_jspService方法里





## 3.4 JSP指令

```jsp
<%-- JSP指令
    语法：<%@ %>
	作用：导jar包...
--%>
<%@ page errorPage="/500.jsp"%>
```





# 4 九大内置对象

- PageContext 存东西
- Request         存东西
- Response
- Session          存东西
- Application 【ServletContext】 存东西
- config          【ServletConfig】
- out
- page
- exception

```jsp
<%
    pageContext.setAttribute("name1","你好");    //在一个页面中生效
    request.setAttribute("name2", "我好");       //在一次请求中有效，请求转发也有效
    session.setAttribute("name3", " 大家好");    //在一次会话中有效，浏览器的开关期间
    application.setAttribute("name4", "一起好"); //在服务器中有效，服务器的开关期间
%>
```

request：用户看完就没用了，比如：新闻，用户退出再进需要重新加载

session：用户用完一会还有用，比如：购物车

application：一个用户用完了，其他用户还可能使用，比如：聊天数据





# 5 JSP标签、JSTL标签、EL表达式



## 5.1 EL表达式

语法：${...}

- 获取数据
- 执行运算
- 获取web开发的常用对象

```jsp
<% 
	request.setAttribute("name","孤傲苍狼");
%>
<%--${name}等同于pageContext.findAttribute("name") --%>
	使用EL表达式获取数据：${name}  
```



## 5.2 JSP标签

```jsp
<%--
    http://localhost:8080/javaweb_day02_jsp_war_exploded/get.jsp?name=value1&password=value2
--%>
<jsp:forward page="/get.jsp">
    <jsp:param name="name" value="value1"></jsp:param>
    <jsp:param name="password" value="value2"></jsp:param>
</jsp:forward>
```

使用<%=>来取参数

```jsp
名字：<%=request.getParameter("name")%>
密码：<%=request.getParameter("password")%>
```





## 5.3 JSTL标签

```jsp
<%-- 引入jstl核心标签库 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
```

使用param.name来取参数

```jsp
<form action="coreif.jsp" method="get">
    <input type="text" name="name" value="${param.name}"/>
    <input type="submit" value="登录"/>
</form>
```

