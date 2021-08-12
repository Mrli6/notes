# 什么是JdbcTemplate？



**Spring框架对JDBC进行封装，使用JdbcTemplate方便实现对数据库操作**







# 准备工作

（1）引入jar包

```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.16</version>
</dependency>

<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-jdbc</artifactId>
    <version>5.3.9</version>
</dependency>

<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-tx</artifactId>
    <version>5.3.9</version>
</dependency>

<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-orm</artifactId>
    <version>5.3.9</version>
</dependency>

<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid</artifactId>
    <version>1.1.9</version>
</dependency>
```



（2）配置数据库连接池

```xml
<!-- 数据库连接池 -->
<bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
    <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"></property>
    <property name="url" value="jdbc:mysql://localhost:3306/user_db?serverTimezone=UTC"></property>
    <property name="username" value="root"></property>
    <property name="password" value="123"></property>
</bean>
```



（3）创建JdbcTemplate对象，注入DataSource

```xml
<!-- JdbcTemplate对象 -->
<bean id = "jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
	<!-- 注入dataSource（用set方法） -->
    <property name="dataSource" ref="dataSource"></property>
</bean>
```



（4）创建Service类、Dao类，在Dao注入jdbcTemplate对象

```xml
<!-- 开启组件扫描 -->
<context:component-scan base-package="com.jdbctemplate"></context:component-scan>
```

```java
@Service
public class BookService {
    //注入dao对象
    @Autowired
    private BookDao bookDao;
}
```

```java
public interface BookDao {
}
```

```java
@Repository
public class BookDaoImpl implements BookDao {
    //注入jdbcTemplate对象
    @Autowired
    private JdbcTemplate jdbcTemplate;
}
```









# JdbcTemplate操作数据库（添加）

（1）创建一个实体类，这个类对应于数据库中的一张表

```java
public class Book {
    private int bookId;
    private String bookName;
    private String bookStatus;

    public int getBookId() {
        return bookId;
    }
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
    public String getBookName() {
        return bookName;
    }
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
    public String getBookStatus() {
        return bookStatus;
    }
    public void setBookStatus(String bookStatus) {
        this.bookStatus = bookStatus;
    }
}
```



（2）完善Service类、Dao类

```java
@Service
public class BookService {
    //注入dao对象
    @Autowired
    private BookDao bookDao;

    //在表中添加数据的方法
    public void addBook(Book book){
        bookDao.add(book);
    }
}
```

```java
public interface BookDao {
    void add(Book book);
}
```

```java
@Repository
public class BookDaoImpl implements BookDao {
    //注入jdbcTemplate对象
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //在表中添加数据的方法
    @Override
    public void add(Book book) {
        //创建sql语句
        String sql = "insert into t_book value(?,?,?)";
        Object[] args = new Object[]{book.getBookId(), book.getBookName(), book.getBookStatus()};
        /*
        	update方法有两个参数
            第一个参数：sql语句
            第二个参数：可变参数，设置sql语句值
        */
        int num = jdbcTemplate.update(sql, args);
        System.out.println(num);
    }

}
```



（3）测试

```java
@Test
public void test(){
    ApplicationContext context = new ClassPathXmlApplicationContext("jdbcTemplate1.xml");
    BookService bookService = context.getBean("bookService", BookService.class);
    Book book = new Book();
    book.setBookId(2);
    book.setBookName("spring");
    book.setBookStatus("online");
    bookService.addBook(book);
}
```













# JdbcTemplate操作数据库（修改和删除）



```java
//修改表中数据的方法
@Override
public void modify(Book book) {
    //创建sql语句
    String sql = "update t_book set bookName=?, bookStatus=? where bookId=?";
    Object[] args = new Object[]{book.getBookName(), book.getBookStatus(), book.getBookId()};
    int num = jdbcTemplate.update(sql, args);
    System.out.println(num);
}

//根据id值删除表中数据的方法
@Override
public void delete(int id) {
    //创建sql语句
    String sql = "delete from t_book where bookId=?";
    int num = jdbcTemplate.update(sql, id);
    System.out.println(num);
}
```











