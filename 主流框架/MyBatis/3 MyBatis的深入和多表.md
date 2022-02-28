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
			UNPOOLED	采用传统的获取链接的方式，虽实现了javax.sql.DataSource接口，但没有池的思想，
						每次使用都会重新获取一个链接
			JNDI		采用服务器提供的JNDI技术实现来获取DataSource对象，不同服务器所能拿到的DataSource是不一样的。
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



# 2、动态SQL



## 2.1 if

```xml
<select id="queryBlogIf" parameterType="map" resultType="blog">
	SELECT * FROM blog WHERE 1=1
    <if test="title != null">
        AND title=#{title}
    </if>
    <if test="author != null">
        AND author=#{author}
    </if>
</select>
```

改进where

```xml
<select id="queryBlogIf" parameterType="map" resultType="blog">
	SELECT * FROM blog
    <where>
        <if test="title != null">
            AND title=#{title}
        </if>
        <if test="author != null">
            AND author=#{author}
        </if>
    </where>
</select>
```

where标签会自动去除 and 或 or





## 2.2 choose

```xml
<select id="queryBlogChoose" parameterType="map" resultType="blog">
    SELECT * FROM blog
    <where>
        <choose>
            <when test="title != null">
                AND title=#{title}
            </when>
			<when test="author != null">
                AND author=#{author}
            </when>
            <otherwise>
            	AND views > 1000
            </otherwise>
        </choose>
    </where>
</select>
```





## 2.3 trim





## 2.4 foreach

在mysql中，会用到如下语句：

```mysql
select * from uesr where id in(41,42,43,46);
```

如何在mybatis中实现上述代码的功能呢？

```xml
<select id="findByIds" resultType="com.mybatis.domain.User">
    select * from user
    <where>
        <if test="ids!=null and ids.size()>0">
            <foreach item="uid" collection="ids" open="and id in (" separator="," close=")">
                #{uid}
            </foreach>
        </if>
    </where>
</select>
```







## 2.5 sql片段

经常看到 select * from user 这样的重复代码，我们可以使用标签抽取这个重复部分

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





## 3.2 多对一查询

### 方式一：

```xml
<resultMap id="StudentTeacher" type="Student">
	<result property="id" column="sid"/>
    <result property="name" column="sname"/>
    <association property="teacher" javaType="Teacher">
    	<result property="name" column="tname"/>
    </association>
</resultMap>

<select id = "getStudent" resultMap="StudentTeacher">
	SELECT s.id AS sid, s.name AS sname, t.name AS tname
    FROM student s, teacher t
    WHERE s.tid = t.id
</select>
```



### 方式二：

```xml
<resultMap id="StudentTeacher" type="Student">
    <result property="id" column="id"/>
    <result property="name" column="name"/>
    <assocation property="teacher" column="tid" javaType="Teacher" select="getTeacher"/>
</resultMap>

<select id = "getStudent" resultMap="StudentTeacher">
	SELECT * from student
</select>

<select id = "getTeacher" resultMap="Teacher">
	SELECT * from teacher where id=#{id}
</select>
```





## 3.3 一对多查询

### 方式一：

```xml
<select id="getTeacher" resultMap="TeacherStudent">
	SELECT s.id AS sid, s.name AS sname, t.name AS tname, t.id AS tid
    FROM student s, teacher t
    WHERE s.tid = t.id and t.id = #{id}
</select>

<resultMap id="TeacherStudent" type="Teacher">
	<result property="id" column="tid"/>
    <result property="name" column="tname"/>
    <collection property="students" ofType="Student">
    	<result property="id" column="sid"/>
        <result property="name" column="sname"/>
        <result property="tid" column="tid"/>
    </collection>
</resultMap>
```



### 方式二：

```xml
<resultMap id="TeacherStudent" type="Teacher">
	<collection property="students" javaType="ArrayList" ofType="Student" select="getStudentByTeacherId" column="id"/>
</resultMap>

<select id="getTeacher" resultMap="TeacherStudent">
	SELECT * FROM teacher WHERE id = #{tid}
</select>

<select id="getStudentByTeacherId" resultType="Student">
	SELECT * FROM student WHERE tid = #{tid}
</select>
```







## 3.4 mybatis维护多对多关系







# 4、分表查询



## 4.1 limit

使用哈希表做参数

```
https://www.bilibili.com/video/BV1NE411Q7Nx?p=13&spm_id_from=pageDriver
```





## 4.2  RowBounds

```
https://www.bilibili.com/video/BV1NE411Q7Nx?p=14&spm_id_from=pageDriver
```

















