/*
	��mysql���У����һ���ֶ�ͬʱ��not null��uniqueԼ���Ļ���
	���ֶ��Զ���������ֶΡ���ע�⣺oracle�в�һ������
*/
Ψһ��Լ��uniqueԼ�����ֶβ����ظ������ǿ���ΪNULL��
	drop table if exists t_vip;
	create table t_vip(
		id int,
		name varchar(255) unique,
		email varchar(255)
	);
	insert into t_vip(id,name,email) values(1,'zhangsan','zhangsan@123.com');
	insert into t_vip(id,name,email) values(2,'lisi','lisi@123.com');
	insert into t_vip(id,name,email) values(3,'wangwu','wangwu@123.com');
	select * from t_vip;

	insert into t_vip(id,name,email) values(4,'wangwu','wangwu@sina.com');
	ERROR 1062 (23000): Duplicate entry 'wangwu' for key 'name'

	insert into t_vip(id) values(4);
	insert into t_vip(id) values(5);
	+------+----------+------------------+
	| id   | name     | email            |
	+------+----------+------------------+
	|    1 | zhangsan | zhangsan@123.com |
	|    2 | lisi     | lisi@123.com     |
	|    3 | wangwu   | wangwu@123.com   |
	|    4 | NULL     | NULL             |
	|    5 | NULL     | NULL             |
	+------+----------+------------------+
	name�ֶ���Ȼ��uniqueԼ���ˣ����ǿ���ΪNULL��


������name��email�����ֶ�������������Ψһ�ԣ�������
	drop table if exists t_vip;
	create table t_vip(
		id int,
		name varchar(255) unique,  // Լ��ֱ����ӵ��к���ģ������м�Լ����
		email varchar(255) unique
	);
	���ű����������ǲ����������ϡ������󡱵ġ�
	����������ʾ��name����Ψһ�ԣ�email����Ψһ�ԡ�����Ψһ��

	���������������Ƿ����ҡ������󡱵ġ�
	������������Ϸ�ʽ������Ļ����϶�����ʧ�ܣ���Ϊ'zhangsan'��'zhangsan'�ظ��ˡ�
	insert into t_vip(id,name,email) values(1,'zhangsan','zhangsan@123.com');
	insert into t_vip(id,name,email) values(2,'zhangsan','zhangsan@sina.com');

	��ô���������ı����ܷ����������أ�
		drop table if exists t_vip;
		create table t_vip(
			id int,
			name varchar(255),
			email varchar(255),
			unique(name,email) // Լ��û��������еĺ��棬����Լ������Ϊ��Լ����
		);
		insert into t_vip(id,name,email) values(1,'zhangsan','zhangsan@123.com');
		insert into t_vip(id,name,email) values(2,'zhangsan','zhangsan@sina.com');
		select * from t_vip;

		name��email�����ֶ���������Ψһ������
		insert into t_vip(id,name,email) values(3,'zhangsan','zhangsan@sina.com');
		ERROR 1062 (23000): Duplicate entry 'zhangsan-zhangsan@sina.com' for key 'name'

	ʲôʱ��ʹ�ñ�Լ���أ�
		��Ҫ������ֶ������������ĳһ��Լ����ʱ����Ҫʹ�ñ�Լ����

unique ��not null����������
	drop table if exists t_vip;
	create table t_vip(
		id int,
		name varchar(255) not null unique
	);

	mysql> desc t_vip;
	+-------+--------------+------+-----+---------+-------+
	| Field | Type         | Null | Key | Default | Extra |
	+-------+--------------+------+-----+---------+-------+
	| id    | int(11)      | YES  |     | NULL    |       |
	| name  | varchar(255) | NO   | PRI | NULL    |       |
	+-------+--------------+------+-----+---------+-------+

	��mysql���У����һ���ֶ�ͬʱ��not null��uniqueԼ���Ļ���
	���ֶ��Զ���������ֶΡ���ע�⣺oracle�в�һ������

	insert into t_vip(id,name) values(1,'zhangsan');

	insert into t_vip(id,name) values(2,'zhangsan'); //�����ˣ�name�����ظ�

	insert into t_vip(id) values(2); //�����ˣ�name����ΪNULL��