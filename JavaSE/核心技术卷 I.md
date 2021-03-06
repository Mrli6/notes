# 第三章 入门

## 类、变量的命名规则

​	类名必须以字母开头
​	变量名开头不能为数字，但可以是字母、下划线、美元符号甚至中文





## 二进制数与数字字面量

​	Java7开始，前缀0b或0B可以写二进制数，如`0b1001`表示数字9；另外，可以为数字字面量加下划线，如`0b10_01`表示数字9，注意：这里的下划线不能出现在数字起始位置和结束位置，如`0b_1001_`





## 当心转义序列

​	注意以下代码

```java
// \u00A0 is a newline
// Look inside c:\users
```

​	对于第一行代码，\u00A0被替换为换行符，《核心技术卷第10版》写道会产生语法错误，但在IDEA2018中可以正常运行

​	对于第二行代码，\u后面跟着4个非十六进制数





## 浮点数的取模

​	Java允许浮点数进行取模运算，如下所示

```java
15.0 % 2
15 % 2.5
```





## strictfp关键字

​	如果将一个类标记为`strictfp`，那么这个类的所有方法都要使用严格的浮点计算。

详情见《核心技术》P38





## Math类中的取模方法floorMod

​	考虑一下问题：计算一个时钟时针的位置。时针所指向的数恒 > 0，如果只是简单的进行操作：`(position + adjustment) % 12`，那么会出现一些问题：`position + adjustment`的值可能是负数，与时针恒 > 0相矛盾。所以不得不进行改进`((position + adjustment) % 12 + 12) % 12`。很麻烦。

​	floorMod可以简化代码`floorMod(position + adjustment, 12)`，但遗憾的是，对于负除数，floorMod会得到负数结果





## 二元操作时的操作数

- 如果两个操作数中有一个是double，另一个就会转换为double
- 否则，如果其中一个操作数是float，另一个就会转换为float
- 否则，如果其中一个操作数是long，另一个就会转换为long
- 否则，两个操作数都将转换为int（byte、short、char之间的二元运算，都会被当作int来处理）



注意如下代码：

```java
int x = 0;
x += 3.5;
```

`x += 3.5;`是合法的，这是把x设置为`(int)(x+3.5)`





## 位运算符

​	移位操作数要完成取模运算，int类型 `1<<35`等同于`1<<3`；对于long类型，需要完成模64运算





## 枚举类型

```java
public class Test{
    enum Size{SMALL, MEDIUM, LARGE, EXTRA_LAGRE};

    public static void main(String[] args){
        Size s = null; 
        s = Size.EXTRA_LAGRE;
        System.out.println(s);
    }
}
```

对于以上代码，Size类型的变量可以为null，也可以为某个枚举值。

### 注意：

​	**枚举类中的枚举值都是实例，在比较两个枚举值时，直接使用 ==，永远不要使用 equals方法**

详情见《核心技术》P188





## 字符串相等比较

​	检测两个字符串是否相等，使用如下表达式：

```java
"hello".equal(str);
// 也可以用 str.compareTo("hello") == 0; 但不建议。
```

​	如果不区分大小写，可以使用 `equalsIgnoreCase` 方法。

​	切记不要使用 == 运算符，这个运算符只是确实两个字符串是否在同一个位置。

### 	注意：

​	**虚拟机只共享字符串常量，对于 + 或 substring 等操作产生的结果是不共享的。**





## swich case标签

- 类型为char、byte、short或int的常量表达式
- 枚举常量
- Java7开始，还可以是字符串字面量







# 第四章 对象与类

## 类之间的常见关系

- 依赖（uses a）
- 聚合（has a）
- 继承（is a）

订单类使用账户类，要查看账户状态才能下订单，所以订单类依赖账户类。应该尽可能地减少依赖

订单类有商品类，因为订单中包含许多商品，这是聚合。





## 打印当前月的日历

![image-20210903152412311](D:\面向工作学习\JavaSE\核心技术卷 I.assets\image-20210903152412311.png)

```java
public class Test{

    public static void main(String[] args){
        LocalDate date = LocalDate.now();
        System.out.println(date);
        int curMonth = date.getMonthValue();
        int curDay = date.getDayOfMonth();
        DayOfWeek weekday = date.getDayOfWeek(); //表示当前日子是星期几
        int value = weekday.getValue();

        date = date.minusDays(curDay-1); // 回退到本月的第一天
        System.out.println("Mon Tue Wed Thu Fri Sat Sun");
        for(int i = 1; i < value; i++){
            System.out.print("  ");
        }

        while(date.getMonthValue() == curMonth){
            System.out.printf("%3d", date.getDayOfMonth());
            if(date.getDayOfMonth() == curDay){
                System.out.printf("*");
            }else{
                System.out.printf(" ");
            }
            date = date.plusDays(1);
            if(date.getDayOfWeek().getValue() == 1){	//下一天是星期一，所以要换行
                System.out.println();
            }
        }
    }
}
```





