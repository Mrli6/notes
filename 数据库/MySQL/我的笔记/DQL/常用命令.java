登录：
	mysql -hlocalhost -P3306 -uroot -p密码

本地登录：
	mysql -uroot -p密码

退出：
	exit

查看mysql数据库的版本号：
	select version();

查看当前使用的是哪个数据库？
	select database();

查看数据库：
	show databases;

创建数据库：
	create database 库名;

查看数据库里的表：
	show tables from mysql;
	-- 此处mysql指的是数据库名；
	
	或者

	use mysql;#选择数据库
	show tables;

查看表的结构：
	desc 表名;

查看表中的数据：
	select * from 表名;

查询一个字段：
	select 字段名 from 表名;

查询多个字段：
	select 字段名,字段名 from 表名;

给一个字段取别名并查看：
	select 字段名 as 别名 from 表名;
		注意：别名中出现空格或者中文，需要加引号''

字段可以使用数学表达式：
	select ename,sal*12 from emp;

条件查询：
	select
		...
	from
		...
	where
		...
	

排序：
	select
		...
	from
		...
	where
		...
	order by
		...（desc降序，asc升序）
	以上语句执行顺序：
		第一步：from
		第二步：where
		第三步：select
		第四步：order by（排序最后执行）