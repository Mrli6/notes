1、limit作用：
	将查询结果集的一部分取出来。通常使用在分页查询当中。
	分页的作用是为了提高用户的体验，因为一次全部都查出来，
	用户体验差。
	可以一页一页翻页看。

2、limit怎么用呢？

	完整用法：limit startIndex, length
		startIndex是起始下标，length是长度。
		起始下标从0开始。

	缺省用法：limit 5; 这是取前5.

	按照薪资降序，取出排名在前5名的员工？
	select 
		ename,sal
	from
		emp
	order by 
		sal desc
	limit 5; //取前5

	select 
		ename,sal
	from
		emp
	order by 
		sal desc
	limit 0,5;