# 1、连接池以及事务控制



## 1.1 连接池使用及分析

```
连接池就是用于存储链接的一个容器
容器其实就是一个集合对象，该集合必须是线程安全的，两个线程不能拿到同一个链接
该集合必须实现队列特性：先进先出
```

```
mybatis链接池提供了3种方式的配置
	配置的位置：
			主配置文件种的dataSource标签，type属性就是表示采用何种连接池方式
	type属性的取值：
			POOLED		采用传统的javax.sql.DataSource规范中的连接池
			UNPOOLED	采用传统的获取链接的方式，虽实现了javax.sql.DataSource接口，但没有池的思							想，每次使用都会重新获取一个链接
			JNDI		采用服务器提供的JNDI技术实现来获取DataSource对象，不同服务器所能拿到的							DataSource是不一样的。
						注意：只有web或maven工程才能使用。
						tomcat采用dbcp连接池
```

![image-20210819191624759](C:\Users\94125\AppData\Roaming\Typora\typora-user-images\image-20210819191624759.png)



POOLED执行过程：先看空闲池是否空，若不空，拿里面的链接；若空了，就查看活跃池的数量有没有超过最大值，没有超过就新建一个									连接池；超过了就取最老的链接并等待最老的链接执行结束再执行。



![image-20210819213605749](C:\Users\94125\AppData\Roaming\Typora\typora-user-images\image-20210819213605749.png)







## 1.2 事务控制的分析



mybatis通过sqlsession对象的commit方法和rollback方法实现事务的提交和回滚。

可以设置为自动提交事务，使用以下代码

```java
SqlSession session = factory.openSession(true);
```











# 2、mappers配置文件中的几个标签





## 2.1 if标签



接口

```java
/**
 * 根据参数条件查询数据
 * @param user 参数条件可以是id、名字、性别、地址...也可能没有条件
 * @return
*/
List<User> findByCondition(User user);
```



映射配置文件

```xml
<!-- 根据参数条件查询数据，test是执行条件，只有当test中为true，执行if里的语句 -->
<select id="findByCondition" parameterType="com.mybatis.domain.User" resultType="com.mybatis.domain.User">
    select * from user where 1=1
    <if test="username != null">
        and username = #{username}
    </if>
    <if test="sex != null">
        and sex = #{sex}
    </if>
</select>
```



测试

```java
@Test
public void testFindByCondition(){
    User user = new User();
    user.setUsername("老王");
    user.setSex("女");
    List<User> users = userDao.findByCondition(user);
    for(User u : users){
        System.out.println(u);
    }
}
```







## 2.2 where标签

可以去掉 select * from user where 1=1 里的where 1=1

```xml
<!-- 根据参数条件查询数据 -->
<select id="findByCondition" parameterType="com.mybatis.domain.User" resultType="com.mybatis.domain.User">
    select * from user
    <where>
        <if test="username != null">
            and username = #{username}
        </if>
        <if test="sex != null">
            and sex = #{sex}
        </if>
    </where>
</select>
```





## 2.3 foreach标签

在mysql中，会用到如下语句：

```mysql
select * from uesr where id in(41,42,43,46);
```

如何在mybatis中实现上述代码的功能呢？

在QueryVo类中添加一个类型为List、属性名为ids的属性，并编写set and get方法



接口

```
/**
* 根据QueryVo中提供的ids集合查询数据
* @param vo
* @return
*/
List<User> findByIds(QueryVo vo);
```



映射配置文件

```xml
<!-- 根据QueryVo中提供的ids集合查询数据 -->
<select id="findByIds" resultType="com.mybatis.domain.User">
    select * from user
    <where>
        <if test="ids!=null and ids.size()>0">
            <foreach collection="ids" open="and id in (" close=")" item="uid" separator=",">
                #{uid}
            </foreach>
        </if>
    </where>
</select>
```



测试

```java
@Test
public void testFindByIds(){
    QueryVo vo = new QueryVo();
    List<Integer> list = new ArrayList<Integer>();
    list.add(41);
    list.add(42);
    list.add(43);
    list.add(46);
    vo.setIds(list);
    List<User> users = userDao.findByIds(vo);
    for(User u : users){
        System.out.println(u);
    }
}
```





## 2.4 sql标签

在以上的代码中，经常看到 select * from user 这样的重复代码，我们可以使用标签抽取这个重复部分

```xml
<sql id="defaultUser">
    select * from user
</sql>
```

改写查询所有数据的代码

```xml
<select id="findAll" resultType="com.mybatis.domain.User">
    <include refid="defaultUser"></include>
</select>
```









# 3、多表操作

一对一：一个人只有一个身份证号，一个身份证号只能属于一个人

一对多：用户可以下多个订单，多个订单属于同一个用户

多对多：一个学生被多个老师教过，一个老师教多个学生

特例：如果拿出每一个订单，它只能属于一个用户，所以mabatis把多对一看成了一对一。



示例：用户和账户

​					一个用户可以有多个账户，一个账户只能属于一个用户(多个账户可以属于一个用户)

步骤：

​			1、建立两张表：用户表、账户表

​							让用户表和账户表之间具备一对多的关系：需要使用外键在账户中添加

​			2、建立两个实体类：用户实体类和账户实体类

​							让用户类和账户类体现出一对多关系

​			3、建立两个配置文件：用户、账户配置文件

​			4、实现功能：

​							查询用户时，得到用户下的所有账户信息

​							查询账户时，得到所属用户的用户信息





```
https://www.bilibili.com/video/BV1Db411s7F5?p=49
```

​		



## 3.1 一对一查询





















## 3.2 一对多查询





































## 3.3 mybatis维护多对多关系















































