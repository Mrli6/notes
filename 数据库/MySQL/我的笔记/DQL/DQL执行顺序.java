关于DQL语句的大总结：
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
	limit
		...
	
执行顺序？
	1.from
	2.where
	3.group by
	4.having
	5.select
	6.order by
	7.limit..
	以上关键字只能按照这个顺序来，不能颠倒。

执行顺序？
	1. from
	2. where
	3. group by
	4. having
	5. select
	6. order by
	7. limit
	从某张表中查询数据，
	先经过where条件筛选出有价值的数据。
	对这些有价值的数据进行分组。
	分组之后可以使用having继续筛选。
	select查询出来。
	排序
	分页输出
