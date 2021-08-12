/*
�������ˣ���һ�����͸�������֮�⣬�������������з��ࣿ
	��Ȼ����������ֵ��һ����Ȼ������ҵ��û��ϵ��
	ҵ������������ֵ��ҵ����ܹ��������������п��˺�������ֵ�������ҵ��������

ע�⣺һ�ű�����Լ��ֻ�����1����������ֻ����1������

����ֵ����ʹ�ã�
	int
	bigint
	char
	�����͡�

������ʹ�ã�varchar��������������ֵһ�㶼�����֣�
			һ�㶼�Ƕ����ģ�
*/
����Լ����������
		����Լ��������һ��Լ����
		�����ֶΣ����ֶ������������Լ�����������ֶν����������ֶ�
		����ֵ�������ֶ��е�ÿһ��ֵ������������ֵ��
	
ʲô����������ɶ�ã�
	����ֵ��ÿһ�м�¼��Ψһ��ʶ��
	����ֵ��ÿһ�м�¼�����֤�ţ�����

��ס���κ�һ�ű�Ӧ����������û������������Ч����

������������not null + unique������ֵ������NULL��ͬʱҲ�����ظ�����


��ô��һ�ű��������Լ���أ�
	drop table if exists t_vip;
	// 1���ֶ�����������������һ����
	create table t_vip(
		id int primary key,  //�м�Լ��
		name varchar(255)
	);
	insert into t_vip(id,name) values(1,'zhangsan');
	insert into t_vip(id,name) values(2,'lisi');

	//���󣺲����ظ�
	insert into t_vip(id,name) values(2,'wangwu');
	ERROR 1062 (23000): Duplicate entry '2' for key 'PRIMARY'

	//���󣺲���ΪNULL
	insert into t_vip(name) values('zhaoliu');
	ERROR 1364 (HY000): Field 'id' doesn't have a default value

�����������������ʹ�ñ�Լ����
	drop table if exists t_vip;
	create table t_vip(
		id int,
		name varchar(255),
		primary key(id)  // ��Լ��
	);
	insert into t_vip(id,name) values(1,'zhangsan');

	//����
	insert into t_vip(id,name) values(1,'lisi');
	ERROR 1062 (23000): Duplicate entry '1' for key 'PRIMARY'

��Լ����Ҫ�Ǹ�����ֶ������������Լ����
	drop table if exists t_vip;
	// id��name��������������������������������
	create table t_vip(
		id int,
		name varchar(255),
		email varchar(255),
		primary key(id,name)
	);
	insert into t_vip(id,name,email) values(1,'zhangsan','zhangsan@123.com');
	insert into t_vip(id,name,email) values(1,'lisi','lisi@123.com');

	//���󣺲����ظ�
	insert into t_vip(id,name,email) values(1,'lisi','lisi@123.com');
	ERROR 1062 (23000): Duplicate entry '1-lisi' for key 'PRIMARY'

	��ʵ�ʿ����в�����ʹ�ã���������������ʹ�õ�һ������
	��Ϊ����ֵ���ڵ�����������м�¼�����֤�ţ�ֻҪ����ﵽ���ɣ���һ��������������
	���������Ƚϸ��ӣ�������ʹ�ã�����

һ����������Լ���ܼ�������
	drop table if exists t_vip;
	create table t_vip(
		id int primary key,
		name varchar(255) primary key
	);
	ERROR 1068 (42000): Multiple primary key defined
	���ۣ�һ�ű�����Լ��ֻ�����1����������ֻ����1������
	
����ֵ����ʹ�ã�
	int
	bigint
	char
	�����͡�

	������ʹ�ã�varchar��������������ֵһ�㶼�����֣�һ�㶼�Ƕ����ģ�

�������ˣ���һ�����͸�������֮�⣬�������������з��ࣿ
	��Ȼ����������ֵ��һ����Ȼ������ҵ��û��ϵ��
	ҵ������������ֵ��ҵ����ܹ��������������п��˺�������ֵ�������ҵ��������

	��ʵ�ʿ�����ʹ��ҵ�������࣬����ʹ����Ȼ������һЩ��
		��Ȼ����ʹ�ñȽ϶࣬��Ϊ����ֻҪ�������ظ����У�����Ҫ�����塣
		ҵ���������ã���Ϊ����һ����ҵ��ҹ�����ô��ҵ�����䶯��ʱ��
		���ܻ�Ӱ�쵽����ֵ������ҵ������������ʹ�á�����ʹ����Ȼ������

��mysql���У���һ�ֻ��ƣ����԰��������Զ�ά��һ������ֵ��
	drop table if exists t_vip;
	create table t_vip(
		id int primary key auto_increment, //auto_increment��ʾ��������1��ʼ����1������
		name varchar(255)
	);
	insert into t_vip(name) values('zhangsan');
	insert into t_vip(name) values('zhangsan');
	insert into t_vip(name) values('zhangsan');
	insert into t_vip(name) values('zhangsan');
	insert into t_vip(name) values('zhangsan');
	insert into t_vip(name) values('zhangsan');
	insert into t_vip(name) values('zhangsan');
	insert into t_vip(name) values('zhangsan');
	select * from t_vip;

	+----+----------+
	| id | name     |
	+----+----------+
	|  1 | zhangsan |
	|  2 | zhangsan |
	|  3 | zhangsan |
	|  4 | zhangsan |
	|  5 | zhangsan |
	|  6 | zhangsan |
	|  7 | zhangsan |
	|  8 | zhangsan |
	+----+----------+

