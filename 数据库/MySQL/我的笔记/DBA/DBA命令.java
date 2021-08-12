DBA常用命令？

	重点掌握：
		数据的导入和导出（数据的备份）
		其它命令了解一下即可。（这个培训日志文档留着，以后忘了，可以打开文档复制粘贴。）
	
	数据导出？
		注意：在windows的dos命令窗口中：
			mysqldump bjpowernode>D:\bjpowernode.sql -uroot -p123456
		
		可以导出指定的表吗？
			mysqldump bjpowernode emp>D:\bjpowernode.sql -uroot -p123456

	数据导入？
		注意：需要先登录到mysql数据库服务器上。
		然后创建数据库：create database bjpowernode;
		使用数据库：use bjpowernode
		然后初始化数据库：source D:\bjpowernode.sql
