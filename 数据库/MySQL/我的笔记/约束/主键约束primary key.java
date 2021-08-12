/*
主键除了：单一主键和复合主键之外，还可以这样进行分类？
	自然主键：主键值是一个自然数，和业务没关系。
	业务主键：主键值和业务紧密关联，例如拿银行卡账号做主键值。这就是业务主键！

注意：一张表，主键约束只能添加1个。（主键只能有1个。）

主键值建议使用：
	int
	bigint
	char
	等类型。

不建议使用：varchar来做主键。主键值一般都是数字，
			一般都是定长的！
*/
主键约束的相关术语？
		主键约束：就是一种约束。
		主键字段：该字段上添加了主键约束，这样的字段叫做：主键字段
		主键值：主键字段中的每一个值都叫做：主键值。
	
什么是主键？有啥用？
	主键值是每一行记录的唯一标识。
	主键值是每一行记录的身份证号！！！

记住：任何一张表都应该有主键，没有主键，表无效！！

主键的特征：not null + unique（主键值不能是NULL，同时也不能重复！）


怎么给一张表添加主键约束呢？
	drop table if exists t_vip;
	// 1个字段做主键，叫做：单一主键
	create table t_vip(
		id int primary key,  //列级约束
		name varchar(255)
	);
	insert into t_vip(id,name) values(1,'zhangsan');
	insert into t_vip(id,name) values(2,'lisi');

	//错误：不能重复
	insert into t_vip(id,name) values(2,'wangwu');
	ERROR 1062 (23000): Duplicate entry '2' for key 'PRIMARY'

	//错误：不能为NULL
	insert into t_vip(name) values('zhaoliu');
	ERROR 1364 (HY000): Field 'id' doesn't have a default value

可以这样添加主键吗，使用表级约束？
	drop table if exists t_vip;
	create table t_vip(
		id int,
		name varchar(255),
		primary key(id)  // 表级约束
	);
	insert into t_vip(id,name) values(1,'zhangsan');

	//错误
	insert into t_vip(id,name) values(1,'lisi');
	ERROR 1062 (23000): Duplicate entry '1' for key 'PRIMARY'

表级约束主要是给多个字段联合起来添加约束？
	drop table if exists t_vip;
	// id和name联合起来做主键：复合主键！！！！
	create table t_vip(
		id int,
		name varchar(255),
		email varchar(255),
		primary key(id,name)
	);
	insert into t_vip(id,name,email) values(1,'zhangsan','zhangsan@123.com');
	insert into t_vip(id,name,email) values(1,'lisi','lisi@123.com');

	//错误：不能重复
	insert into t_vip(id,name,email) values(1,'lisi','lisi@123.com');
	ERROR 1062 (23000): Duplicate entry '1-lisi' for key 'PRIMARY'

	在实际开发中不建议使用：复合主键。建议使用单一主键！
	因为主键值存在的意义就是这行记录的身份证号，只要意义达到即可，单一主键可以做到。
	复合主键比较复杂，不建议使用！！！

一个表中主键约束能加两个吗？
	drop table if exists t_vip;
	create table t_vip(
		id int primary key,
		name varchar(255) primary key
	);
	ERROR 1068 (42000): Multiple primary key defined
	结论：一张表，主键约束只能添加1个。（主键只能有1个。）
	
主键值建议使用：
	int
	bigint
	char
	等类型。

	不建议使用：varchar来做主键。主键值一般都是数字，一般都是定长的！

主键除了：单一主键和复合主键之外，还可以这样进行分类？
	自然主键：主键值是一个自然数，和业务没关系。
	业务主键：主键值和业务紧密关联，例如拿银行卡账号做主键值。这就是业务主键！

	在实际开发中使用业务主键多，还是使用自然主键多一些？
		自然主键使用比较多，因为主键只要做到不重复就行，不需要有意义。
		业务主键不好，因为主键一旦和业务挂钩，那么当业务发生变动的时候，
		可能会影响到主键值，所以业务主键不建议使用。尽量使用自然主键。

在mysql当中，有一种机制，可以帮助我们自动维护一个主键值？
	drop table if exists t_vip;
	create table t_vip(
		id int primary key auto_increment, //auto_increment表示自增，从1开始，以1递增！
		name varchar(255)
	);
	insert into t_vip(name) values('zhangsan');
	insert into t_vip(name) values('zhangsan');
	insert into t_vip(name) values('zhangsan');
	insert into t_vip(name) values('zhangsan');
	insert into t_vip(name) values('zhangsan');
	insert into t_vip(name) values('zhangsan');
	insert into t_vip(name) values('zhangsan');
	insert into t_vip(name) values('zhangsan');
	select * from t_vip;

	+----+----------+
	| id | name     |
	+----+----------+
	|  1 | zhangsan |
	|  2 | zhangsan |
	|  3 | zhangsan |
	|  4 | zhangsan |
	|  5 | zhangsan |
	|  6 | zhangsan |
	|  7 | zhangsan |
	|  8 | zhangsan |
	+----+----------+

