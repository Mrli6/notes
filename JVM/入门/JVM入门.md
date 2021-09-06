# 双亲委派机制

1、类加载器收到类加载的请求

2、将这个请求向上委托给父类加载器，如果有这个类的缓存就返回，没有就一直委托到启动类加载器

3、启动类加载器检查是否能加载当前类，能加载就结束，否则抛出异常并通知子类加载器加载

4、重复步骤 3







# 沙箱安全机制









# native关键字







# 堆

一个JVM只有一个堆内存，堆内存的大小可以调节。

```java
// 设置进入老年代的时间
-XX:MaxTenuringThreshold=???
```



## 推内存调优

打印GC信息

```java
-Xms1024m -Xms1024m -xx:+PrintGCDetails
```



## Jprofiler分析OOM

settings ---> Plugins ---> 搜 Jprofiler ---> download



得到dump文件

```java
-Xms1m -Xmx8m -XX:+HeapDumpOnOutOfMemoryError
```





# GC



## 引用计数法









## 复制算法



对于幸存区，哪个区是空的哪个区就是to



好处：没有内存碎片

坏处：浪费内存空间

最佳使用场景：对象存活度较低







## 标记清除算法



标记活着的对象，对没有标记的对象进行清除



好处：不需要额外空间

坏处：两次扫描，浪费时间，产生内存碎片







## 标记压缩算法









# JMM

java memory model