�ύ����commit; ���
�ع�����rollback; ��䣨�ع���Զ����ֻ�ܻع�����һ�ε��ύ�㣡��

�����Ӧ��Ӣ�ﵥ���ǣ�transaction

����һ�£���mysql����Ĭ�ϵ�������Ϊ�������ģ�
	mysqlĬ���������֧���Զ��ύ����ġ����Զ��ύ��
	ʲô���Զ��ύ��
		ÿִ��һ��DML��䣬���ύһ�Σ�

	�����Զ��ύʵ�����ǲ��������ǵĿ���ϰ�ߣ���Ϊһ��ҵ��
	ͨ������Ҫ����DML��乲ִͬ�в�����ɵģ�Ϊ�˱�֤����
	�İ�ȫ������Ҫ��ͬʱ�ɹ�֮�����ύ�����Բ���ִ��һ��
	���ύһ����

��ô��mysql���Զ��ύ���ƹرյ��أ�
	��ִ��������start transaction;

��ʾ����
	---------------------------------�ع�����----------------------------------------
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


	---------------------------------�ύ����----------------------------------------
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