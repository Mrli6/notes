union�ϲ���ѯ�����

��������ѯ������λ��MANAGER��SALESMAN��Ա����
	select ename,job from emp where job = 'MANAGER' or job = 'SALESMAN';
	select ename,job from emp where job in('MANAGER','SALESMAN');
	+--------+----------+
	| ename  | job      |
	+--------+----------+
	| ALLEN  | SALESMAN |
	| WARD   | SALESMAN |
	| JONES  | MANAGER  |
	| MARTIN | SALESMAN |
	| BLAKE  | MANAGER  |
	| CLARK  | MANAGER  |
	| TURNER | SALESMAN |
	+--------+----------+
	
	select ename,job from emp where job = 'MANAGER'
	union
	select ename,job from emp where job = 'SALESMAN';
	
	+--------+----------+
	| ename  | job      |
	+--------+----------+
	| JONES  | MANAGER  |
	| BLAKE  | MANAGER  |
	| CLARK  | MANAGER  |
	| ALLEN  | SALESMAN |
	| WARD   | SALESMAN |
	| MARTIN | SALESMAN |
	| TURNER | SALESMAN |
	+--------+----------+

	union��Ч��Ҫ��һЩ�����ڱ�������˵��ÿ����һ���±�
	��ƥ��Ĵ�������ѿ��������ɱ��ķ�������
	����union���Լ���ƥ��Ĵ������ڼ���ƥ�����������£�
	��������������������ƴ�ӡ�

	a ���� b ���� c
	a 10����¼
	b 10����¼
	c 10����¼
	ƥ������ǣ�1000

	a ���� bһ�������10 * 10 --> 100��
	a ���� cһ�������10 * 10 --> 100��
	ʹ��union�Ļ��ǣ�100�� + 100�� = 200�Ρ���union�ѳ˷�����˼ӷ����㣩

union��ʹ�õ�ʱ����ע��������

	//����ģ�union�ڽ��н�����ϲ���ʱ��Ҫ�������������������ͬ��
	select ename,job from emp where job = 'MANAGER'
	union
	select ename from emp where job = 'SALESMAN';

	// MYSQL���ԣ�oracle�﷨�ϸ� �������ԣ�����Ҫ�󣺽�����ϲ�ʱ�к��е���������ҲҪһ�¡�
	select ename,job from emp where job = 'MANAGER'
	union
	select ename,sal from emp where job = 'SALESMAN';
	+--------+---------+
	| ename  | job     |
	+--------+---------+
	| JONES  | MANAGER |
	| BLAKE  | MANAGER |
	| CLARK  | MANAGER |
	| ALLEN  | 1600    |
	| WARD   | 1250    |
	| MARTIN | 1250    |
	| TURNER | 1500    |
	+--------+---------+