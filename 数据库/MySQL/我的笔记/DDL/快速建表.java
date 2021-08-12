//将emp的查询结果当作一张表emp2新建。
create table emp2 as select * from emp;


//此时，mytable表中只有ename字段
create table mytable as select ename from emp;
