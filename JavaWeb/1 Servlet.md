# 1 入门案例



引入jar包

```xml
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>servlet-api</artifactId>
    <version>2.5</version>
    <scope>provided</scope>
</dependency>
<dependency>
    <groupId>javax.servlet.jsp</groupId>
    <artifactId>jsp-api</artifactId>
    <version>2.0</version>
    <scope>provided</scope>
</dependency>
```



编写一个java类继承HttpServlet

```java
public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 响应的类型：text或者html
        resp.setContentType("text/html");
        // 设置编码格式，防止页面上中文乱码
        resp.setCharacterEncoding("utf-8");
        // 获取响应的输出流
        PrintWriter out = resp.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Hello World!</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>你好!</h1>");
        out.println("</body>");
        out.println("</html>");
    }
}
```



在web.xml中配置servlet

```xml
<!-- 以下代码执行流程：获取到/kuangshen的路径，根据serlet-name中的配置找servlet，
	找到这个servlet后执行servlet-class里的内容
-->
<!-- 注册servlet -->
<servlet>
    <servlet-name>helloServlet</servlet-name>
    <servlet-class>kuangshen.HelloServlet</servlet-class>
</servlet>
<!-- 一个servlet对应一个或多个mapper映射 -->
<servlet-mapping>
    <servlet-name>helloServlet</servlet-name>
    <!-- 请求路径 -->
    <url-pattern>/kuangshen</url-pattern>
</servlet-mapping>
```





# 2 Mapper问题

默认请求路径（优先级最低）

```xml
<servlet>
    <servlet-name>helloServlet</servlet-name>
    <servlet-class>kuangshen.HelloServlet</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>helloServlet</servlet-name>
    <url-pattern>/*</url-pattern>
</servlet-mapping>
```

一个servlet可以指定一个mapper映射路径

```xml
<servlet>
    <servlet-name>helloServlet</servlet-name>
    <servlet-class>kuangshen.HelloServlet</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>helloServlet</servlet-name>
    <url-pattern>/kuangshen</url-pattern>
</servlet-mapping>
```

一个servlet可以指定多个mapper映射路径

```xml
<servlet>
    <servlet-name>helloServlet</servlet-name>
    <servlet-class>kuangshen.HelloServlet</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>helloServlet</servlet-name>
    <url-pattern>/kuangshen</url-pattern>
</servlet-mapping>
<servlet-mapping>
    <servlet-name>helloServlet</servlet-name>
    <url-pattern>/hello</url-pattern>
</servlet-mapping>
```

一个servlet可以指定一个通用映射路径

```xml
<servlet>
    <servlet-name>helloServlet</servlet-name>
    <servlet-class>kuangshen.HelloServlet</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>helloServlet</servlet-name>
    <url-pattern>/kuangshen/*</url-pattern>
</servlet-mapping>
```

指定前缀映射路径

```xml
<servlet>
    <servlet-name>helloServlet</servlet-name>
    <servlet-class>kuangshen.HelloServlet</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>helloServlet</servlet-name>
    <url-pattern>*.kuangshen</url-pattern>
</servlet-mapping>
```





# 3 ServletContext



web容器在启动的时候，它会为每个web程序都创建一个对应的ServletContext对象，它代表了当前的web应用



## 3.1 共享数据

```java
public class SetContext extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext = this.getServletContext();

        servletContext.setAttribute("username", "你好");//键：username    值：你好

    }
}
```

```java
public class GetContext extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext = this.getServletContext();

        String value = (String)servletContext.getAttribute("username");
        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");
        resp.getWriter().print(value);

    }
}
```

```xml
<servlet>
    <servlet-name>setContext</servlet-name>
    <servlet-class>com.kuangshen.servlet.SetContext</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>setContext</servlet-name>
    <url-pattern>/set</url-pattern>
</servlet-mapping>

<servlet>
    <servlet-name>getContext</servlet-name>
    <servlet-class>com.kuangshen.servlet.GetContext</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>getContext</servlet-name>
    <url-pattern>/get</url-pattern>
</servlet-mapping>
```





## 3.2 获取初始化参数

```java
public class GetInitParam extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext = this.getServletContext();

        String url = servletContext.getInitParameter("url");
        resp.getWriter().print(url);

    }
}
```

```xml
<!-- 配置web应用初始化参数 -->
<context-param>
    <param-name>url</param-name>
    <param-value>jdbc:mysql://localhost:3306/table</param-value>
</context-param>

<servlet>
    <servlet-name>getInitParam</servlet-name>
    <servlet-class>com.kuangshen.servlet.GetInitParam</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>getInitParam</servlet-name>
    <url-pattern>/gip</url-pattern>
</servlet-mapping>
```





