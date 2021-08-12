建立在第一范式的基础之上，
	要求所有非主键字段必须完全依赖主键，不要产生部分依赖。

	学生编号 学生姓名 教师编号 教师姓名
	----------------------------------------------------
	1001			张三		001		王老师
	1002			李四		002		赵老师
	1003			王五		001		王老师
	1001			张三		002		赵老师

	这张表描述了学生和老师的关系：（1个学生可能有多个老师，1个老师有多个学生）
	这是非常典型的：多对多关系！

	分析以上的表是否满足第一范式？
		不满足第一范式。
	
	怎么满足第一范式呢？修改

	学生编号+教师编号(pk)		学生姓名  教师姓名
	----------------------------------------------------
	1001			001				张三			王老师
	1002			002				李四			赵老师
	1003			001				王五			王老师
	1001			002				张三			赵老师

	学生编号 教师编号，两个字段联合做主键，复合主键（PK: 学生编号+教师编号）
	经过修改之后，以上的表满足了第一范式。但是满足第二范式吗？
		不满足，“张三”依赖1001，“王老师”依赖001，显然产生了部分依赖。
		产生部分依赖有什么缺点？
			数据冗余了。空间浪费了。“张三”重复了，“王老师”重复了。
	
	为了让以上的表满足第二范式，你需要这样设计：
		使用三张表来表示多对多的关系！！！！
		学生表
		学生编号(pk)		学生名字
		------------------------------------
		1001					张三
		1002					李四
		1003					王五
		
		教师表
		教师编号(pk)		教师姓名
		--------------------------------------
		001					王老师
		002					赵老师

		学生教师关系表
		id(pk)			学生编号(fk)			教师编号(fk)
		------------------------------------------------------
		1						1001						001
		2						1002						002
		3						1003						001
		4						1001						002
	

	背口诀：
		多对多怎么设计？
			多对多，三张表，关系表两个外键！！！！！！！！！！！！！！！
