ע�⣺ԭ�����ݲ��ᱻ�޸ģ�ֻ�ǲ�ѯ���ȥ��

�ؼ��� distinct

distinctֻ�ܳ����������ֶε���ǰ�����������������
�ֶ�֮ǰ���������ֶ���������ȥ�ء�

mysql> select job from emp;
+-----------+
| job       |
+-----------+
| CLERK     |
| SALESMAN  |
| SALESMAN  |
| MANAGER   |
| SALESMAN  |
| MANAGER   |
| MANAGER   |
| ANALYST   |
| PRESIDENT |
| SALESMAN  |
| CLERK     |
| CLERK     |
| ANALYST   |
| CLERK     |
+-----------+


mysql> select distinct job from emp;
+-----------+
| job       |
+-----------+
| CLERK     |
| SALESMAN  |
| MANAGER   |
| ANALYST   |
| PRESIDENT |
+-----------+


//ͳ�Ʋ�ͬ��λ������
select
	count(distinct job)
from
	emp;