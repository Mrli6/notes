delete from 表名 where 条件
	注意：没有条件，整张表数据被删除
	delete from t_user//删除整张表的数据


delete删除数据原理？
	删除数据，但该数据在硬盘上的真是存储空间不会被释放
	缺点：删除效率较低
	优点：支持回滚，删除了可以再恢复