## 当心返回可变对象

​	首先要理解可变对象，**可变对象表示 可以被外部修改的对象**，比如 属性权限修饰符为public、类中提供了set方法。

举例：

```java
public class Employee{
	pirvate Date date;
    ...
	public Date getDay(){
		return date;
	}
    ...
}
```

在以上代码中，**虽然将属性私有化，但是Date类型的对象中有set、get方法，是一个可变对象**，可以在外部调用Date自身的set方法修改私有属性。如果要解决这个问题，应该对私有属性进行克隆再返回，例如 `return (Date)date.clone();`





## 基于类的访问权限

​	**一个方法可以访问所属类的所有对象的私有数据**

```java
class Employee{
    private String name;
    ...
    public boolean equals(Employee other){
        return name.equals(other.name);	//这个没有使用other的get方法
    }
    ...
}
```

对于以上代码，尽管没有使用get方法，但也能访问到other的私有属性name。





## final修饰成员变量

​	final大多修饰基本类型或者不可变类类型（类中任何方法都不能改变其对象）。

​	对于final修饰不可变类，会造成混乱，如 `private final StringBuffer s = new StringBuffer()`，final关键字只是表示变量s的值不会改变，但指向的值可以发生改变。





## 静态方法

在下面两种情况下使用静态方法：

- 一个方法不需要访问对象状态，其所需的参数都是通过显式参数提供（隐式参数如 this）

  ​	如 Math.pow(a, b)

- 一个方法只需要访问类的静态域





## main方法

​	Employee类中有main方法，java  Employee会执行里面的main方法；如果Test类包含了Employee类，同时里面也有main方法，那么java Test永不会执行Employee中的main方法。

​	详情见《核心技术》P116





## 方法参数

​	java总是采用按值调用，即方法得到的是参数值的一个拷贝。

​	示例可见《核心技术》p118





## 包

![image-20210904193843381](D:\面向工作学习\JavaSE\核心技术卷 I.assets\image-20210904193843381.png)

​	在这种情况下，需要从基目录编译和运行类：

```shell
javac com/mycompany/PayrollApp.java
java com.mycompany.PayrollApp
```

### 注意：

​	**编译器对文件（带有文件分隔符和扩展名.java的文件）进行操作，而 Java解释器加载类（带有.分隔符）**

​	**编译器在编译源文件时不检查目录结构。假定源文件开头有 `package com.mycompany`，即使这个源文件没有在子目录下，也可进行编译。如果他不依赖于其他包，就不会出现编译错误。**

​	详情见《核心技术》P131



## 类设计技巧

- **一定要保证数据私有**

- **一定要对数据初始化**

- **不要在类中使用过多的基本类型**

  ​	Person类中位置有国家、省份、区市、街道等属性，如果在Person类中添加这些基本类型，会显得冗杂，应该将这些属性封装在Address类中，并在Person类中使用Address类

- **不是所有的域都需要独立的访问器和更改器**

  ​	每个人都有隐私，所以要保证这些隐私不能被访问和修改

- **将职责过多的类进行分解**

- **类名和方法名要能够体现它们的职责**

- **优先使用不可变的类**

详情见《核心技术》p144









# 第五章 继承

## 父类不能强转为子类

```java
Employee stuff = new Employee();
```

在以上代码中，出现 `(Manager)stuff` 会报错。

```java
Employee stuff = new Manager();
```

在以上代码中，可以使用 `(Manager)stuff`





## 子类访问父类私有域

​	子类的方法不能直接访问父类的私有域，需要用 super关键字才能完成 从父类继承的私有域的初始化。

​	举例：Employee类中有私有属性 工资；Manager类继承Employee类，在Manager新增私有属性 奖金。

当查询Employee类对象的工资时，直接返回私有属性 工资；但是，Manager类对象工资有两部分，一部分是继承的工资，一部分是奖金，所以要调用父类的查询工资方法，再加上奖金之后返回结果。

详情见《核心技术》P149





## 多态

```java
Employee e;
e = new Employee(...);
e = new Manager(...);
```

