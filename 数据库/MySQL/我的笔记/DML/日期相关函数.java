str_to_date：将字符串varchar类型转换成date类型
date_format：将date类型转换成具有一定格式的varchar字符串类型

str_to_date函数怎么用？
		str_to_date('01-10-1990','%d-%m-%Y')

		insert into t_user(id,name,birth) values(1, 'zhangsan', str_to_date('01-10-1990','%d-%m-%Y'));

		通常使用在插入insert方面，因为插入的时候需要一个日期类型的数据，
		需要通过该函数将字符串转换成date。
		
		如果你提供的日期字符串是这个格式，str_to_date函数就不需要了！！！
			%Y-%m-%d
		insert into t_user(id,name,birth) values(2, 'lisi', '1990-10-01');


date_format函数怎么用？
		date_format(日期类型数据, '日期格式')
		这个函数通常使用在查询日期方面。设置展示的日期格式。
	
		select id,name,date_format(birth, '%m/%d/%Y') as birth from t_user;

	mysql> select id,name,birth from t_user;
	+------+----------+------------+
	| id   | name     | birth      |
	+------+----------+------------+
	|    1 | zhangsan | 1990-10-01 |
	|    2 | lisi     | 1990-10-01 |
	+------+----------+------------+
	以上的SQL语句实际上是进行了默认的日期格式化，
	自动将数据库中的date类型转换成varchar类型。
	并且采用的格式是mysql默认的日期格式：'%Y-%m-%d'