1、什么是视图？
	view:站在不同的角度去看待同一份数据。

2、怎么创建视图对象？怎么删除视图对象？

	表复制：
	mysql> create table dept2 as select * from dept;

	dept2表中的数据：
	mysql> select * from dept2;
	+--------+------------+----------+
	| DEPTNO | DNAME      | LOC      |
	+--------+------------+----------+
	|     10 | ACCOUNTING | NEW YORK |
	|     20 | RESEARCH   | DALLAS   |
	|     30 | SALES      | CHICAGO  |
	|     40 | OPERATIONS | BOSTON   |
	+--------+------------+----------+

	创建视图对象：
		create view dept2_view as select * from dept2;
	
	删除视图对象：
		drop view dept2_view;
	
	注意：只有DQL语句才能以view的形式创建。
		create view view_name as 这里的语句必须是DQL语句;

3、用视图做什么？

	我们可以面向视图对象进行增删改查，对视图对象的增删改查，会导致
	原表被操作！（视图的特点：通过对视图的操作，会影响到原表数据。）

	//面向视图查询
	select * from dept2_view; 

	// 面向视图插入
	insert into dept2_view(deptno,dname,loc) values(60,'SALES', 'BEIJING');

	// 查询原表数据
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

	// 面向视图删除
	mysql> delete from dept2_view;

	// 查询原表数据
	mysql> select * from dept2;
	Empty set (0.00 sec)
	

	// 创建视图对象
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

	// 查询视图对象
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

	// 面向视图更新
	update emp_dept_view set sal = 1000 where dname = 'ACCOUNTING';

	// 原表数据被更新
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

4、视图对象在实际开发中到底有什么用？《方便，简化开发，利于维护》

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
	
	
	假设有一条非常复杂的SQL语句，而这条SQL语句需要在不同的位置上反复使用。
	每一次使用这个sql语句的时候都需要重新编写，很长，很麻烦，怎么办？
		可以把这条复杂的SQL语句以视图对象的形式新建。
		在需要编写这条SQL语句的位置直接使用视图对象，可以大大简化开发。
		并且利于后期的维护，因为修改的时候也只需要修改一个位置就行，只需要
		修改视图对象所映射的SQL语句。
	
	我们以后面向视图开发的时候，使用视图的时候可以像使用table一样。
	可以对视图进行增删改查等操作。视图不是在内存当中，视图对象也是
	存储在硬盘上的，不会消失。

	再提醒一下：
		视图对应的语句只能是DQL语句。
		但是视图对象创建完成之后，可以对视图进行增删改查等操作。

	小插曲：
		增删改查，又叫做：CRUD。
		CRUD是在公司中程序员之间沟通的术语。一般我们很少说增删改查。
		一般都说CRUD。

		C:Create（增）
		R:Retrive（查：检索）
		U:Update（改）
		D:Delete（删）