​	Employee变量可以引用Employee类对象，也可以引用Employee子类对象。

### 注意：

```java
Manager boss = new Manager(...);
Employee[] staff = new Employee[3];
staff[0] = boss;
```

在这个例子中，boss 和 staff[0] 引用同一个对象。但编译器将 staff[0]看成 Employee对象，所以只能这样调用 `boss.setBonus(...)`，不能这样调用 `staff[0].setBonus(...)`。

详情见《核心技术》P154





## 抽象类

- 类中只要有抽象方法，那就必须把类声明为抽象的
- 类中没有抽象方法，也可以声明为抽象类
- 抽象类不能被实例化，一个抽象类的对象变量，只能引用非抽象子类的对象

详情见《核心技术》P160





## 编写完美equals方法

先看《核心技术》P166，试着理解 对称性

1）显示参数命名为otherObject，稍后需要将它转换成另外一个叫做other的变量

2）检测 this 与 otherObject 是否引用用一个对象

​	if(this == otherObject) return true;

3）检测 otherObject 是否为 null，如果为 null，返回 false。

​	if(otherObject == null) return false;

4）比较 this 与 otherObject 是否属于同一个类。如果 equals 的语义在每个子类中有所改变，就是用 getClass 检测：

​	if(getClass() != otherObject.getClass()) return fasle;

如果所有的子类都拥有统一的语义，就是用 instanceof检测：

​	if(!(otherObject instanceof ClassName)) return false;

5）将 otherObject 转换为相应的类类型变量：

​	ClassName other = (ClassName)otherObject

6）现在开始对所有需要比较的域进行比较了。使用 == 比较基本类型域，使用 equals 比较对象域。如果所有的域都匹配，就返回 true；否则返回 fasle。

​	return field1 == other.field1

​		&& Objects.equals(field2, other.field2)

​		&& ...

如果在子类中重新定义 equals，就要在其中包含调用 super.equals(other)。





## 自动装箱

​	自动装箱规范要求 boolean、byte、char <= 127，介于-128~127之间的 short 和 int 被包装到固定的对象中。

```java
Integer a = 1000;
Integer b = 1000;
if(a == b)... // 结果是false
    
a = 100;
b = 100;
if(a == b)... // 结果是true
```

​	另外，如果在一个条件表达式中混合使用 Integer 和 Double 类型，Integer 值就是拆箱，提升为double，再装箱为Double

```java
Integer n = 1;
Double x = 2.0;
System.out.println(true ? n : x); // 打印 1.0
```





## 反射中数组toString问题

​	如果想要利用反射机制得到某一个类中的所有属性、方法（具体代码可以参考P195），那么必须要注意 类型是数组的参数，如果仅仅按照参考代码编写，那会出现如下的输出：

```java
public [Ljava.lang.String; split (java.lang.String);
public [Ljava.lang.String; split (java.lang.String, int);
```

这是由于数组继承了Object类的 toString 方法，可以稍作改进：

```java
if(type.isArray()){
    Class<?> componentType = type.getComponentType();
    System.out.print(componentType.getName()+"[] ");
}else{
    System.out.print(type.getName()+" ");
}
```

关于更多反射知识，见《核心技术》P190





## 继承的设计技巧

- 将公共操作和域放在超类
- 不要使用受保护的域
- 使用继承实现 "is a"关系
- 除非所有继承的方法都有意义，否则不要使用继承
- 在覆盖方法时，不要改变预期的行为
- 使用多态，而非类型信息
- 不要过多地使用反射

详情见《核心技术》P208







# 第六章 接口、lambda表达式与内部类

## 接口中的元素

- 常量（自动设为 `public static final`）

- 抽象方法 （自动设为 `public`）

  ​	实现接口的类，必须实现接口中的所有抽象方法。

- 静态方法

  ```java
  public interface MyTools{
      public static int add(int a, int b){
          return a+b;
      }
  }
  ```

  ​	详情见《核心技术》P218

- 默认方法

  ```java
  public interface Collection{
      int size(); // 抽象方法
      default boolean isEmpty(){	// 默认方法
          return size() == 0;
      }
  }
  ```

  默认方法可以调用任何其他方法。以上代码中不用操心实现 isEmpty方法了。

  ​	详情见《核心技术》P219





## 接口演化

```java
public class Bag implements Collection
```

