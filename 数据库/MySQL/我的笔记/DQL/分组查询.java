�����ѯ���ǳ���Ҫ�������*****��
	
	19.1��ʲô�Ƿ����ѯ��
		��ʵ�ʵ�Ӧ���У�������������������Ҫ�Ƚ��з��飬Ȼ���ÿһ������ݽ��в�����
		���ʱ��������Ҫʹ�÷����ѯ����ô���з����ѯ�أ�
			select
				...
			from
				...
			group by
				...
	19.2����֮ǰ�Ĺؼ���ȫ�������һ������һ�����ǵ�ִ��˳��
		select
			...
		from
			...
		where
			...
		group by
			...
		order by
			...
		
		���Ϲؼ��ֵ�˳���ܵߵ�����Ҫ���䡣
		ִ��˳����ʲô��
			1. from
			2. where
			3. group by
			4. select
			5. order by
		
		Ϊʲô���麯������ֱ��ʹ����where���棿
			select ename,sal from emp where sal > min(sal);//����
			��Ϊ���麯����ʹ�õ�ʱ������ȷ���֮�����ʹ�á�
			whereִ�е�ʱ�򣬻�û�з��顣����where���治�ܳ��ַ��麯����

			select sum(sal) from emp; 
			���û�з��飬Ϊɶsum()�����������أ�
				��Ϊselect��group by֮��ִ�С�
	19.3���ҳ�ÿ��������λ�Ĺ��ʺͣ�
	
		ʵ��˼·�����չ�����λ���飬Ȼ��Թ�����͡�
			select 
				job,sum(sal)
			from
				emp
			group by
				job;
			
			+-----------+----------+
			| job       | sum(sal) |
			+-----------+----------+
			| ANALYST   |  6000.00 |
			| CLERK     |  4150.00 |
			| MANAGER   |  8275.00 |
			| PRESIDENT |  5000.00 |
			| SALESMAN  |  5600.00 |
			+-----------+----------+
			�����������ִ��˳��
				�ȴ�emp���в�ѯ���ݡ�
				����job�ֶν��з��顣
				Ȼ���ÿһ������ݽ���sum(sal)
		
		select ename,job,sum(sal) from emp group by job;
		+-------+-----------+----------+
		| ename | job       | sum(sal) |
		+-------+-----------+----------+
		| SCOTT | ANALYST   |  6000.00 |
		| SMITH | CLERK     |  4150.00 |
		| JONES | MANAGER   |  8275.00 |
		| KING  | PRESIDENT |  5000.00 |
		| ALLEN | SALESMAN  |  5600.00 |
		+-------+-----------+----------+
		���������mysql�п���ִ�У����Ǻ������塣
		���������oracle��ִ�б���
		oracle���﷨��mysql���﷨�ϸ񡣣�mysql���﷨�����˵��ɢһЩ����

		�ص���ۣ�
			��һ��select��䵱�У������group by���Ļ���
			select����ֻ�ܸ����μӷ�����ֶΣ��Լ����麯����
			������һ�ɲ��ܸ���

	19.4���ҳ�ÿ�����ŵ����н��
		ʵ��˼·��ʲô��
			���ղ��ű�ŷ��飬��ÿһ������ֵ��

			select�������ename�ֶ�û�����壬����oracle�ᱨ��
			mysql> select ename,deptno,max(sal) from emp group by deptno;
			+-------+--------+----------+
			| ename | deptno | max(sal) |
			+-------+--------+----------+
			| CLARK |     10 |  5000.00 |
			| SMITH |     20 |  3000.00 |
			| ALLEN |     30 |  2850.00 |
			+-------+--------+----------+

			mysql> select deptno,max(sal) from emp group by deptno;
			+--------+----------+
			| deptno | max(sal) |
			+--------+----------+
			|     10 |  5000.00 |
			|     20 |  3000.00 |
			|     30 |  2850.00 |
			+--------+----------+

	19.5���ҳ���ÿ�����ţ���ͬ������λ�������н�ʣ�
		+--------+-----------+---------+--------+
		| ename  | job       | sal     | deptno |
		+--------+-----------+---------+--------+
		| MILLER | CLERK     | 1300.00 |     10 |
		| KING   | PRESIDENT | 5000.00 |     10 |
		| CLARK  | MANAGER   | 2450.00 |     10 |

		| FORD   | ANALYST   | 3000.00 |     20 |
		| ADAMS  | CLERK     | 1100.00 |     20 |
		| SCOTT  | ANALYST   | 3000.00 |     20 |
		| JONES  | MANAGER   | 2975.00 |     20 |
		| SMITH  | CLERK     |  800.00 |     20 |

		| BLAKE  | MANAGER   | 2850.00 |     30 |
		| MARTIN | SALESMAN  | 1250.00 |     30 |
		| ALLEN  | SALESMAN  | 1600.00 |     30 |
		| TURNER | SALESMAN  | 1500.00 |     30 |
		| WARD   | SALESMAN  | 1250.00 |     30 |
		| JAMES  | CLERK     |  950.00 |     30 |
		+--------+-----------+---------+--------+
		���ɣ������ֶ����ϳ�1���ֶο����������ֶ����Ϸ��飩
		select 
			deptno, job, max(sal)
		from
			emp
		group by
			deptno, job;

		+--------+-----------+----------+
		| deptno | job       | max(sal) |
		+--------+-----------+----------+
		|     10 | CLERK     |  1300.00 |
		|     10 | MANAGER   |  2450.00 |
		|     10 | PRESIDENT |  5000.00 |
		|     20 | ANALYST   |  3000.00 |
		|     20 | CLERK     |  1100.00 |
		|     20 | MANAGER   |  2975.00 |
		|     30 | CLERK     |   950.00 |
		|     30 | MANAGER   |  2850.00 |
		|     30 | SALESMAN  |  1600.00 |
		+--------+-----------+----------+
		
	19.6��ʹ��having���ԶԷ�����֮������ݽ�һ�����ˡ�
	having���ܵ���ʹ�ã�having���ܴ���where��having����
	��group by����ʹ�á�

	�ҳ�ÿ���������н�ʣ�Ҫ����ʾ���н�ʴ���3000�ģ�

		��һ�����ҳ�ÿ���������н��
			���ղ��ű�ŷ��飬��ÿһ�����ֵ��
			select deptno,max(sal) from emp group by deptno;
			+--------+----------+
			| deptno | max(sal) |
			+--------+----------+
			|     10 |  5000.00 |
			|     20 |  3000.00 |
			|     30 |  2850.00 |
			+--------+----------+
		
		�ڶ�����Ҫ����ʾ���н�ʴ���3000
			select 
				deptno,max(sal) 
			from 
				emp 
			group by 
				deptno
			having
				max(sal) > 3000;

			+--------+----------+
			| deptno | max(sal) |
			+--------+----------+
			|     10 |  5000.00 |
			+--------+----------+


			˼��һ�����⣺���ϵ�sql���ִ��Ч���ǲ��ǵͣ�
			�Ƚϵͣ�ʵ���Ͽ����������ǣ��Ƚ�����3000�Ķ��ҳ�����Ȼ���ٷ��顣
			select 
				deptno,max(sal)
			from
				emp
			where
				sal > 3000
			group by
				deptno;
			
			+--------+----------+
			| deptno | max(sal) |
			+--------+----------+
			|     10 |  5000.00 |
			+--------+----------+

			�Ż����ԣ�
				where��having������ѡ��where��whereʵ����ɲ����ˣ���ѡ��
				having��
		
		19.7��whereû�취�ģ�������
			�ҳ�ÿ������ƽ��н�ʣ�Ҫ����ʾƽ��н�ʸ���2500�ġ�

			��һ�����ҳ�ÿ������ƽ��н��
				select deptno,avg(sal) from emp group by deptno;
				+--------+-------------+
				| deptno | avg(sal)    |
				+--------+-------------+
				|     10 | 2916.666667 |
				|     20 | 2175.000000 |
				|     30 | 1566.666667 |
				+--------+-------------+

			�ڶ�����Ҫ����ʾƽ��н�ʸ���2500��
				select 
					deptno,avg(sal) 
				from 
					emp 
				group by 
					deptno
				having
					avg(sal) > 2500;
			
			+--------+-------------+
			| deptno | avg(sal)    |
			+--------+-------------+
			|     10 | 2916.666667 |
			+--------+-------------+
20�����ܽᣨ����Ĳ�ѯѧ���ˣ�
	select 
		...
	from
		...
	where
		...
	group by
		...
	having
		...
	order by
		...
	
	���Ϲؼ���ֻ�ܰ������˳���������ܵߵ���

	ִ��˳��
		1. from
		2. where
		3. group by
		4. having
		5. select
		6. order by
	��ĳ�ű��в�ѯ���ݣ�
	�Ⱦ���where����ɸѡ���м�ֵ�����ݡ�
	����Щ�м�ֵ�����ݽ��з��顣
	����֮�����ʹ��having����ɸѡ��
	select��ѯ������
	������������

	select 
		job,avg(sal) as avgsal
	from 
		emp 
	where 
		job != 'MANAGER'
	group by
		job
	having
		avg(sal) > 1500
	order by
		sal desc;