## 3.3 转发请求

路径不变，但获得的是gip中的内容

```java
public class RequestDispatcher extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext = this.getServletContext();

        servletContext.getRequestDispatcher("/gip").forward(req, resp);

    }
}
```

```xml
<servlet>
    <servlet-name>RD</servlet-name>
    <servlet-class>com.kuangshen.servlet.RequestDispatcher</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>RD</servlet-name>
    <url-pattern>/rd</url-pattern>
</servlet-mapping>
```





## 3.4 读取资源文件

```properties
username=hello
password=world
```

```java
public class GetProperties extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext = this.getServletContext();

        InputStream in = servletContext.getResourceAsStream("/WEB-INF/classes/db.properties");

        Properties properties = new Properties();
        properties.load(in);
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        resp.getWriter().print(username+":"+password);

    }
}
```

```xml
<servlet>
    <servlet-name>getProperties</servlet-name>
    <servlet-class>com.kuangshen.servlet.GetProperties</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>getProperties</servlet-name>
    <url-pattern>/gp</url-pattern>
</servlet-mapping>
```







# 4 HttpServletResponse



## 4.1 向浏览器输出消息

见第3点ServletContext中的代码





## 4.2 下载文件

```java
public class FileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1. 获取要下载的文件的路径
        String path = "D:\\mavenproject\\javaweb_day01_servlet\\response-01\\target\\response-01\\WEB-INF\\classes\\杨辉三角.md";
        //2. 下载文件的文件名
        String fileName = path.substring(path.lastIndexOf("\\")+1);
        //3. 设置让浏览器支持下载,中文文件名URLEncoder.encode编码，否则乱码
        resp.setHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(fileName,"UTF-8"));
        //4. 获取下载文件的输入流
        FileInputStream in = new FileInputStream(path);
        //5. 创建缓冲区
        int len = 0;
        byte[] buffer = new byte[1024];
        //6. 获取OutputStream对象
        ServletOutputStream out = resp.getOutputStream();
        //7. 将FileOutputStream流写入到buffer缓冲区, 使用OutputStream将缓冲区中的数据输出到客户端
        while((len = in.read(buffer)) > 0){
            out.write(buffer, 0, len);
        }
        //8. 关闭流
        in.close();
        out.close();

    }
}
```

```xml
<servlet>
    <servlet-name>down</servlet-name>
    <servlet-class>com.kuangshen.response.FileServlet</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>down</servlet-name>
    <url-pattern>/down</url-pattern>
</servlet-mapping>
```





## 4.3 验证码功能

```java
public class IdentifyingCode extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 让浏览器每3秒刷新一次
        resp.setHeader("refresh", "3");

        // 在内存中创建一个宽80，高20的三原色图片
        BufferedImage image = new BufferedImage(80, 20, BufferedImage.TYPE_INT_RGB);

        // 得到图片
        Graphics2D pencil = (Graphics2D) image.getGraphics();
        // 设置图片背景颜色并填充
        pencil.setColor(Color.white);
        pencil.fillRect(0,0,80,20);
        // 给图片写数据
        pencil.setColor(Color.BLUE);
        pencil.setFont(new Font(null,Font.BOLD,20));
        pencil.drawString(makeNum(), 0, 20);

        // 告诉浏览器，这个请求用图片的方式打开
        resp.setContentType("image/jpeg");
        // 网站存在缓存，关闭浏览器缓存
        resp.setDateHeader("expires", -1);
        resp.setHeader("Cache-Control", "no-cache");
        resp.setHeader("Pargma", "no-cache");

        // 把图片写给浏览器
        ImageIO.write(image, "jpg", resp.getOutputStream());
    }

    private String makeNum(){
        Random random = new Random();
        String num = random.nextInt(9999999)+"";

        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < 7-num.length(); i++){
            sb.append(0);
        }

        return num+sb.toString();
    }
}
```

```xml
<servlet>
    <servlet-name>identifyingCode</servlet-name>
    <servlet-class>com.kuangshen.response.IdentifyingCode</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>identifyingCode</servlet-name>
    <url-pattern>/ic</url-pattern>
</servlet-mapping>
```





## 4.4 重定向(最重要)

```java
public class Redirect extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("/response01_war/ic");
        /*	重定向的底层实现
        resp.setHeader("Location", "/response01_war/ic");
        resp.setStatus(302);
        */
    }
}
```

```xml
<servlet>
    <servlet-name>redirect</servlet-name>
    <servlet-class>com.kuangshen.response.Redirect</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>redirect</servlet-name>
    <url-pattern>/rd</url-pattern>
</servlet-mapping>
```



重定向时，url发生变化，302

请求转发，url不发生变化，307