Bag类实现了Collection接口，如果以后在Collection中增加了新的抽象方法，那么会报错，因为实现接口的类必须实现接口的所有抽象方法；假设不重新编译这个类，尽管能正常加载，但是在一个Bag实例上调用新的抽象方法，会出现 AbstractMethodError。默认方法可以解决以上问题，因为默认方法不需要在实现类中实现；如果没有重新编译，一个Bag实例调用新方法，将调用Collection.xxx。





## 解决默认方法冲突

- 超类优先
- 接口冲突

详情见《核心技术》P220





## 使用接口中的常量、静态方法

```java
public interface Constant {
    int NUMBER = 1;
    enum Size{SMALL, LARGET};
    public static int add(int a, int b){
        return a+b;
    }
}
```

对于以上代码，我们可以在外部类中使用如下代码 `Constant.NUMBER` `Constant.Size.SMALL` `Constant.add(...)`。





## 接口可以被扩展（继承）

```java
public interface A{
}
public interface B extends A{
}
```





## clone方法的修饰符

详情见《核心技术》P225





## lambda注意事项

- 一个 lambda 表达式只在某些分支返回一个值，而在另外一些分支不返回值，是不合法的。

  ​	例如：`(int x) -> {if(x >= 0) return 1;}`不合法

- 不能把 lambda 表达式赋给类型为 Object 的变量，Object 不是一个函数式接口。

- 如果在 lambda 表达式中引用变量，而这个变量可能在外部改变，是不合法的。

  ​	例如：

  ```java
  public static void repeat(String text, int count){
      for(int i = 0; i < count; i++){
          ActionListner listener = event ->{
              System.out.println(i + ": " + text); 	// error
          }
          new Timer(1000, listener).start();
      }
  }
  ```

- 在 lambda 表达式中声明与一个局部变量同名的参数或局部变量，是不合法的。

  ​	例如：

  ```java
  int first = 0;
  Comparator<String> comp =
      (first, second) -> first.length() - second.length();	// error
  ```

详情见《核心技术》P231





## 内部类

- 只有内部类可以是私有类（private），常规类只可以具有包可见性或共有可见性（public、default、默认）。
- 内部类中声明的所有静态域都必须是final。
- 内部类可以有静态方法，但只能访问外围类的静态域和方法

详情见《核心技术》P244





## 代理

​	跟踪方法调用：

```java
public class Test {
    public static void main(String[] args) {
        Object[] elements = new Object[1000];

        for(int i = 0; i < elements.length; i++){
            Integer element = i+1;
            InvocationHandler invocationHandler = new TraceHandler(element);
            Object o = Proxy.newProxyInstance(null, new Class[]{Comparable.class}, invocationHandler);
            elements[i] = o;    // 向数组中填充了代理对象
        }

        Integer key = new Random().nextInt(elements.length)+1;

        // 只要执行代理对象的方法，那么代理对象自动调用了TraceHander类中的invoke方法
        int i = Arrays.binarySearch(elements, key);
        if(i >= 0){
            System.out.println(elements[i]);
        }
    }
}

class TraceHandler implements InvocationHandler{

    private Object target;
    
    public TraceHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.print(target);
        System.out.print("."+method.getName()+"(");
        if(args != null){
            for(int i = 0; i < args.length; i++){
                System.out.print(args[i]);
                if(i < args.length-1){
                    System.out.print(", ");
                }
            }
        }
        System.out.println(")");

        return method.invoke(target, args);
    }
}
```

代理类一定是public和final。

详情见《核心技术》P259







# 第七章 异常、断言和日志

## 异常

### 必须抛出所有可能的受查异常

​	派生于Error类或RuntimeException类的所有异常称为 非受查（unchecked）异常，所有其他的异常称为 受查（checked）异常。

​	一个方法必须声明所有可能抛出的受查异常，而 非受查异常要么不可控制（Error），要么就应该避免发生（RuntimeException）。





### 子类抛出的异常不能比超类的更通用

​	子类方法可以抛出更特定的异常，或者根本不抛出任何异常。

​	超类没有抛出任何受查异常，子类也不能抛出任何受查异常。





### 捕获异常时的异常变量

```java
catch(FileNotFoundException | UnknownHostException e){...}
```

​	捕获多个异常时，异常变量隐含为 final 变量





### 将原始异常设置为新异常

```java
try{
	...
}catch(SQLException e){
	Throwable se = new ServletException();
	se.initCause(e);
	throw se;
}
```

可以使用 `Throwable e = se.getCause()` 来重新获得原始异常。

​	这个包装技术可以将 受查异常 包装成 运行时异常 抛出





### 抛出更具体异常

```java
try{
	...
}catch(Exception e){
	logger.log(level, message, e);
	throw e;
}
```

