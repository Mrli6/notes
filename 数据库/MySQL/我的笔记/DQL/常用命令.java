��¼��
	mysql -hlocalhost -P3306 -uroot -p����

���ص�¼��
	mysql -uroot -p����

�˳���
	exit

�鿴mysql���ݿ�İ汾�ţ�
	select version();

�鿴��ǰʹ�õ����ĸ����ݿ⣿
	select database();

�鿴���ݿ⣺
	show databases;

�������ݿ⣺
	create database ����;

�鿴���ݿ���ı�
	show tables from mysql;
	-- �˴�mysqlָ�������ݿ�����
	
	����

	use mysql;#ѡ�����ݿ�
	show tables;

�鿴��Ľṹ��
	desc ����;

�鿴���е����ݣ�
	select * from ����;

��ѯһ���ֶΣ�
	select �ֶ��� from ����;

��ѯ����ֶΣ�
	select �ֶ���,�ֶ��� from ����;

��һ���ֶ�ȡ�������鿴��
	select �ֶ��� as ���� from ����;
		ע�⣺�����г��ֿո�������ģ���Ҫ������''

�ֶο���ʹ����ѧ���ʽ��
	select ename,sal*12 from emp;

������ѯ��
	select
		...
	from
		...
	where
		...
	

����
	select
		...
	from
		...
	where
		...
	order by
		...��desc����asc����
	�������ִ��˳��
		��һ����from
		�ڶ�����where
		��������select
		���Ĳ���order by���������ִ�У