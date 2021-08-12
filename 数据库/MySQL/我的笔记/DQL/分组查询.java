分组查询（非常重要：五颗星*****）
	
	19.1、什么是分组查询？
		在实际的应用中，可能有这样的需求，需要先进行分组，然后对每一组的数据进行操作。
		这个时候我们需要使用分组查询，怎么进行分组查询呢？
			select
				...
			from
				...
			group by
				...
	19.2、将之前的关键字全部组合在一起，来看一下他们的执行顺序？
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
		
		以上关键字的顺序不能颠倒，需要记忆。
		执行顺序是什么？
			1. from
			2. where
			3. group by
			4. select
			5. order by
		
		为什么分组函数不能直接使用在where后面？
			select ename,sal from emp where sal > min(sal);//报错。
			因为分组函数在使用的时候必须先分组之后才能使用。
			where执行的时候，还没有分组。所以where后面不能出现分组函数。

			select sum(sal) from emp; 
			这个没有分组，为啥sum()函数可以用呢？
				因为select在group by之后执行。
	19.3、找出每个工作岗位的工资和？
	
		实现思路：按照工作岗位分组，然后对工资求和。
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
			以上这个语句的执行顺序？
				先从emp表中查询数据。
				根据job字段进行分组。
				然后对每一组的数据进行sum(sal)
		
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
		以上语句在mysql中可以执行，但是毫无意义。
		以上语句在oracle中执行报错。
		oracle的语法比mysql的语法严格。（mysql的语法相对来说松散一些！）

		重点结论：
			在一条select语句当中，如果有group by语句的话，
			select后面只能跟：参加分组的字段，以及分组函数。
			其它的一律不能跟。

	19.4、找出每个部门的最高薪资
		实现思路是什么？
			按照部门编号分组，求每一组的最大值。

			select后面添加ename字段没有意义，另外oracle会报错。
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

	19.5、找出“每个部门，不同工作岗位”的最高薪资？
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
		技巧：两个字段联合成1个字段看。（两个字段联合分组）
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
		
	19.6、使用having可以对分完组之后的数据进一步过滤。
	having不能单独使用，having不能代替where，having必须
	和group by联合使用。

	找出每个部门最高薪资，要求显示最高薪资大于3000的？

		第一步：找出每个部门最高薪资
			按照部门编号分组，求每一组最大值。
			select deptno,max(sal) from emp group by deptno;
			+--------+----------+
			| deptno | max(sal) |
			+--------+----------+
			|     10 |  5000.00 |
			|     20 |  3000.00 |
			|     30 |  2850.00 |
			+--------+----------+
		
		第二步：要求显示最高薪资大于3000
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


			思考一个问题：以上的sql语句执行效率是不是低？
			比较低，实际上可以这样考虑：先将大于3000的都找出来，然后再分组。
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

			优化策略：
				where和having，优先选择where，where实在完成不了了，再选择
				having。
		
		19.7、where没办法的？？？？
			找出每个部门平均薪资，要求显示平均薪资高于2500的。

			第一步：找出每个部门平均薪资
				select deptno,avg(sal) from emp group by deptno;
				+--------+-------------+
				| deptno | avg(sal)    |
				+--------+-------------+
				|     10 | 2916.666667 |
				|     20 | 2175.000000 |
				|     30 | 1566.666667 |
				+--------+-------------+

			第二步：要求显示平均薪资高于2500的
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
20、大总结（单表的查询学完了）
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
	
	以上关键字只能按照这个顺序来，不能颠倒。

	执行顺序？
		1. from
		2. where
		3. group by
		4. having
		5. select
		6. order by
	从某张表中查询数据，
	先经过where条件筛选出有价值的数据。
	对这些有价值的数据进行分组。
	分组之后可以使用having继续筛选。
	select查询出来。
	最后排序输出！

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