### 注意事项：

**url中加斜杠”/”和不加斜杠的区别：**

**设webapp为web程序包名**

**通常来说，不加斜杠的形式（如”example.jsp”)请求的是相对于当前页面路径的资源 http://localhost:8080/webapp/examole；**

**加斜杠的形式（”/example.jsp”)请求的是服务器根目录下的资源，完整的url是由服务器地址+/example构成的:http://localhost:8080/example。如果页面不是放在服务器跟目录而是web程序包下，则不能使用加斜杠的形式。**



**如果在重定向时，在req或者resp中存了数据，那么这些数据会丢失**







```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<h2>Hello World!</h2>
    
<%-- ${pageContext.request.contextPath}代表当前项目路径 --%>
<form action="${pageContext.request.contextPath}/requestTest" method="get">
    用户名：<input type="text" name="username"><br/>
    密码：<input type="text" name="password"><br/>
    <input type="submit">
</form>
</body>
</html>
```

```java
public class RequestTest extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        ServletContext servletContext = this.getServletContext();
        servletContext.setAttribute("username", username);
        servletContext.setAttribute("password", password);

        resp.sendRedirect("/response01_war/success.jsp");

    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
```

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

    <h1>Success</h1><br/>

    ${pageContext.findAttribute("username")}
    ${pageContext.findAttribute("password")}

</body>
</html>
```

```xml
<servlet>
    <servlet-name>requestTest</servlet-name>
    <servlet-class>com.kuangshen.response.RequestTest</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>requestTest</servlet-name>
    <url-pattern>/requestTest</url-pattern>
