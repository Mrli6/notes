lower ת��Сд
		mysql> select lower(ename) as ename from emp;
		+--------+
		| ename  |
		+--------+
		| smith  |
		| allen  |
		| ward   |
		| jones  |
		| martin |
		| blake  |
		| clark  |
		| scott  |
		| king   |
		| turner |
		| adams  |
		| james  |
		| ford   |
		| miller |
		+--------+
		14�����룬�����14����������ǵ��д��������ص㡣

	upper ת����д
		mysql> select * from t_student;
		+----------+
		| name     |
		+----------+
		| zhangsan |
		| lisi     |
		| wangwu   |
		| jack_son |
		+----------+

		mysql> select upper(name) as name from t_student;
		+----------+
		| name     |
		+----------+
		| ZHANGSAN |
		| LISI     |
		| WANGWU   |
		| JACK_SON |
		+----------+

	substr ȡ�Ӵ���substr( ����ȡ���ַ���, ��ʼ�±�,��ȡ�ĳ���)��
		select substr(ename, 1, 1) as ename from emp;
		ע�⣺��ʼ�±��1��ʼ��û��0.
		�ҳ�Ա�����ֵ�һ����ĸ��A��Ա����Ϣ��
			��һ�ַ�ʽ��ģ����ѯ
				select ename from emp where ename like 'A%';
			�ڶ��ַ�ʽ��substr����
				select 
					ename 
				from 
					emp 
				where 
					substr(ename,1,1) = 'A';

		����ĸ��д��
			select name from t_student;
			select upper(substr(name,1,1)) from t_student;
			select substr(name,2,length(name) - 1) from t_student;
			select concat(upper(substr(name,1,1)),substr(name,2,length(name) - 1)) as result from t_student;
			+----------+
			| result   |
			+----------+
			| Zhangsan |
			| Lisi     |
			| Wangwu   |
			| Jack_son |
			+----------+
		
	concat���������ַ�����ƴ��
		select concat(empno,ename) from emp;
		+---------------------+
		| concat(empno,ename) |
		+---------------------+
		| 7369SMITH           |
		| 7499ALLEN           |
		| 7521WARD            |
		| 7566JONES           |
		| 7654MARTIN          |
		| 7698BLAKE           |
		| 7782CLARK           |
		| 7788SCOTT           |
		| 7839KING            |
		| 7844TURNER          |
		| 7876ADAMS           |
		| 7900JAMES           |
		| 7902FORD            |
		| 7934MILLER          |
		+---------------------+

	length ȡ����
		select length(ename) enamelength from emp;
		+-------------+
		| enamelength |
		+-------------+
		|           5 |
		|           5 |
		|           4 |
		|           5 |
		|           6 |
		|           5 |
		|           5 |
		|           5 |
		|           4 |
		|           6 |
		|           5 |
		|           5 |
		|           4 |
		|           6 |
		+-------------+

	trim ȥ�ո�
		mysql> select * from emp where ename = '  KING';
		Empty set (0.00 sec)

		mysql> select * from emp where ename = trim('   KING');
		+-------+-------+-----------+------+------------+---------+------+--------+
		| EMPNO | ENAME | JOB       | MGR  | HIREDATE   | SAL     | COMM | DEPTNO |
		+-------+-------+-----------+------+------------+---------+------+--------+
		|  7839 | KING  | PRESIDENT | NULL | 1981-11-17 | 5000.00 | NULL |     10 |
		+-------+-------+-----------+------+------------+---------+------+--------+

	str_to_date ���ַ���ת��������
	date_format ��ʽ������
	format ����ǧ��λ

	case..when..then..when..then..else..end
		��Ա���Ĺ�����λ��MANAGER��ʱ�򣬹����ϵ�10%����������λ��SALESMAN��ʱ�򣬹����ϵ�50%,����������
		��ע�⣺���޸����ݿ⣬ֻ�ǽ���ѯ�����ʾΪ�����ϵ���
		select 
			ename,
			job, 
			sal as oldsal,
			(case job when 'MANAGER' then sal*1.1 when 'SALESMAN' then sal*1.5 else sal end) as newsal 
		from 
			emp;
		
		+--------+-----------+---------+---------+
		| ename  | job       | oldsal  | newsal  |
		+--------+-----------+---------+---------+
		| SMITH  | CLERK     |  800.00 |  800.00 |
		| ALLEN  | SALESMAN  | 1600.00 | 2400.00 |
		| WARD   | SALESMAN  | 1250.00 | 1875.00 |
		| JONES  | MANAGER   | 2975.00 | 3272.50 |
		| MARTIN | SALESMAN  | 1250.00 | 1875.00 |
		| BLAKE  | MANAGER   | 2850.00 | 3135.00 |
		| CLARK  | MANAGER   | 2450.00 | 2695.00 |
		| SCOTT  | ANALYST   | 3000.00 | 3000.00 |
		| KING   | PRESIDENT | 5000.00 | 5000.00 |
		| TURNER | SALESMAN  | 1500.00 | 2250.00 |
		| ADAMS  | CLERK     | 1100.00 | 1100.00 |
		| JAMES  | CLERK     |  950.00 |  950.00 |
		| FORD   | ANALYST   | 3000.00 | 3000.00 |
		| MILLER | CLERK     | 1300.00 | 1300.00 |
		+--------+-----------+---------+---------+

	round ��������
		select �ֶ� from ����;
		select ename from emp;
		select 'abc' from emp; // select����ֱ�Ӹ���������/����ֵ��

		mysql> select 'abc' as bieming from emp;
		+---------+
		| bieming |
		+---------+
		| abc     |
		| abc     |
		| abc     |
		| abc     |
		| abc     |
		| abc     |
		| abc     |
		| abc     |
		| abc     |
		| abc     |
		| abc     |
		| abc     |
		| abc     |
		| abc     |
		+---------+

		mysql> select abc from emp;
		ERROR 1054 (42S22): Unknown column 'abc' in 'field list'
		�����϶�������Ϊ���abc����һ���ֶε����֣�ȥemp������abc�ֶ�ȥ�ˡ�

		select 1000 as num from emp; // 1000 Ҳ�Ǳ�����һ��������/����ֵ��
		+------+
		| num  |
		+------+
		| 1000 |
		| 1000 |
		| 1000 |
		| 1000 |
		| 1000 |
		| 1000 |
		| 1000 |
		| 1000 |
		| 1000 |
		| 1000 |
		| 1000 |
		| 1000 |
		| 1000 |
		| 1000 |
		+------+

		���ۣ�select������Ը�ĳ������ֶ��������Ե�ͬ��������������Ҳ���Ը�������/����ֵ�����ݣ���
		select 21000 as num from dept;
		+-------+
		| num   |
		+-------+
		| 21000 |
		| 21000 |
		| 21000 |
		| 21000 |
		+-------+

		mysql> select round(1236.567, 0) as result from emp; //��������λ��
		+--------+
		| result |
		+--------+
		|   1237 |
		|   1237 |
		|   1237 |
		|   1237 |
		|   1237 |
		|   1237 |
		|   1237 |
		|   1237 |
		|   1237 |
		|   1237 |
		|   1237 |
		|   1237 |
		|   1237 |
		|   1237 |
		+--------+

		select round(1236.567, 1) as result from emp; //����1��С��
		select round(1236.567, 2) as result from emp; //����2��С��
		select round(1236.567, -1) as result from emp; // ������ʮλ��
		+--------+
		| result |
		+--------+
		|   1240 |
		|   1240 |
		|   1240 |
		|   1240 |
		|   1240 |
		|   1240 |
		|   1240 |
		|   1240 |
		|   1240 |
		|   1240 |
		|   1240 |
		|   1240 |
		|   1240 |
		|   1240 |
		+--------+

		select round(1236.567, -2) as result from emp;
		+--------+
		| result |
		+--------+
		|   1200 |
		|   1200 |
		|   1200 |
		|   1200 |
		|   1200 |
		|   1200 |
		|   1200 |
		|   1200 |
		|   1200 |
		|   1200 |
		|   1200 |
		|   1200 |
		|   1200 |
		|   1200 |
		+--------+

	rand() ���������
		mysql> select round(rand()*100,0) from emp; // 100���ڵ������
		+---------------------+
		| round(rand()*100,0) |
		+---------------------+
		|                  76 |
		|                  29 |
		|                  15 |
		|                  88 |
		|                  95 |
		|                   9 |
		|                  63 |
		|                  89 |
		|                  54 |
		|                   3 |
		|                  54 |
		|                  61 |
		|                  42 |
		|                  28 |
		+---------------------+
		
	ifnull ���Խ� null ת����һ������ֵ
		ifnull�ǿմ�������ר�Ŵ���յġ�
		���������ݿ⵱�У�ֻҪ��NULL�������ѧ���㣬���ս������NULL��
		mysql> select ename, sal + comm as salcomm from emp;
		+--------+---------+
		| ename  | salcomm |
		+--------+---------+
		| SMITH  |    NULL |
		| ALLEN  | 1900.00 |
		| WARD   | 1750.00 |
		| JONES  |    NULL |
		| MARTIN | 2650.00 |
		| BLAKE  |    NULL |
		| CLARK  |    NULL |
		| SCOTT  |    NULL |
		| KING   |    NULL |
		| TURNER | 1500.00 |
		| ADAMS  |    NULL |
		| JAMES  |    NULL |
		| FORD   |    NULL |
		| MILLER |    NULL |
		+--------+---------+

		����ÿ��Ա������н��
			��н = (��н + �²���) * 12
			
			select ename, (sal + comm) * 12 as yearsal from emp;
			+--------+----------+
			| ename  | yearsal  |
			+--------+----------+
			| SMITH  |     NULL |
			| ALLEN  | 22800.00 |
			| WARD   | 21000.00 |
			| JONES  |     NULL |
			| MARTIN | 31800.00 |
			| BLAKE  |     NULL |
			| CLARK  |     NULL |
			| SCOTT  |     NULL |
			| KING   |     NULL |
			| TURNER | 18000.00 |
			| ADAMS  |     NULL |
			| JAMES  |     NULL |
			| FORD   |     NULL |
			| MILLER |     NULL |
			+--------+----------+

			ע�⣺NULLֻҪ�������㣬���ս��һ����NULL��Ϊ�˱������������Ҫʹ��ifnull������
			ifnull�����÷���ifnull(����, �������ĸ�ֵ)
				��������ݡ�ΪNULL��ʱ�򣬰�������ݽṹ�����ĸ�ֵ��
			
			����ΪNULL��ʱ�򣬽���������0
				select ename, (sal + ifnull(comm, 0)) * 12 as yearsal from emp;
				+--------+----------+
				| ename  | yearsal  |
				+--------+----------+
				| SMITH  |  9600.00 |
				| ALLEN  | 22800.00 |
				| WARD   | 21000.00 |
				| JONES  | 35700.00 |
				| MARTIN | 31800.00 |
				| BLAKE  | 34200.00 |
				| CLARK  | 29400.00 |
				| SCOTT  | 36000.00 |
				| KING   | 60000.00 |
				| TURNER | 18000.00 |
				| ADAMS  | 13200.00 |
				| JAMES  | 11400.00 |
				| FORD   | 36000.00 |
				| MILLER | 15600.00 |
				+--------+----------+