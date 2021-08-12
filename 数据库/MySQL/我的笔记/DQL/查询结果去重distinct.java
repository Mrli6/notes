注意：原表数据不会被修改，只是查询结果去重

关键字 distinct

distinct只能出现在所有字段的最前方，如果出现在两个
字段之前，则两个字段联合起来去重。

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


//统计不同岗位的数量
select
	count(distinct job)
from
	emp;