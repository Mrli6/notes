外键约束涉及到的相关术语：
	外键约束：一种约束（foreign key）
	外键字段：该字段上添加了外键约束
	外键值：外键字段当中的每一个值。


业务背景：
	请设计数据库表，来描述“班级和学生”的信息？

	第一种方案：班级和学生存储在一张表中？？？
	t_student
	no(pk)			name		classno			classname
	----------------------------------------------------------------------------------
	1				jack			100			北京市大兴区亦庄镇第二中学高三1班
	2				lucy			100			北京市大兴区亦庄镇第二中学高三1班
	3				lilei			100			北京市大兴区亦庄镇第二中学高三1班
	4				hanmeimei		100			北京市大兴区亦庄镇第二中学高三1班
	5				zhangsan		101			北京市大兴区亦庄镇第二中学高三2班
	6				lisi			101			北京市大兴区亦庄镇第二中学高三2班
	7				wangwu			101			北京市大兴区亦庄镇第二中学高三2班
	8				zhaoliu			101			北京市大兴区亦庄镇第二中学高三2班
	分析以上方案的缺点：
		数据冗余，空间浪费！！！！
		这个设计是比较失败的！
	
	第二种方案：班级一张表、学生一张表？？
	
	t_class 班级表
	classno(pk)			classname
	------------------------------------------------------
	100					北京市大兴区亦庄镇第二中学高三1班
	101					北京市大兴区亦庄镇第二中学高三1班

	t_student 学生表
	no(pk)			name				cno(FK引用t_class这张表的classno)
	----------------------------------------------------------------
	1				jack				100
	2				lucy				100
	3				lilei				100
	4				hanmeimei			100
	5				zhangsan			101
	6				lisi				101
	7				wangwu				101
	8				zhaoliu				101

	当cno字段没有任何约束的时候，可能会导致数据无效。可能出现一个102，但是102班级不存在。
	所以为了保证cno字段中的值都是100和101，需要给cno字段添加外键约束。
	那么：cno字段就是外键字段。cno字段中的每一个值都是外键值。

	注意：
		t_class是父表
		t_student是子表

		删除表的顺序？
			先删子，再删父。

		创建表的顺序？
			先创建父，再创建子。

		删除数据的顺序？
			先删子，再删父。

		插入数据的顺序？
			先插入父，再插入子。

	思考：子表中的外键引用的父表中的某个字段，被引用的这个字段必须是主键吗？
		不一定是主键，但至少具有unique约束。

	测试：外键可以为NULL吗？
		外键值可以为NULL。

案例代码：
drop table if exists t_student
drop table if exists t_class

create table t_class(
	classno int primary key,
	classname varchar(255)
);

create table t_student(
	no int primary key auto_increment,
	name verchar(255),
	cno int,
	foreign key(cno) references t_class(classno)
);