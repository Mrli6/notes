两者在linux下和windows下不同，linux下不区分，windows下区分。


在windows下主要区别就是

单引号( ' )或双引号主要用于 字符串的引用符号

如：

mysql> SELECT 'hello', '"hello"', '""hello""', 'hel''lo', '/'hello';

 

数据库、表、索引、列和别名用的是引用符是反勾号(‘`’)  注：Esc下面的键

如：

mysql>SELECT * FROM `select` WHERE `select`.id > 100;



如果SQL服务器模式包括ANSI_QUOTES模式选项，还可以用双引号将识别符引起来：

mysql> CREATE TABLE "test" (col INT);
ERROR 1064: You have an error in your SQL syntax. (...)
mysql> SET sql_mode='ANSI_QUOTES';
mysql> CREATE TABLE "test" (col INT);
Query OK, 0 rows affected (0.00 sec)
————————————————
版权声明：本文为CSDN博主「一夜长风」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/wlzx120/article/details/52524130