</servlet-mapping>
```





# 5 HttpServletRequest

HttpServletRequest代表客户端的请求，用户通过http协议访问服务器，http请求中的所有信息会被封装到HttpServletRequest，通过这个HttpServletRequest的方法，可以获得客户端的所有信息。



**获取参数，请求转发**

```java
public class Request extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String[] hobbies = req.getParameterValues("hobbies");

        System.out.println(username);
        System.out.println(password);
        System.out.println(Arrays.toString(hobbies));

        req.setAttribute("username", username);
        req.setAttribute("password", password);
        req.setAttribute("hobbies", hobbies);

        req.getRequestDispatcher("/success.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
```

```jsp
<form action="${pageContext.request.contextPath}/login" method="post">
    用户名：<input type="text" name="username"><br/>
    密码：<input type="password" name="password"><br/>
    爱好：
    <input type="checkbox" name="hobbies" value="篮球">篮球
    <input type="checkbox" name="hobbies" value="足球">足球
    <input type="checkbox" name="hobbies" value="乒乓球">乒乓球
    <br/>
    <input type="submit" value="submit">
</form>
```

```jsp
<h1>执行成功</h1>

${pageContext.findAttribute("username")}
${pageContext.findAttribute("password")}
${pageContext.findAttribute("hobbies")}
```

```xml
<servlet>
    <servlet-name>request</servlet-name>
    <servlet-class>com.kuangshen.servlet.Request</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>request</servlet-name>
    <url-pattern>/login</url-pattern>
</servlet-mapping>
```



### 注意事项：

**getRequestDispatcher分成两种，可以用request调用，也可以用getServletContext()调用**

**不同的是：**

**request.getRequestDispatcher(url)的url可以是相对路径也可以是绝对路径。**

**而this.getServletContext().getRequestDispatcher(url)的url只能是绝对路径。**





**sendRedirect和getRequestDispatcher方法中的url的不同：**

**response.sendRedirect(url)：url=”example”, 地址相对于当前请求的目录; url=”/example”,请求的地址为服务器根目录下的example,如“http://localhost:8080/example” . 所以，sendRedirect方法中的url通常不在开头加”/”. sendRedirect的url区分方式和通常情况是一致的。**

**request.getRequestDispatcher(url)则与sendRedirect不同：url=”example”指向相对于当前请求地址的资源，加”/”开头指向web程序根目录下的资源/webapp/example。**







# 6 Cookie



cookie是客服端保存会话的技术



## 6.1 常见场景

网站登录之后，第二次访问自动进入账号

```java
public class Cookie1 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();

        // 服务端从请求中获取cookie
        Cookie[] cookies = req.getCookies();

        if(cookies != null){
            out.print("上次登录时间：");
            for (Cookie cookie : cookies) {
                if("key".equals(cookie.getName())){
                    long time = Long.parseLong(cookie.getValue());
                    Date date = new Date(time);
                    out.print(date.toLocaleString());
                }
            }
        }else{
            out.print("这是第一次登录");
        }

        Cookie cookie = new Cookie("key", System.currentTimeMillis()+"");

        // 设置cookie的有效期
        cookie.setMaxAge(24*60*60);

        // 在响应中添加cookie
        resp.addCookie(cookie);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
```

```xml
<servlet>
    <servlet-name>cookie</servlet-name>
    <servlet-class>com.kuangshen.Cookie1</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>cookie</servlet-name>
    <url-pattern>/c1</url-pattern>
</servlet-mapping>
```





## 6.2 细节

- 一个Cookie只能保存一个信息
- 一个web站点可以给浏览器发送多个Cookie，最多可存放20个cookie
- Cookie大小有4kb的限制
- 浏览器最多存放300个cookie
- cookie的key值不能为中文，否则报500





## 6.3 删除cookie

- 不设置有效期，关闭浏览器，自动失效
- 设置有效期时间为0

```java
public class Cookie2 extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 名字必须要删除的cookie名字一致
        Cookie cookie = new Cookie("key", System.currentTimeMillis()+"");
        cookie.setMaxAge(0);
        resp.addCookie(cookie);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
```

```xml
<servlet>
    <servlet-name>cookie2</servlet-name>
    <servlet-class>com.kuangshen.Cookie2</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>cookie2</servlet-name>
    <url-pattern>/c2</url-pattern>
</servlet-mapping>
```





## 6.4 传递中文数据

- 编码	`URLEncoder.encode("你好", "utf-8")`
- 解码    `URLDecoder.decode(cookie.getValue(), "utf-8")`

```java
public class Cookie3 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();

        Cookie[] cookies = req.getCookies();

        if(cookies != null){
            for (Cookie cookie : cookies) {
                if("name".equals(cookie.getName())){
                    // 解码
                    out.print(URLDecoder.decode(cookie.getValue(), "utf-8"));
                }
            }
        }else{
            System.out.println("第一次登录");
        }

        // 编码
        Cookie cookie = new Cookie("name", URLEncoder.encode("你好", "utf-8"));
        resp.addCookie(cookie);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
```

```xml
<servlet>
    <servlet-name>cookie3</servlet-name>
    <servlet-class>com.kuangshen.Cookie3</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>cookie3</servlet-name>
    <url-pattern>/c3</url-pattern>
</servlet-mapping>
```







# 7 Session(重点)



- 服务器会给每个用户（浏览器）创建一个Session对象

- 一个Session独占一个浏览器，只要浏览器没有关闭，这个Session就存在

- 用户登录之后，整个网站都可以访问。

  

Session和cookie的区别

- Cookie是把用户数据写给用户的浏览器，浏览器保存(可以保存多个)
- Session把用户数据写到用户独占的Session中，服务器端保存(保存最要信息，减少服务器资源浪费)
- Session对象有服务器创建



使用场景：

- 保存一个登录用户的信息
- 购物车信息
- 在网站中经常使用的数据





```java
public class Person {
    private String name;
    private Integer age;

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Pserson{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
```

```java
public class SessionDemo1 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        // 得到Session对象
        HttpSession session = req.getSession();

        // 在session中存东西
        session.setAttribute("name", new Person("你好", 1));

        // 获取session的id
        String id = session.getId();

        // 判断session是否是新建的
        if(session.isNew()){
            resp.getWriter().print("创建成功 session id ： "+id);
        }else{
            resp.getWriter().print("已经存在 session id ： "+id);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
```

```java
public class SessionDemo2 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        // 得到Session对象
        HttpSession session = req.getSession();

        Person name = (Person)session.getAttribute("name");

        System.out.println(name);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
```

```java
public class SessionDemo3 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        // 移除key为name的键值对
        session.removeAttribute("name");
        // 注销session
        session.invalidate();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
```

```xml
<servlet>
    <servlet-name>s1</servlet-name>
    <servlet-class>SessionDemo1</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>s1</servlet-name>
    <url-pattern>/s1</url-pattern>
</servlet-mapping>

<servlet>
    <servlet-name>s2</servlet-name>
    <servlet-class>SessionDemo2</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>s2</servlet-name>
    <url-pattern>/s2</url-pattern>
</servlet-mapping>

<servlet>
    <servlet-name>s3</servlet-name>
    <servlet-class>SessionDemo3</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>s3</servlet-name>
    <url-pattern>/s3</url-pattern>
</servlet-mapping>

<session-config>
    <!-- 设置自动注销session的时间，以分钟为单位 -->
    <session-timeout>10</session-timeout>
</session-config>
```



## 销毁session

- 手动销毁

  ```
  session.invalidate();
  ```

  

- 自动销毁

  ```xml
  <session-config>
      <!-- 设置自动注销session的时间，以分钟为单位 -->
      <session-timeout>10</session-timeout>
  </session-config>
  ```

  



