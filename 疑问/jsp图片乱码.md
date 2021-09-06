# 问题一：

在webapp下新建一个error目录，里面放了两种图片：404.png、500.png

在web.xml中进行一下配置：

```xml
<error-page>
    <error-code>404</error-code>
    <location>/error/404.png</location>
</error-page>

<error-page>
    <error-code>500</error-code>
    <location>/error/500.png</location>
</error-page>
```



在webapp下新建一个hello.jsp文件

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%
    int i = 1/0;
%>

</body>
</html>
```



在访问不存在的网页时，404图片正常导出。但在访问hello.jsp页面时，网页上的500图片乱码







# 问题二：

保持问题一中的代码不变，在hello.jsp文件中仅仅添加了以下代码

```jsp
<%@ page errorPage="/500.jsp"%>
```



在webapp中创建一个500.jsp的文件

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<img src="${pageContext.request.contextPath}/error/500.png" alt="500">
</body>
</html>
```



为什么这次500图片正常显示而没有乱码？