一台电脑仅能设置一个ssh，设置了ssh后，push时不需要输入账户密码

1、回到家目录
	cd ~

2、删去以前设置过的ssh(只有更换ssh时才要这步)
	rm -r .ssh/

3、生成目录
	ssh-keygen -t rsa -C github邮箱账号

4、回车

5、进入生成的ssh目录
	cd .ssh/

6、cat id_rsa.pub 复制里面的所有内容

7、github 点击settings 点击SSH and GPG keys

8、点击new SSH key 将复制的内容粘贴到 Key 中

9、Title随便写 点击Add SSH key

10、git remote add 别名 ssh网址