like 
	��Ϊģ����ѯ��֧��%���»���ƥ��
	%ƥ���������ַ�
	�»��ߣ�����һ���ַ���
	��%��һ������ķ��ţ�_ Ҳ��һ��������ţ�

	�ҳ������к���O�ģ�
	mysql> select ename from emp where ename like '%O%';
	+-------+
	| ename |
	+-------+
	| JONES |
	| SCOTT |
	| FORD  |
	+-------+

	�ҳ�������T��β�ģ�
		select ename from emp where ename like '%T';
		
	�ҳ�������K��ʼ�ģ�
		select ename from emp where ename like 'K%';

	�ҳ��ڶ�����ÿ��A�ģ�
		select ename from emp where ename like '_A%';
	
	�ҳ���������ĸ��R�ģ�
		select ename from emp where ename like '__R%';
	
	t_studentѧ����
	name�ֶ�
	----------------------
	zhangsan
	lisi
	wangwu
	zhaoliu
	jack_son

	�ҳ��������С�_���ģ�
		select name from t_student where name like '%_%'; //�������С�

		mysql> select name from t_student where name like '%\_%'; // \ת���ַ���
		+----------+
		| name     |
		+----------+
		| jack_son |
		+----------+
