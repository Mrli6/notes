语法格式：
	insert into 表名(字段名1,字段名2,字段名3) values(值1,值2,值3);

	注意：字段名和值要一一对应。什么是一一对应？
		数量要对应。数据类型要对应。



insert语句中的“字段名”可以省略吗？可以
	insert into t_student values(2); //错误的

	// 注意：前面的字段名省略的话，等于都写上了！所以值也要都写上！
	insert into t_student values(2, 'lisi', 'f', 20, 'lisi@123.com');



insert插入多条记录？
	insert into t_user(字段名1,字段名2)
	values(值1,值2),(值3,值4);



//将查询结果插入到一张表中
insert into 表名1 select 字段名 from 表名2
	注意：表2的查询结果结构应该和表1结构相同