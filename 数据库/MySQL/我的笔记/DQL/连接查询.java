//内连接
------------------------------------------------
select
	a.ename b.ename
from
	emp a
join
	emp b
on
	a.mgr = b.empno;//等值连接
-------------------------------------------------
select 
	e.ename, e.sal, s.grade
from
	emp e
join
	salgrade s
on
	e.sal between s.losal and s.hisal; //非等值连接
---------------------------------------------------
select 
	a.ename as '员工名', b.ename as '领导名'
from
	emp a
join
	emp b
on	//自连接
	a.mgr = b.empno; //员工的领导编号 = 领导的员工编号
----------------------------------------------------

//外连接
--------------------------------------------------
select 
	e.ename,d.dname
from
	emp e 
right join	//右连接
	dept d
on
	e.deptno = d.deptno;
---------------------------------------------------
select 
	e.ename,d.dname
from
	dept d 
left join	//左连接
	emp e
on
	e.deptno = d.deptno;
---------------------------------------------------
(select 
	e.ename,d.dname
from
	dept d 
left join
	emp e
on
	e.deptno = d.deptno)

union//全连接

(select 
	e.ename,d.dname
from
	dept d 
right join
	emp e
on
	e.deptno = d.deptno)