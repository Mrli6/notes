1、给网址取别名
	git remote -v //查看管理的远程库的别名及网址

	git remote add 别名 网址 //添加远程库网址，可以用 别名 代替网址

2、推送操作(本地库上传到远程库)
	git push 网址或别名 分支

3、克隆操作(远程库克隆到本地库、创建远程库网址别名、初始化本地库)
	git clone 网址

4、拉取操作(pull = fetch + merge)
	git fetch 网址或别名 远程分支
	git merge 网址或别名/远程分支

	git pull 网址或别名 远程分支

团队冲突：a和b都修改相同文件，a先push了，那么b就不能push了(报错：
		![rejected] master->master(fetch first))，b需要先pull
		接着处理合并冲突，再push。
	