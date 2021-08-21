# 1、单表crud操作

### IUserDao接口

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

    /**
     * 保存数据
     * @param user
     */
    void saveUser(User user);

    /**
     * 更新数据
     * @param user
     */
    void updateUser(User user);

    /**
     * 根据id删除数据
     * @param id
     */
    void deleteUser(Integer id);

    /**
     * 根据id查询一个数据
     * @param id
     * @return User对象
     */
    User findById(Integer id);

    /**
     * 根据名字模糊查询
     * @param name
     * @return User对象的集合
     */
    List<User> findByName(String name);

    /**
     * 查询数据的总条数
     * @return 总条数
     */
    int findTotal();
}
```



### 映射配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">


<!-- 映射配置文件 -->
<mapper namespace="com.mybatis.dao.IUserDao">
    <!--查询所有数据, resultType属性是将结果集封装为User-->
    <select id="findAll" resultType="com.mybatis.domain.User">
        select * from user
    </select>

    <!-- 保存数据，parameterType属性是参数的类型 -->
    <insert id="saveUser" parameterType="com.mybatis.domain.User">
        <!-- 查询新增数据的id值，KeyProperty对应的是实体类中的名称 -->
        <selectKey keyProperty="id" keyColumn="id" resultType="INT" order="AFTER">
            select last_insert_id();
        </selectKey>
        insert into user(username, birthday, sex, address) value(#{username},#{birthday},#{sex},#{address});
    </insert>

    <!-- 更新数据 -->
    <update id="updateUser" parameterType="com.mybatis.domain.User">
        update user set username=#{username}, birthday=#{birthday}, sex=#{sex}, address=#{address} where id=#{id};
    </update>

    <!-- 删除数据，因为只有一个值，所以#{}里的名称可以随便设置 -->
    <delete id="deleteUser" parameterType="java.lang.Integer">
        delete from user where id=#{uid};
    </delete>

    <!-- 根据id值查询一个数据 -->
    <select id="findById" parameterType="INT" resultType="com.mybatis.domain.User">
        select * from user where id=#{uid};
    </select>

    <!-- 根据名字模糊查询 -->
    <select id="findByName" parameterType="java.lang.String" resultType="com.mybatis.domain.User">
        <!-- 使用的PreparedStatement预处理方式 -->
        select * from user where username like #{s};
        <!--  模糊查询的另一种方式,中间的名称必须是value，这种方式是Statement的字符拼接sql语句，
        select * from user where username like '%${value}%';
        -->
    </select>

    <!-- 查询数据的总条数 -->
    <select id="findTotal" resultType="INT">
        select count(id) from user;
    </select>
</mapper>
```



### 测试代码

```java
/**
 * 入门案例
 */
public class MybatisTest {

    private InputStream in;
    private SqlSessionFactory factory;
    private SqlSession session;
    private IUserDao userDao;

    @Before //示在任意使用@Test注解标注的public void方法执行之前执行
    public void init() throws Exception {
        //1.读取配置文件
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2.创建SqlSessionFactory工厂
        factory = new SqlSessionFactoryBuilder().build(in);
        //3.使用工厂生成SqlSession对象
        session = factory.openSession();
        //4.使用SqlSession创建Dao接口的代理对象
        userDao = session.getMapper(IUserDao.class);
    }

    @After  //表示在任意使用@Test注解标注的public void方法执行之后执行
    public void destory() throws Exception {
        //提交事务
        session.commit();
        //释放资源
        session.close();
        in.close();
    }

    @Test
    public void testFindAll() {
        List<User> users = userDao.findAll();
        for(User user : users){
            System.out.println(user);
        }
    }

    @Test
    public void testSaveUser(){
        User user = new User();
        user.setUsername("你好");
        user.setBirthday(new Date());
        user.setSex("男");
        user.setAddress("杭州");
        System.out.println("保存之前："+user);
        userDao.saveUser(user);
        System.out.println("保存之后："+user);
    }

    @Test
    public void testUpdateUser(){
        User user = new User();
        user.setId(50);
        user.setUsername("你也好");
        user.setBirthday(new Date());
        user.setSex("女");
        user.setAddress("杭州");
        userDao.updateUser(user);
    }

    @Test
    public void testDeleteUser(){
        userDao.deleteUser(50);
    }

    @Test
    public void testFindById(){
        User user = userDao.findById(41);
        System.out.println(user);
    }

    @Test
    public void testFindByName(){
        List<User> users = userDao.findByName("%王%");
        /* 如果用value方式，不需要在这里的字符串中提供%
        */
        for(User user : users){
            System.out.println(user);
        }
    }

    @Test
    public void testFindTotal(){
        int count = userDao.findTotal();
        System.out.println(count);
    }
}

```











# 2、参数深入



## 2.1 Mybatis的参数



### 2.1.1 parameterType(输入类型)



### 2.1.2 传递简单类型



### 2.1.3 传递pojo对象

Mybatis使用ognl表达式解析对象字段的值，#{}或者${}括号中的值为pojo属性名称

ognl表达式：Object Graphic Navigation Language

​		它通过对象的get方法来获取数据，在写法上把get省略

​		比如：获取用户名称

​							类中的写法：user.getUsername();

​							ognl表达式写法：user.username

​		为什么在mybatis中，可以直接username，而不是user.username？

​							因为在parameterType中已经提供了属性所属的类，所以可以不需要写对象名



### 2.1.4 传递pojo包装对象

创建一个实体类，该实体类中有User属性

```java
public class QueryVo {
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
```



