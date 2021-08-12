/*
	在mysql当中，如果一个字段同时被not null和unique约束的话，
	该字段自动变成主键字段。（注意：oracle中不一样！）
*/
唯一性约束unique约束的字段不能重复，但是可以为NULL。
	drop table if exists t_vip;
	create table t_vip(
		id int,
		name varchar(255) unique,
		email varchar(255)
	);
	insert into t_vip(id,name,email) values(1,'zhangsan','zhangsan@123.com');
	insert into t_vip(id,name,email) values(2,'lisi','lisi@123.com');
	insert into t_vip(id,name,email) values(3,'wangwu','wangwu@123.com');
	select * from t_vip;

	insert into t_vip(id,name,email) values(4,'wangwu','wangwu@sina.com');
	ERROR 1062 (23000): Duplicate entry 'wangwu' for key 'name'

	insert into t_vip(id) values(4);
	insert into t_vip(id) values(5);
	+------+----------+------------------+
	| id   | name     | email            |
	+------+----------+------------------+
	|    1 | zhangsan | zhangsan@123.com |
	|    2 | lisi     | lisi@123.com     |
	|    3 | wangwu   | wangwu@123.com   |
	|    4 | NULL     | NULL             |
	|    5 | NULL     | NULL             |
	+------+----------+------------------+
	name字段虽然被unique约束了，但是可以为NULL。


新需求：name和email两个字段联合起来具有唯一性！！！！
	drop table if exists t_vip;
	create table t_vip(
		id int,
		name varchar(255) unique,  // 约束直接添加到列后面的，叫做列级约束。
		email varchar(255) unique
	);
	这张表这样创建是不符合我以上“新需求”的。
	这样创建表示：name具有唯一性，email具有唯一性。各自唯一。

	以下这样的数据是符合我“新需求”的。
	但如果采用以上方式创建表的话，肯定创建失败，因为'zhangsan'和'zhangsan'重复了。
	insert into t_vip(id,name,email) values(1,'zhangsan','zhangsan@123.com');
	insert into t_vip(id,name,email) values(2,'zhangsan','zhangsan@sina.com');

	怎么创建这样的表，才能符合新需求呢？
		drop table if exists t_vip;
		create table t_vip(
			id int,
			name varchar(255),
			email varchar(255),
			unique(name,email) // 约束没有添加在列的后面，这种约束被称为表级约束。
		);
		insert into t_vip(id,name,email) values(1,'zhangsan','zhangsan@123.com');
		insert into t_vip(id,name,email) values(2,'zhangsan','zhangsan@sina.com');
		select * from t_vip;

		name和email两个字段联合起来唯一！！！
		insert into t_vip(id,name,email) values(3,'zhangsan','zhangsan@sina.com');
		ERROR 1062 (23000): Duplicate entry 'zhangsan-zhangsan@sina.com' for key 'name'

	什么时候使用表级约束呢？
		需要给多个字段联合起来添加某一个约束的时候，需要使用表级约束。

unique 和not null可以联合吗？
	drop table if exists t_vip;
	create table t_vip(
		id int,
		name varchar(255) not null unique
	);

	mysql> desc t_vip;
	+-------+--------------+------+-----+---------+-------+
	| Field | Type         | Null | Key | Default | Extra |
	+-------+--------------+------+-----+---------+-------+
	| id    | int(11)      | YES  |     | NULL    |       |
	| name  | varchar(255) | NO   | PRI | NULL    |       |
	+-------+--------------+------+-----+---------+-------+

	在mysql当中，如果一个字段同时被not null和unique约束的话，
	该字段自动变成主键字段。（注意：oracle中不一样！）

	insert into t_vip(id,name) values(1,'zhangsan');

	insert into t_vip(id,name) values(2,'zhangsan'); //错误了：name不能重复

	insert into t_vip(id) values(2); //错误了：name不能为NULL。