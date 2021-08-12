语法格式：

create table 表名(
	字段名1 数据类型 default 默认值, 
	字段名2 数据类型 default 默认值, 
	字段名3 数据类型 default 默认值
	);

表名：建议以t_ 或者 tbl_开始，可读性强。见名知意。
字段名：见名知意。
表名和字段名都属于标识符。

create table t_student(
		no int,
		name varchar(32),
		sex char(1) default 'm',
		age int(3),
		email varchar(255)
	);
