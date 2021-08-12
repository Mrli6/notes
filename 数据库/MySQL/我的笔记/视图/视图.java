1��ʲô����ͼ��
	view:վ�ڲ�ͬ�ĽǶ�ȥ����ͬһ�����ݡ�

2����ô������ͼ������ôɾ����ͼ����

	���ƣ�
	mysql> create table dept2 as select * from dept;

	dept2���е����ݣ�
	mysql> select * from dept2;
	+--------+------------+----------+
	| DEPTNO | DNAME      | LOC      |
	+--------+------------+----------+
	|     10 | ACCOUNTING | NEW YORK |
	|     20 | RESEARCH   | DALLAS   |
	|     30 | SALES      | CHICAGO  |
	|     40 | OPERATIONS | BOSTON   |
	+--------+------------+----------+

	������ͼ����
		create view dept2_view as select * from dept2;
	
	ɾ����ͼ����
		drop view dept2_view;
	
	ע�⣺ֻ��DQL��������view����ʽ������
		create view view_name as �������������DQL���;

3������ͼ��ʲô��

	���ǿ���������ͼ���������ɾ�Ĳ飬����ͼ�������ɾ�Ĳ飬�ᵼ��
	ԭ������������ͼ���ص㣺ͨ������ͼ�Ĳ�������Ӱ�쵽ԭ�����ݡ���

	//������ͼ��ѯ
	select * from dept2_view; 

	// ������ͼ����
	insert into dept2_view(deptno,dname,loc) values(60,'SALES', 'BEIJING');

	// ��ѯԭ������
	mysql> select * from dept2;
	+--------+------------+----------+
	| DEPTNO | DNAME      | LOC      |
	+--------+------------+----------+
	|     10 | ACCOUNTING | NEW YORK |
	|     20 | RESEARCH   | DALLAS   |
	|     30 | SALES      | CHICAGO  |
	|     40 | OPERATIONS | BOSTON   |
	|     60 | SALES      | BEIJING  |
	+--------+------------+----------+

	// ������ͼɾ��
	mysql> delete from dept2_view;

	// ��ѯԭ������
	mysql> select * from dept2;
	Empty set (0.00 sec)
	

	// ������ͼ����
	create view 
		emp_dept_view
	as
		select 
			e.ename,e.sal,d.dname
		from
			emp e
		join
			dept d
		on
			e.deptno = d.deptno;

	// ��ѯ��ͼ����
	mysql> select * from emp_dept_view;
	+--------+---------+------------+
	| ename  | sal     | dname      |
	+--------+---------+------------+
	| CLARK  | 2450.00 | ACCOUNTING |
	| KING   | 5000.00 | ACCOUNTING |
	| MILLER | 1300.00 | ACCOUNTING |
	| SMITH  |  800.00 | RESEARCH   |
	| JONES  | 2975.00 | RESEARCH   |
	| SCOTT  | 3000.00 | RESEARCH   |
	| ADAMS  | 1100.00 | RESEARCH   |
	| FORD   | 3000.00 | RESEARCH   |
	| ALLEN  | 1600.00 | SALES      |
	| WARD   | 1250.00 | SALES      |
	| MARTIN | 1250.00 | SALES      |
	| BLAKE  | 2850.00 | SALES      |
	| TURNER | 1500.00 | SALES      |
	| JAMES  |  950.00 | SALES      |
	+--------+---------+------------+

	// ������ͼ����
	update emp_dept_view set sal = 1000 where dname = 'ACCOUNTING';

	// ԭ�����ݱ�����
	mysql> select * from emp;
	+-------+--------+-----------+------+------------+---------+---------+--------+
	| EMPNO | ENAME  | JOB       | MGR  | HIREDATE   | SAL     | COMM    | DEPTNO |
	+-------+--------+-----------+------+------------+---------+---------+--------+
	|  7369 | SMITH  | CLERK     | 7902 | 1980-12-17 |  800.00 |    NULL |     20 |
	|  7499 | ALLEN  | SALESMAN  | 7698 | 1981-02-20 | 1600.00 |  300.00 |     30 |
	|  7521 | WARD   | SALESMAN  | 7698 | 1981-02-22 | 1250.00 |  500.00 |     30 |
	|  7566 | JONES  | MANAGER   | 7839 | 1981-04-02 | 2975.00 |    NULL |     20 |
	|  7654 | MARTIN | SALESMAN  | 7698 | 1981-09-28 | 1250.00 | 1400.00 |     30 |
	|  7698 | BLAKE  | MANAGER   | 7839 | 1981-05-01 | 2850.00 |    NULL |     30 |
	|  7782 | CLARK  | MANAGER   | 7839 | 1981-06-09 | 1000.00 |    NULL |     10 |
	|  7788 | SCOTT  | ANALYST   | 7566 | 1987-04-19 | 3000.00 |    NULL |     20 |
	|  7839 | KING   | PRESIDENT | NULL | 1981-11-17 | 1000.00 |    NULL |     10 |
	|  7844 | TURNER | SALESMAN  | 7698 | 1981-09-08 | 1500.00 |    0.00 |     30 |
	|  7876 | ADAMS  | CLERK     | 7788 | 1987-05-23 | 1100.00 |    NULL |     20 |
	|  7900 | JAMES  | CLERK     | 7698 | 1981-12-03 |  950.00 |    NULL |     30 |
	|  7902 | FORD   | ANALYST   | 7566 | 1981-12-03 | 3000.00 |    NULL |     20 |
	|  7934 | MILLER | CLERK     | 7782 | 1982-01-23 | 1000.00 |    NULL |     10 |
	+-------+--------+-----------+------+------------+---------+---------+--------+

4����ͼ������ʵ�ʿ����е�����ʲô�ã������㣬�򻯿���������ά����

	create view 
		emp_dept_view
	as
		select 
			e.ename,e.sal,d.dname
		from
			emp e
		join
			dept d
		on
			e.deptno = d.deptno;
	
	
	������һ���ǳ����ӵ�SQL��䣬������SQL�����Ҫ�ڲ�ͬ��λ���Ϸ���ʹ�á�
	ÿһ��ʹ�����sql����ʱ����Ҫ���±�д���ܳ������鷳����ô�죿
		���԰��������ӵ�SQL�������ͼ�������ʽ�½���
		����Ҫ��д����SQL����λ��ֱ��ʹ����ͼ���󣬿��Դ��򻯿�����
		�������ں��ڵ�ά������Ϊ�޸ĵ�ʱ��Ҳֻ��Ҫ�޸�һ��λ�þ��У�ֻ��Ҫ
		�޸���ͼ������ӳ���SQL��䡣
	
	�����Ժ�������ͼ������ʱ��ʹ����ͼ��ʱ�������ʹ��tableһ����
	���Զ���ͼ������ɾ�Ĳ�Ȳ�������ͼ�������ڴ浱�У���ͼ����Ҳ��
	�洢��Ӳ���ϵģ�������ʧ��

	������һ�£�
		��ͼ��Ӧ�����ֻ����DQL��䡣
		������ͼ���󴴽����֮�󣬿��Զ���ͼ������ɾ�Ĳ�Ȳ�����

	С������
		��ɾ�Ĳ飬�ֽ�����CRUD��
		CRUD���ڹ�˾�г���Ա֮�乵ͨ�����һ�����Ǻ���˵��ɾ�Ĳ顣
		һ�㶼˵CRUD��

		C:Create������
		R:Retrive���飺������
		U:Update���ģ�
		D:Delete��ɾ��