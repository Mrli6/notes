1��limit���ã�
	����ѯ�������һ����ȡ������ͨ��ʹ���ڷ�ҳ��ѯ���С�
	��ҳ��������Ϊ������û������飬��Ϊһ��ȫ�����������
	�û�����
	����һҳһҳ��ҳ����

2��limit��ô���أ�

	�����÷���limit startIndex, length
		startIndex����ʼ�±꣬length�ǳ��ȡ�
		��ʼ�±��0��ʼ��

	ȱʡ�÷���limit 5; ����ȡǰ5.

	����н�ʽ���ȡ��������ǰ5����Ա����
	select 
		ename,sal
	from
		emp
	order by 
		sal desc
	limit 5; //ȡǰ5

	select 
		ename,sal
	from
		emp
	order by 
		sal desc
	limit 0,5;