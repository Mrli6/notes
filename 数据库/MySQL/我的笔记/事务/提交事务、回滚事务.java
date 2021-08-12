提交事务：commit; 语句
回滚事务：rollback; 语句（回滚永远都是只能回滚到上一次的提交点！）

事务对应的英语单词是：transaction

测试一下，在mysql当中默认的事务行为是怎样的？
	mysql默认情况下是支持自动提交事务的。（自动提交）
	什么是自动提交？
		每执行一条DML语句，则提交一次！

	这种自动提交实际上是不符合我们的开发习惯，因为一个业务
	通常是需要多条DML语句共同执行才能完成的，为了保证数据
	的安全，必须要求同时成功之后再提交，所以不能执行一条
	就提交一条。

怎么将mysql的自动提交机制关闭掉呢？
	先执行这个命令：start transaction;

演示事务：
	---------------------------------回滚事务----------------------------------------
	mysql> use bjpowernode;
	Database changed
	mysql> select * from dept_bak;
	Empty set (0.00 sec)

	mysql> start transaction;
	Query OK, 0 rows affected (0.00 sec)

	mysql> insert into dept_bak values(10,'abc', 'tj');
	Query OK, 1 row affected (0.00 sec)

	mysql> insert into dept_bak values(10,'abc', 'tj');
	Query OK, 1 row affected (0.00 sec)

	mysql> select * from dept_bak;
	+--------+-------+------+
	| DEPTNO | DNAME | LOC  |
	+--------+-------+------+
	|     10 | abc   | tj   |
	|     10 | abc   | tj   |
	+--------+-------+------+
	2 rows in set (0.00 sec)

	mysql> rollback;
	Query OK, 0 rows affected (0.00 sec)

	mysql> select * from dept_bak;
	Empty set (0.00 sec)


	---------------------------------提交事务----------------------------------------
	mysql> use bjpowernode;
	Database changed
	mysql> select * from dept_bak;
	+--------+-------+------+
	| DEPTNO | DNAME | LOC  |
	+--------+-------+------+
	|     10 | abc   | bj   |
	+--------+-------+------+
	1 row in set (0.00 sec)

	mysql> start transaction;
	Query OK, 0 rows affected (0.00 sec)

	mysql> insert into dept_bak values(20,'abc
	Query OK, 1 row affected (0.00 sec)

	mysql> insert into dept_bak values(20,'abc
	Query OK, 1 row affected (0.00 sec)

	mysql> insert into dept_bak values(20,'abc
	Query OK, 1 row affected (0.00 sec)

	mysql> commit;
	Query OK, 0 rows affected (0.01 sec)

	mysql> select * from dept_bak;
	+--------+-------+------+
	| DEPTNO | DNAME | LOC  |
	+--------+-------+------+
	|     10 | abc   | bj   |
	|     20 | abc   | tj   |
	|     20 | abc   | tj   |
	|     20 | abc   | tj   |
	+--------+-------+------+
	4 rows in set (0.00 sec)

	mysql> rollback;
	Query OK, 0 rows affected (0.00 sec)

	mysql> select * from dept_bak;
	+--------+-------+------+
	| DEPTNO | DNAME | LOC  |
	+--------+-------+------+
	|     10 | abc   | bj   |
	|     20 | abc   | tj   |
	|     20 | abc   | tj   |
	|     20 | abc   | tj   |
	+--------+-------+------+
	4 rows in set (0.00 sec)