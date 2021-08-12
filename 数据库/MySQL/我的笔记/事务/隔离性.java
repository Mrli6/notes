/*
���뼶����ʾ
https://www.bilibili.com/video/BV1Vy4y1z7EX?p=110
*/
A���Һ�B�����м���һ��ǽ�����ǽ���Ժܺ�Ҳ���Ժܱ������������ĸ��뼶��
���ǽԽ�񣬱�ʾ���뼶���Խ�ߡ�

1�����������֮��ĸ��뼶������Щ�أ�4������

	1.1����δ�ύ��read uncommitted����͵ĸ��뼶�𣩡�û���ύ�Ͷ����ˡ�
		ʲô�Ƕ�δ�ύ��
			����A���Զ�ȡ������Bδ�ύ�����ݡ�
		���ָ��뼶����ڵ�������ǣ�
			�������(Dirty Read)
			���ǳƶ����������ݡ�
		���ָ��뼶��һ�㶼�������ϵģ�����������ݿ���뼶���Ƕ����𲽣�

	1.2�������ύ��read committed���ύ֮����ܶ�����
		ʲô�Ƕ����ύ��
			����Aֻ�ܶ�ȡ������B�ύ֮������ݡ�
		���ָ��뼶������ʲô���⣿
			��������������
		���ָ��뼶�����ʲô���⣿
			�����ظ���ȡ���ݡ�
			ʲô�ǲ����ظ���ȡ�����أ�
				��������֮�󣬵�һ�ζ�����������3������ǰ����û��
				���������ܵڶ����ٶ�ȡ��ʱ�򣬶�����������4����3������4
				��Ϊ�����ظ���ȡ��

		���ָ��뼶���ǱȽ���ʵ�����ݣ�ÿһ�ζ����������Ǿ��Ե���ʵ��
		oracle���ݿ�Ĭ�ϵĸ��뼶���ǣ�read committed

	1.3�����ظ�����repeatable read���ύ֮��Ҳ����������Զ��ȡ�Ķ��Ǹտ�������ʱ�����ݡ�
		ʲô�ǿ��ظ���ȡ��
			����A����֮�󣬲����Ƕ�ã�ÿһ��������A�ж�ȡ��������
			����һ�µġ���ʹ����B�������Ѿ��޸ģ������ύ�ˣ�����A
			��ȡ�������ݻ���û�з����ı䣬����ǿ��ظ�����
		���ظ��������ʲô���⣿
			����˲����ظ���ȡ���ݡ�
		���ظ������ڵ�������ʲô��
			���Ի���ֻ�Ӱ����
			ÿһ�ζ�ȡ�������ݶ��ǻ��󡣲�����ʵ��
		
		�糿9�㿪ʼ����������ֻҪ���񲻽�����������9�㣬���������ݻ���������
		�������Ǽ��󡣲������Ե���ʵ��

		mysql��Ĭ�ϵ�������뼶������������������������������

	1.4�����л�/���л���serializable����ߵĸ��뼶��
		������߸��뼶��Ч����͡���������е����⡣
		���ָ��뼶���ʾ�����Ŷӣ����ܲ�����
		synchronized���߳�ͬ��������ͬ����
		ÿһ�ζ�ȡ�������ݶ�������ʵ�ģ�����Ч������͵ġ�

2����֤���ָ��뼶��

�鿴���뼶��SELECT @@tx_isolation
+-----------------+
| @@tx_isolation  |
+-----------------+
| REPEATABLE-READ |
+-----------------+
mysqlĬ�ϵĸ��뼶��


�����Եı�t_user
��֤��read uncommited
mysql> set global transaction isolation level read uncommitted;
����A												����B
--------------------------------------------------------------------------------
use bjpowernode;
													use bjpowernode;
start transaction;
select * from t_user;
													start transaction;
													insert into t_user values('zhangsan');
select * from t_user;




��֤��read commited
mysql> set global transaction isolation level read committed;
����A												����B
--------------------------------------------------------------------------------
use bjpowernode;
													use bjpowernode;
start transaction;
													start transaction;
select * from t_user;
													insert into t_user values('zhangsan');
select * from t_user;
													commit;
select * from t_user;






��֤��repeatable read
mysql> set global transaction isolation level repeatable read;
����A												����B
--------------------------------------------------------------------------------
use bjpowernode;
													use bjpowernode;
start transaction;
													start transaction;
select * from t_user;
													insert into t_user values('lisi');
													insert into t_user values('wangwu');
													commit;
select * from t_user;





��֤��serializable
mysql> set global transaction isolation level serializable;
����A												����B
--------------------------------------------------------------------------------
use bjpowernode;
													use bjpowernode;
start transaction;
													start transaction;
select * from t_user;
insert into t_user values('abc');
													select * from t_user;
