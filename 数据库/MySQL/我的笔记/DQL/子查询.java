什么是子查询？
	select语句中嵌套select语句，被嵌套的select语句称为子查询。

子查询都可以出现在哪里呢？
	select
		..(select).
	from
		..(select).
	where
		..(select).

/*查工资大于最低工资的，显示0行数据。why？？？
select 
	ename, sal
from
	emp
group by
	ename
having
	sal > min(sal);
*/