在IUserDao接口中添加如下代码

```java
/**
* 根据QueryVo中的条件查询数据
* @param vo
* @return
*/
List<User> findByVo(QueryVo vo);
```



在IUserDao.xml中添加如下代码

```xml
<!-- 根据QueryVo中的条件查询数据 -->
<select id="findByVo" parameterType="com.mybatis.domain.QueryVo" resultType="com.mybatis.domain.User">
    <!-- #{}里面的user是QueryVo对象里的属性 -->
    select * from user where username like #{user.username};
</select>
```



测试

```java
@Test
public void testFindByVo(){
    QueryVo queryVo = new QueryVo();
    User user = new User();
    user.setUsername("%王%");
    queryVo.setUser(user);
    List<User> users = userDao.findByVo(queryVo);
    for(User u : users){
        System.out.println(u);
    }
}
```













## 2.2 Mybatis的输出结果封装



### 2.2.1 resultType(输出类型)



#### 2.2.1.1 输出简单类型



#### 2.2.1.2 输出pojo对象



#### 2.2.1.3 输出pojo列表











### 2.2.2 resultMap结果类型



**resultMap可以解决实体类属性名与数据库列名不对应的问题，详情见2.3**









## 2.3 实体类属性名与数据库列名不对应问题



**数据库列名为id、sex，实体类名是userId、userSex**

**改变了实体类中的属性名称，#{}和${}中的值也都要改变，因为这是一一对应的。改变之后增删改可以正常执行**

**，但是查询出错，因为实体类的名称已经与数据库名称不一致了**

```
https://www.bilibili.com/video/BV1Db411s7F5?p=28&spm_id_from=pageDriver
```



#### 解决方法

方法一：在映射配置文件IUserDao.xml中的查询语句里起别名

```
select id as userId, sex as userSex from user;
```



方法二：不改变sql语句，配置对应关系

```xml
<!-- 配置 查询结果的列名和实体类的属性名的对应关系 -->
<resultMap id="userMap" type="com.mybatis.domain.User">
    <!-- 主键字段的对应 -->
    <id property="userId" column="id"></id>
    <!-- 非主键字段的对应 -->
    <result property="userSex" column="sex"></result>
</resultMap>

<!-- 查询结果 -->
<select id="findAll" resultMap="userMap">
	select * from user;
</select>
```











# 3、实现DAO层开发

```
https://www.bilibili.com/video/BV1Db411s7F5?p=30
```













# 4、主配置文件的细节(标签的使用)



**主配置文件configuration标签里可以配置properties标签**

```xml
<!-- 配置properties
	可以在标签内部配置连接数据库的信息，也可以通过标签里的resource属性引用外部配置文件信息
	resource属性：
		用于指定配置文件的位置，是按照类路径的写法来写的，并且必须存在于类路径下
	url属性：
		与resource属性作用相同，但必须按照协议写法写地址
	这两个属性也可以用来配置mapper
-->
<!-- 内部配置 -->
<properties>
    <property name="dirver" value="com.mysql.cj.jdbc.Driver"/>
    <property name="url" value="jdbc:mysql://localhost:3306/user_db?serverTimezone=UTC"/>
    <property name="username" value="root"/>
    <property name="password" value="123"/>
</properties>


<!-- 引用外部配置文件 -->
<properties resource="jdbcConfig.properties">
</properties>
```

同时也要修改配置数据连接池的信息

```xml
<!-- 配置数据源（连接池） -->
<dataSource type="POOLED">
    <!-- 配置连接数据库的4个基本信息 -->
    <property name="driver" value="${dirver}"/>
    <property name="url" value="${url}"/>
    <property name="username" value="${username}"/>
    <property name="password" value="${password}"/>
</dataSource>
```





**主配置文件configuration标签里可以配置typeAliases标签**

```xml
<!-- 使用typeAliases配置别名，他只能配置domain中的类的别名，起别名之后可以在映射配置文件的形参类型和结果类型中使用该别名
	typeAliases、package属性都起到起别名的作用
	package属性也可以用来配置mapper
-->
<typeAliases>
    <!-- type属性指定的是实体类全限定类名；alias属性指定别名，当指定了别名就不再区分大小写 -->
    <typeAlias type="com.mybatis.domain.User" alias="user"></typeAlias>
    
    <!-- 用于指定要配置别名的包，当指定之后，该包下的实体类都会注册别名，并且类名就是别名，不再区分大小写 -->
    <package name="com.mybatis.domain"></package>
</typeAliases>
```





# 5、typeAliases和resultMap标签区别

```
typeAliases编写在主配置文件configuration标签中；resultMap编写在映射配置文件mapper标签中
typeAliases只能给类名起别名；resultMap主要任务是解决实体类属性名和数据库列名的不对应问题，但也可以起到别名的作用
```



### typeAliases

```xml
<!-- typeAliases给User类起别名，如果在select标签中配置了resultType="uSer",返回值封装为User类型，其中user不区分大小写 
-->
<typeAliases>
    <typeAlias type="com.mybatis.domain.User" alias="user"></typeAlias>
</typeAliases>
```

```xml
<!-- 查询结果 -->
<select id="findAll" resultType="uSer">
    select * from user;
</select>
```



### resultMap

```xml
<!-- resultMap给User类起别名userMap，如果在select标签中配置resultMap="userMap"，返回值封装为User类型 -->
<resultMap id="userMap" type="com.mybatis.domain.User"></resultMap>

<!-- 查询结果 -->
<select id="findAll" resultMap="userMap">
    select * from user;
</select>
```

