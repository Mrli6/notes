实体类

```java
package domain;

import java.io.Serializable;

public class User implements Serializable {
    private String userName;
    private String password;
    private Integer userAge;

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Integer getUserAge() {
        return userAge;
    }
    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }
}

```



response.jsp

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

    <script src="js/jquery.min.js"></script>

    <script>
        //页面加载，绑定单击事件
        $(function () {
            $("#btn").click(function() {
                //发送ajax请求
                $.ajax({
                    //编写json格式，设置属性和值
                    url:"user/testAjax",
                    contentType:"application/json;charset=UTF-8",
                    data:'{"username":"hehe","password":"123","age":30}',
                    dataType:"json",
                    type:"post",
                    success:function (data) {
                        //data服务器端响应的json数据，进行解析
                        alert(data);
                        alert(data.username);
                        alert(data.password);
                        alert(data.age);
                    }
                });
            });
        });
    </script>

</head>
<body>

    <button id="btn">发送ajax的请求</button>

</body>
</html>
```





控制类

```java
@RequestMapping(path = "/testAjax")
public @ResponseBody User testAjax(@RequestBody User user){
    System.out.println("testAjax执行了...");
    //客户端发送ajax请求，传的是json字符串，后端把json字符串封装到user对象中
    System.out.println(user);

    //做响应，模拟查询数据库
    user.setUserName("haha");
    user.setPassword("666");
    user.setUserAge(20);
    //做响应
    return user;
}
```











对于以上代码，后台接受值为null，前台接受值为undefined。

检查：在实体类中重写toString方法，控制台输出

原因：实体类的名称是userName、password、userAge

​			而jsp文件中是username、password、age 导致出错

​			名称必须一致