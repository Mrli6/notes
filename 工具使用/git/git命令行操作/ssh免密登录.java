һ̨���Խ�������һ��ssh��������ssh��pushʱ����Ҫ�����˻�����

1���ص���Ŀ¼
	cd ~

2��ɾȥ��ǰ���ù���ssh(ֻ�и���sshʱ��Ҫ�ⲽ)
	rm -r .ssh/

3������Ŀ¼
	ssh-keygen -t rsa -C github�����˺�

4���س�

5���������ɵ�sshĿ¼
	cd .ssh/

6��cat id_rsa.pub �����������������

7��github ���settings ���SSH and GPG keys

8�����new SSH key �����Ƶ�����ճ���� Key ��

9��Title���д ���Add SSH key

10��git remote add ���� ssh��ַ