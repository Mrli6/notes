str_to_date�����ַ���varchar����ת����date����
date_format����date����ת���ɾ���һ����ʽ��varchar�ַ�������

str_to_date������ô�ã�
		str_to_date('01-10-1990','%d-%m-%Y')

		insert into t_user(id,name,birth) values(1, 'zhangsan', str_to_date('01-10-1990','%d-%m-%Y'));

		ͨ��ʹ���ڲ���insert���棬��Ϊ�����ʱ����Ҫһ���������͵����ݣ�
		��Ҫͨ���ú������ַ���ת����date��
		
		������ṩ�������ַ����������ʽ��str_to_date�����Ͳ���Ҫ�ˣ�����
			%Y-%m-%d
		insert into t_user(id,name,birth) values(2, 'lisi', '1990-10-01');


date_format������ô�ã�
		date_format(������������, '���ڸ�ʽ')
		�������ͨ��ʹ���ڲ�ѯ���ڷ��档����չʾ�����ڸ�ʽ��
	
		select id,name,date_format(birth, '%m/%d/%Y') as birth from t_user;

	mysql> select id,name,birth from t_user;
	+------+----------+------------+
	| id   | name     | birth      |
	+------+----------+------------+
	|    1 | zhangsan | 1990-10-01 |
	|    2 | lisi     | 1990-10-01 |
	+------+----------+------------+
	���ϵ�SQL���ʵ�����ǽ�����Ĭ�ϵ����ڸ�ʽ����
	�Զ������ݿ��е�date����ת����varchar���͡�
	���Ҳ��õĸ�ʽ��mysqlĬ�ϵ����ڸ�ʽ��'%Y-%m-%d'