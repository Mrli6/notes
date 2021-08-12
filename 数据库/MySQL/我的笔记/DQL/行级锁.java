//悲观锁
select ename,job,sal from emp where job = 'manager' for update;

对于以上语句，只要当前事务没结束，manager的记录被锁定，无法被其他事务操作
