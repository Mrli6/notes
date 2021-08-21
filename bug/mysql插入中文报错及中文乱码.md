# 出现问题

执行如下sql语句

```mysql
insert into `user`(`id`,`username`,`birthday`,`sex`,`address`) values (41,'老王','2018-02-27 17:47:08','男','北京')
```

报错：

```shell
ERROR 1366 (HY000): Incorrect string value: '\xC0\xCF\xCD\xF5' for column 'username' at row 1
```





# 无效的解答

## 方案一

造成这个错误通常是由于创建数据表的时候使用了不正确的编码格式,可以使用如下命令查看操作的目标数据表的编码格式。

```
SHOW CREATE TABLE 表名；
```

通过结果就可以看到目标表的目标字段对应的编码格式，我们只需要把该列的编码格式转化为`utf8`即可。

```
ALTER TABLE 表名 MODIFY 列名 数据类型 CHARACTER SET utf8 非空约束

例如：ALTER TABLE student MODIFY name VARCHAR(20) CHARACTER SET utf8 NOT NULL 
```

当然这只是一个临时的解决办法，更近一步的解决办法是修改数据表的编码，防止以后在使用命令添加数据列的时候，使用的仍是数据表的编码。

```
ALTER TABLE 表名 DEFAULT CHARSET utf8;
```

然后这还不是最终的解决办法，我们还可以通过修改数据库的编码方式来影响新创建的数据表的编码方式,进而影响数据列的编码方式。

```
ALTER DATABASE 数据库名 DEFAULT CHARACTER SET utf8;
```

但是这需要我们每次在创建数据库的时候都必须指定编码方式，要想一劳永逸，我们可以直接去修改mysql的配置文件，使用如下命令：

```
vim /etc/mysql/mysql.cnf.d/mysqld.cnf
```

添加如下配置

```
[mysqld]
character-set-server=utf8
```

保存之后重新启动mysql服务即可,命令如下(以Ubuntu16.04为例)：

```
systemctl restart mysql.service
```

注意在设置编码格式的时候，使用如下命令查看支持的编码格式：

```
SHOW VARIABLES LIKE 'char%'; 
```

![img](https://img2018.cnblogs.com/blog/1546356/201812/1546356-20181223133802915-809677449.png)







## 方案二

修改文件夹选项，选中“显示隐藏的文件、文件夹和驱动器”

用记事本或者notepad++打开my.ini ,现在开始修改:

找到【mysql】

 

修改成：



然后继续往下找，找到【mysqld】修改：



然后保存，重启mysql服务（记得把mysql的服务进程关闭，不能只是关闭命令行窗口然后重新打开，cmd中命令;net stop mysql,net start mysql）。
————————————————
版权声明：本文为CSDN博主「less_bug」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/sinat_35566306/article/details/90274174



# 有效的解答（中文乱码问题也可以解决）



## 方案一

**尝试了方案一、二，如果都不能解决问题，请使用以下方案**

```shell
SET character_set_client =gbk; 		//设置客服端的编码
SET character_set_results =gbk; 	//设置服务器端结果返回的编码
SET character_set_connection =gbk; 	//设置客服端与服务端连接时的编码
```



原因：ｃｍｄ命令窗口里的编码格式和系统是相同的，mysql有服务端编码和客户端编码概念之分
前者就是你认为的UTF8码。
所有的MYSQL客户端不做任何字符转换，这个字符转换工作由服务器来执行，所以每个客户端在执行前，都要执行SET NAMES XXX 告诉服务器：客户端的编码是什么，以便MYSQL服务器能转换成功。
你的MYSQL客户端，明显是GBK或ansi环境，所以你必须加上一条设置客户端编码的语句set names gbk ， 告诉MYSQL服务器正确的客户端编码。
————————————————
版权声明：本文为CSDN博主「new_Stack」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/weixin_41185247/article/details/86504742





## 方案二

```
https://blog.csdn.net/wy798393546/article/details/78321787
```