假设以上代码在方法 `public void update() throws SQLException` 里面，在 java7 之前编译器查看 catch 块中的 throw 语句，然后查看 e 的类型，会指出这个方法可以抛出任何 Exception 而不只是 SQLException。现在，编译器会跟踪到 e 来自 try 块。





### 解耦合try/catch和try/finally

```java
InputStream in = ...;
try{	// 确保报告出现的错误
    try{	// 确保关闭输入流
        ...
    }finally{
        in.close();
    }
}catch(IOException e){
    ...
}
```





### 带有return的finally

​	当 finally 子句包含 return 时，假设利用 return 语句从 try 语句块中退出。在方法返回之前，finally 子句的内容将被执行。如果 finally 子句中也有 return 语句，这个返回值将覆盖原始的返回值：

```java
public static int f(int n){
    try{
        int r = n * n;
        return r;
    }finally{
        if(n == 2) return 0;
    }
}
```

如果调用 f(2)，结果是 0。





### 带资源的try语句

```java
try(Scanner in = new Scanner(new FileInputStream(“d:\\haha.txt”));  
    PrintWriter out = new PrintWriter(“d:\\hehe.txt”)) {  
    while(in.hasNext()) {  
        out.println(in.next().toUpperCase());  
    }  
}
```

不论这个块如何退出，in 和 out 都会关闭。尽可能使用这种方式。





### 分析堆栈轨迹元素

```java
Thorwable t = new Throwable();
StackTraceElement[] frames = t.getStackTrace();
for(StackTraceElement frame : frames){
    System.out.println(f);
}
```

详情见《核心技术》P280





### 使用异常机制的技巧

- 异常处理不能代替简单的测试

  ​	捕获异常的时间开销很大

- 不要过分地细化异常

- 利用异常层次结构

- 不要压制异常

- 在检测错误是，“苛刻”要比放任更好

- 不要羞于传递异常

详情见《核心技术》P283







## 断言

### 语法

```java
assert 条件;
assert 条件 : 表达式;
```

如果条件是 false，则抛出一个 AssertionError 异常。在第二种形式中，表达式将被传入 AssertionError 的构造器，并转换成一个消息字符串。







## 日志

### 基本日志

​	要生成简单的日志，可以使用全局日志记录器并调用其 info 方法：

```java
Logger.getGlobal().info("hello");
```

​	取消所有的日志：

```java
Logger.getGlobal().setLevel(Level.OFF);
```





### 高级日志

详情见《核心技术》P289







# 第八章 泛型程序设计

## 为什么使用泛型

​	以 ArrayList 举例：在泛型之前，ArrayList类维护一个 Object数组，可以在这个数组里插入任意引用类型，在使用时必须强制类型转换。如果需要一个字符串数组链表，不小心添加了一个其他类型，在使用时会出现强制类型转换异常。

​	使用泛型，可以将强制类型转换异常 变为 编译错误，使得程序具有更好的可读性和安全性。





## 泛型方法

```java
class ArrayAlg{
    public static <T> T getMiddle(T... a){
        return a[a.length/2];
    }
}
```

这个方法可以定义在普通类中，也可以定义在泛型类中。

​	使用如下方法调用：

```java
String middle = ArrayAlg.<String>getMiddle("a","b","c");
String middle = ArrayAlg.getMiddle("a","b","c");	// 编译器会推断T一定是String
```

​	如果想知道编译器的推断结果，可以有目的的引入一个错误：将 `ArrayAlg.getMiddle("a",0,null);` 的结果赋给 JButton，查看错误报告:  `found: java.lang.Object&java.io.Serializable&java.lang.Comparable<? extends ...` ，大意就是可以将结果赋给 Object 、Serializable 、Comparable。





## 类型变量的限定

```java
public static <T extends Comparable & Serializable> T min(T[] a)...
```

将 T 限定为 实现Comparable和Serializable接口的类。





## 泛型代码和虚拟机

​	虚拟机没有泛型类型对象，所有对象都属于普通类。

​	无论何时定义一个泛型类型，都自动提供了一个相应的原始类型。原始类型的名字就是删除类型参数后的泛型类型名。

​	擦除类型变量，并替换为限定类型（有多个限定类型的，原始类型用第一个限定的类型来替换；无限定的变量用 Object 替换）

```java
public class Pair<T>{
    private T first;
    ...
} 
/*	替换为 
    public class Pair{
    	private Object first;
    	...
    }
*/

public class Interval<T extends Comparable & Serializable>{
    private T lower;
    ...
}
/*	替换为
	public class Interval{
		private Comparable lower;
		...
	}
*/
```





