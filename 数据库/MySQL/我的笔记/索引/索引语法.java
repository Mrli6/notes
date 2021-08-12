索引怎么创建？怎么删除？语法是什么？

	创建索引：
		mysql> create index emp_ename_index on emp(ename);
		给emp表的ename字段添加索引，起名：emp_ename_index
	
	删除索引：
		mysql> drop index emp_ename_index on emp;
		将emp表上的emp_ename_index索引对象删除。
	


在mysql当中，怎么查看一个SQL语句是否使用了索引进行检索？

	mysql> explain select * from emp where ename = 'KING';
	+----+-------------+-------+------+---------------+------+---------+------+------+-------------+
	| id | select_type | table | type | possible_keys | key  | key_len | ref  | rows | Extra       |
	+----+-------------+-------+------+---------------+------+---------+------+------+-------------+
	|  1 | SIMPLE      | emp   | ALL  | NULL          | NULL | NULL    | NULL |   14 | Using where |
	+----+-------------+-------+------+---------------+------+---------+------+------+-------------+
	扫描14条记录：说明没有使用索引。type=ALL

	mysql> create index emp_ename_index on emp(ename);

	mysql> explain select * from emp where ename = 'KING';
	+----+-------------+-------+------+-----------------+-----------------+---------+-------+------+-------------+
	| id | select_type | table | type | possible_keys   | key             | key_len | ref   | rows | Extra       |
	+----+-------------+-------+------+-----------------+-----------------+---------+-------+------+-------------+
	|  1 | SIMPLE      | emp   | ref  | emp_ename_index | emp_ename_index | 33      | const |    1 | Using where |
	+----+-------------+-------+------+-----------------+-----------------+---------+-------+------+-------------+