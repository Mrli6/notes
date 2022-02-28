# 性能测试



```bash
redis-benchmark -h 127.0.0.1 -p 6379 -c 50 -n 10000
```

![img](https://images2017.cnblogs.com/blog/707331/201802/707331-20180201145503750-901697180.png)





# 基础知识

默认有16个数据库，默认使用第0个

```bash
select 3 #切换数据库

DBSIZE 	#查看数据库大小

set key value #设置键值对

get key #取值

exists key #查看key是否存在

move key 1 #移除key

expire key 时间 #设置key的过期时间

ttl key #查看key过期时间

keys * #查看当前数据库所有的key

type key #查看key的数据类型

flushdb #清空当前数据库

flushall #清空所有数据库
```



redis是单线程的，6开始支持多线程







# 数据类型



## String

```shell
APPEND key value #在key上追加value

STRLEN key #获取key对应的value的长度

incr key #自增

decr key #自减

incrby key 10（步长） # +10

decrby key 10（步长） # -10

getrange key start end #获取value的start至end的字符串

setrange key offest xxx #将value中的偏移量offset开始替换为xxx

setex #如果存在才设置

setnx #如果不存在才设置

mset k1 v1 k2 v2 k3 v3 #批量设置

mget k1 k2 k3 #批量获取

msetnx k1 v1 k4 v4 #这个批量设置是原子性的，一起成功或失败

set user:1 {name:zhangsan,age:3} #设置一个对象user:1

getset key value #先get再set
```





## List

```bash
LPUSH list xxx #将xxx加入list头部中

LPOP list #移除list头部

RPUSH list xxx #将xxx加入list尾部中

RPOP list #移除list尾部

Lindex list x #通过下标x获取值

Llen list #获取list长度

Lrem list x y #移除list中x个y

Ltrim list x y #通过下标修剪指定的长度

LRANGE list start end #截取list的start至end的元素

rpoplpush list1 list2 #移除list1尾部元素并向list2头部添加元素

EXISTS list #查看list是否存在
```



## Set

set中的值不能重复

```bash
sadd myset xxx #向myset插入xxx

SMEMBERS myset #查看myset的成员

SISMEMBERS myset xxx #查看xxx是否为myset的成员

scard myset #获取myset中元素个数

srem myset xxx #移除myset中的元素xxx

spop myset #随机移除元素

SRANDMEMBER myset x #随机获取myset中x个元素

smove myset1 myset2 xxx #将myset1的xxx元素移到myset2

SDIFF set1 set2 #查看set1中与set2的差集

SINTER set1 set2 #查看set1和set2的交集

SUNION set1 set2 #查看set1和set2的并集
```



## Hash

```shell
hset myhash field v1 #设置hash

hget myhash field #获取hash

hmset myhash field1 hello field2 world #批量设置

hmget myhash field1 field2 #批量获取

hgetall myhash #获取所有

hdel myhash field1 #删除myhash指定的哈希

hlen myhash #获取myhash键值对的个数

hexists myhash field1 #判断是否存在

hkeys myhash #获取myhash所有的field

hvals myhash #获取myhash所有的value
```





## Zset

zset在set上增加一个值使得集合有序

```bash
zadd myset 1 one #添加一个

zadd myset 2 two 3 three #添加多个

zrangebyscore salary -inf +inf #从小到大按salary排序

zrem salary xxx #移除salary中的xxx

zcard salary #查看salary元素个数

zcount salary x y #获取salary中区间xy的元素
```





## geospatial

基于zset实现，可以使用zset命令操作





## hyperloglogs

基数（不重复的元素），可以接受误差

```bash
PFadd mykey a b c d #增加元素

PFcount mykey #统计mykey中基数的个数

PFmerge key3 key1 key2 #key1、key2合并到key3中
```

如果允许容错，使用hyperloglog，否则用set





## bitmaps

```bash
setbit sign 0 1 #设置sign中第一个元素为1

getbit sign 3 #获取sign中第三个元素的值

bitcount sign #获取sign中元素值为1的数量
```







# 事务

redis单条命令是原子性的，但事务不保证原子性

```bash
multi #开启事务

discard #取消事务

exec #执行事务
```



编译型异常（代码写错）：事务中所有命令不执行

运行时异常（1/0）：只执行正常命令，错误命令抛出异常





# 乐观锁

悲观锁：什么时候都会出现问题，无论做什么都加锁

乐观锁：什么时候都不会出现问题，不会加锁；更新数据时判断，在此期间是否有人修改。在mysql中使用version来确保数据一致；redis使用watch监控 





# jedis

是redis官方推荐的java连接开发工具，使用java操作redis中间件。



新建maven工程，导入依赖

```xml
<dependency>
    <groupId>redis.clients</groupId>
    <artifactId>jedis</artifactId>
    <version>3.7.0</version>
</dependency>
```





# springboot整合

springboot2.x之后，原来使用的jedis被替换为lettuce

jedis：采用直连，多个线程操作的话，是不安全的，可以使用jedis pool连接池避免不安全。更像BIO模式

lettuce：采用netty，实例可以在多个线程中共享，不存在线程不安全。更像NIO模式



```properties
spring.redis.host=127.0.0.1
spring.redis.port=6379
```





# redis.conf配置









# 持久化



## RDB

触发条件：

​		1、满足save规则

​		2、执行flushall命令

​		3、退出redis



如何恢复rdb文件？

​		只需将rdb文件放在redis启动目录下（config get dir）





## AOF

记录每个写操作。如果aof文件有问题，则启动不了redis（可以使用redis-check-aof --fix xxx.aof 修复aof文件）





# 发布订阅

```bash
subscribe channel #订阅channel，可以自动接受channel发布者发布的信息

publish channel message #向channel发布message
```







# 主从复制

```bash
info replication #查看当前库的主从信息
```

复制3个配置文件，然后修改对应的信息：

​	1、端口

​	2、pid名字

​	3、log文件名字

​	4、dump.rdb文件名字



## 一主二从

默认情况下，每台redis服务器都是主节点。

一般情况下，只用配置从机就ok



暂时修改：

```bash
slaveof 127.0.0.1 6379 #ip为127.0.0.1的6379端口为老大

slaveof no one #自己当主机
```



永久修改：

在配置文件中配置。



测试：主机断开连接，从机依旧连接到主机，但是没有写操作了，主机恢复连接，写操作依然有用；如果从机断开连接，主机进行写操作，从机恢复连接， 从机恢复默认（不再是从机），如果再次变为从机，立马从主机中获取值

![image-20211019181355886](D:\面向工作学习\数据库\Redis\狂神redis.assets\image-20211019181355886.png)





## 哨兵模式（自动选老大）



配置文件 sentinel.conf

```
sentinel monitor 被监控的主机名称 ip 端口号 1
```

启动哨兵

```bash
redis-sentinel  sentinel.conf
```





## 缓存穿透和雪崩