## Java泛型转换的事实

- 虚拟机中没有泛型，只有普通的类和方法。

- 所有的类型参数都用它们的限定类型替换。

- 桥方法被合成来保持多态。

  ​	详情见《核心技术》P318

- 为保持类型安全性，必要时插入强制类型转换。





## 约束与局限性

- 不能使用基本类型实例化类型参数

  ​	没有 `Pair<double>` ，只有 `Pair<Double>` 。

- 运行时类型查询只适用于原始类型

  ```java
  if(a instanceof Pair<String>)	// error
  if(a instanceof Pair<T>)	// error
  Pair<String> p = (Pair<String>) a；	// warning--can only test that a is a Pair
      
  Pair<String> s = ...;
  Pair<Employee> e = ...;
  if(s.getClass() == e.getClass())	// true，两次调用 getClass 方法都返回 Pair.class
  ```

- 不能创建参数化类型的数组

  ```java
  Pair<String>[] p = new Pair<String>[10]; // error
  // 只能使用如下方法
  ArrayList<Pair<String>> p = new ArrayList<>();
  ```

- Varargs警告
- 不是实例化类型变量
- 不能构造泛型数组
- 泛型类的静态上下文中类型变量无效
- 不能抛出或捕获泛型类的实例
- 可以消除对受查异常的检查
- 注意擦除后的冲突

详情见《核心技术》P320





## 泛型类型的继承规则

​	考虑 `Pair<T>` ，Employee是Manager的父类，但是 `Pair<Employee>` 和 `Pair<Manager>` 没有任何关系，将 `Pair<Manager>` 转换赋值给 `Pair<Employee>` 是不合法的。

​	`ArrayList<T>` 实现了 `List<T>` 接口，意味着 `ArrayList<Manager>` 可以转换为 `List<Manager>` ，但不能 转换为 `List<Employee>` 。





## 通配符类型

​	对于 `Pair<T>` 中 `Pair<Manager>` 不能转换为 `Pair<Employee` 的问题，可以使用 通配符类型 解决。

​	例如：通配符类型 `Pair<? extends Employee` 表示任何泛型Pair类型，它的类型参数是 Employee 的子类。

详情见《核心技术》P330





## 子类型限定和超类型限定

​	对于子类型限定 `Pair<? extends Employee>` ，其中的get、set方法如下

```java
? extends Employee getFirst(){...}
void setFirst(? extends Employee){...}	// 编译器只知需要Employee的子类，但不知道具体是什么，所以拒绝写入
```

​	对于超类型限定 `Pair<? super Manager>` ，其中的get、set方法如下

```java
? super Manager getFirst(){...}		// 拒绝读取
void setFirst(? super Manager){...}
```







# 第九章 集合

## 具体的集合

### 散列集

​	如果想要查看某个指定的元素，却忘记了它的位置，就需要访问所有元素，直到找到为止，如果集合有很多元素，将会消耗很多时间。

​	如果不在意元素的顺序，可以使用 散列表 快速查找。缺点就是 无法控制元素出现的次序

​	要想查找 散列表 中对象的位置，就要计算它的 散列码，然后与桶的总数取余，所得结果就是保存这个对象的桶的索引



### 树集

​	将加入其中的元素自动排序。



### 双端队列

​	双端队列可以在头部和尾部同时添加或删除。

​	Java 6中引入了 Deque 接口，并由 ArrayDeque 和 LinkedList 类实现。



### 优先级队列

​	优先级队列并没有对所有的元素排序，但删除却总是删除剩余元素中优先级最小的元素。

​	优先级队列使用的是 堆。堆是一个可以自我调整的二叉树，对树执行添加和删除操作，可以让最小的元素移动到根





## 映射

​	hashmap、treemap





## 视图与包装器

​	Arrays类的静态方法 asList 将返回一个包装了普通 java 数组的 List 包装器。`Arrays.asList(...)` 返回的不是 ArrayList ，而是一个视图对象，带有访问底层数组的 set、get 方法。改变数组大小的所有方法（如 add、remove）都会抛出异常。

​	`Collections.nCopies(n, anObject)` 将返回一个实现了 List 接口的不可修改的对象，并给人一种包含 n 个元素，每个元素都像是一个 anObject 的错觉。

​	视图只是包装了接口而不是实际的集合对象，所以只能访问接口中定义的方法。







# 第十四章 并发





















