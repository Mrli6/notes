1、本地库初始化
	git init
		初始化后的文件在.git隐藏文件夹内

2、设置签名
	2.1 签名形式
		用户名：...
		Email地址：...

	2.2 作用：区分不同开发人员的身份

	2.3 命令：
			项目/仓库级别(保存在 .git/config 文件)：仅在当前本地库范围内有效
				git config user.name ...
				git config user.email ...
			系统用户级别(保存在 ~/.gitconfig 文件)
				git config --global user.name ...
				git config --global user.email ...


3、状态查看(查看工作区、暂存区状态)
	git status

4、添加(将工作区的“新建/添加”添加到暂存区)
	git add 文件名

5、提交(将暂存区内容提交到本地库)
	git commit (-m "这是注释") 文件名

6、查看历史记录
	git log (--pretty=oneline/--oneline)
	git reflog

7、记录前进后退
	基于索引值操作：git reset --hard 哈希索引值
	使用^符号(只能后退)：git reset --hard HEAD^
	使用~符号(后退N步)：git reset --hard HEAD~N

8、删除文件并找回
	删除：
		操作：rm 文件名
			  git add 文件名
			  git commit 文件名
	找回：
		前提：删除前，文件存在时的状态提交到了本地库
		操作：git reset --hard 指针位置(历史记录或当期位置)

9、比较文件差异
	文件在工作区：git diff 文件名
	文件在暂存区：git diff HEAD 文件名
				  git diff HEAD~ 文件名(与历史记录比较)
	多文件比较不需要加文件名


10、查看文件内容
	cat good.txt
	tail -n 3 good.txt(查看后三行)