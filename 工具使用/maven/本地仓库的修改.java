1、Maven的核心程序中仅仅定义了抽象的生命周期，但是具体的工作必须由特定的
   插件来完成，而插件并不包含在Maven的核心程序中

2、当我们执行的Maven命令需要用到某些插件时，Maven核心程序会先到本地仓库找

3、本地仓库的默认位置：家目录\.m2\repository

4、Maven核心程序如果在本地仓库中找不到需要的插件，会自动连外网，到中央仓库下载

5、如果此时无法连接外网，则构建失败

6、修改默认本地仓库的位置可以让Maven核心程序到事先准备好的目录下查找插件
	找到Maven解压目录\conf\settings.xml
	在里面找到 localRepository 标签
	将<localRepository>/path/to/local/repo</localRepository>从注释中取出
	将标签体内容修改为准备好的目录路径