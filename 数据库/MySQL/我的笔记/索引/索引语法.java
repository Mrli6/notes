������ô��������ôɾ�����﷨��ʲô��

	����������
		mysql> create index emp_ename_index on emp(ename);
		��emp���ename�ֶ����������������emp_ename_index
	
	ɾ��������
		mysql> drop index emp_ename_index on emp;
		��emp���ϵ�emp_ename_index��������ɾ����
	


��mysql���У���ô�鿴һ��SQL����Ƿ�ʹ�����������м�����

	mysql> explain select * from emp where ename = 'KING';
	+----+-------------+-------+------+---------------+------+---------+------+------+-------------+
	| id | select_type | table | type | possible_keys | key  | key_len | ref  | rows | Extra       |
	+----+-------------+-------+------+---------------+------+---------+------+------+-------------+
	|  1 | SIMPLE      | emp   | ALL  | NULL          | NULL | NULL    | NULL |   14 | Using where |
	+----+-------------+-------+------+---------------+------+---------+------+------+-------------+
	ɨ��14����¼��˵��û��ʹ��������type=ALL

	mysql> create index emp_ename_index on emp(ename);

	mysql> explain select * from emp where ename = 'KING';
	+----+-------------+-------+------+-----------------+-----------------+---------+-------+------+-------------+
	| id | select_type | table | type | possible_keys   | key             | key_len | ref   | rows | Extra       |
	+----+-------------+-------+------+-----------------+-----------------+---------+-------+------+-------------+
	|  1 | SIMPLE      | emp   | ref  | emp_ename_index | emp_ename_index | 33      | const |    1 | Using where |
	+----+-------------+-------+------+-----------------+-----------------+---------+-------+------+-------------+