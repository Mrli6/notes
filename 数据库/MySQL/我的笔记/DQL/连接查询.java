//������
------------------------------------------------
select
	a.ename b.ename
from
	emp a
join
	emp b
on
	a.mgr = b.empno;//��ֵ����
-------------------------------------------------
select 
	e.ename, e.sal, s.grade
from
	emp e
join
	salgrade s
on
	e.sal between s.losal and s.hisal; //�ǵ�ֵ����
---------------------------------------------------
select 
	a.ename as 'Ա����', b.ename as '�쵼��'
from
	emp a
join
	emp b
on	//������
	a.mgr = b.empno; //Ա�����쵼��� = �쵼��Ա�����
----------------------------------------------------

//������
--------------------------------------------------
select 
	e.ename,d.dname
from
	emp e 
right join	//������
	dept d
on
	e.deptno = d.deptno;
---------------------------------------------------
select 
	e.ename,d.dname
from
	dept d 
left join	//������
	emp e
on
	e.deptno = d.deptno;
---------------------------------------------------
(select 
	e.ename,d.dname
from
	dept d 
left join
	emp e
on
	e.deptno = d.deptno)

union//ȫ����

(select 
	e.ename,d.dname
from
	dept d 
right join
	emp e
on
	e.deptno = d.deptno)