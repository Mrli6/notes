�ǿ�Լ��not nullԼ�����ֶβ���ΪNULL��
	drop table if exists t_vip;
	create table t_vip(
		id int,
		name varchar(255) not null  // not nullֻ���м�Լ����û�б�Լ����
	);
	insert into t_vip(id,name) values(1,'zhangsan');
	insert into t_vip(id,name) values(2,'lisi');

	insert into t_vip(id) values(3);
	ERROR 1364 (HY000): Field 'name' doesn't have a default value