# JdbcTemplate操作数据库（查询）



## 查询返回某个值

例子：查询表里有多少条记录，返回条数

```java
//查询表中数据条数
@Override
public int selectCount() {
    //创建sql语句
    String sql = "select count(*) from t_book";
    /*
        queryForObject(String sql, Class<T> requiredType)
        第一个参数：sql语句
        第二个参数：返回类型Class
    */
    Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
    return count;
}
```









## 查询返回对象

场景：查询图书详情信息

```java
//根据id值查询返回对象
@Override
public Book findBook(int id) {
    //创建sql语句
    String sql = "select * from t_book where bookId=?";
    /*
        queryForObject(String sql, RowMapper<T> rowMapper, Object... args)
        第一参数：sql语句
        第二参数：RowMapper是接口，返回不同类型的数据，使用这个接口里的实现
                类完成数据封装
        第三参数：sql语句值
    */
    Book book = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Book>(Book.class), id);

    return book;
}
```









## 查询返回集合

场景：查询图书列表分页...

```java
//查询返回集合
@Override
public List<Book> findList() {
    //创建sql语句
    String sql = "select * from t_book";
    List<Book> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Book>(Book.class));
    return list;
}
```











# JdbcTemplate操作数据库（批量操作）

批量操作：操作表里的多条数据



## 批量添加

```java
//批量添加操作
@Override
public void batchAdd(List<Object[]> batchArgs) {
    //创建sql语句
    String sql = "insert into t_book value(?,?,?)";
    /*
        batchUpdate(String sql, List<Object[]> batchArgs)
        第一参数：sql语句
        第二参数：List集合，添加多条数据
    */
    int[] ints = jdbcTemplate.batchUpdate(sql, batchArgs);
    System.out.println(Arrays.toString(ints));
}
```

```java
//测试
@Test
public void test(){
    ApplicationContext context = new ClassPathXmlApplicationContext("jdbcTemplate1.xml");
    BookService bookService = context.getBean("bookService", BookService.class);
    List<Object[]> list = new ArrayList<>();
    Object[] obj1 = new Object[]{3, "springmvc", "online"};
    Object[] obj2 = new Object[]{4, "mybaits", "online"};
    Object[] obj3 = new Object[]{5, "springcloud", "online"};
    list.add(obj1);
    list.add(obj2);
    list.add(obj3);
    bookService.batchAdd(list);
}
```







## 批量修改

```java
//批量修改操作
@Override
public void batchModify(List<Object[]> batchArgs) {
    //创建sql语句
    String sql = "update t_book set bookName=?, bookStatus=? where bookId=?";
    int[] ints = jdbcTemplate.batchUpdate(sql, batchArgs);
    System.out.println(Arrays.toString(ints));
}
```

```java
//测试
@Test
public void test(){
    ApplicationContext context = new ClassPathXmlApplicationContext("jdbcTemplate1.xml");
    BookService bookService = context.getBean("bookService", BookService.class);
    List<Object[]> list = new ArrayList<>();
    Object[] obj1 = new Object[]{"springmvc", "no", 3};
    Object[] obj2 = new Object[]{"mybaits", "no", 4};
    Object[] obj3 = new Object[]{"springcloud", "no", 5};
    list.add(obj1);
    list.add(obj2);
    list.add(obj3);
    bookService.batchModify(list);
}
```









## 批量删除

```java
//批量删除操作
@Override
public void batchDelete(List<Object[]> batchArgs) {
    //创建sql语句
    String sql = "delete from t_book where bookId=?";
    int[] ints = jdbcTemplate.batchUpdate(sql, batchArgs);
    System.out.println(ints);
}
```

```java
//测试
@Test
public void test(){
    ApplicationContext context = new ClassPathXmlApplicationContext("jdbcTemplate1.xml");
    BookService bookService = context.getBean("bookService", BookService.class);
    List<Object[]> list = new ArrayList<>();
    Object[] obj1 = new Object[]{1};
    Object[] obj2 = new Object[]{5};
    list.add(obj1);
    list.add(obj2);
    bookService.batchDelete(list);
}
```







