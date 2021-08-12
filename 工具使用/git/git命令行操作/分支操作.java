1、创建分支
	git branch 分支名

2、查看分支
	git branch -v

3、切换分支
	git checkout 分支名

4、合并分支
	4.1、切换到接受修改的分支
	4.2、合并
		git merge 分支名

5、合并冲突
	解决：一、删除特殊符号
		  二、git add 文件名
		  三、git commit -m "注释"