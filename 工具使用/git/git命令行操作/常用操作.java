1�����ؿ��ʼ��
	git init
		��ʼ������ļ���.git�����ļ�����

2������ǩ��
	2.1 ǩ����ʽ
		�û�����...
		Email��ַ��...

	2.2 ���ã����ֲ�ͬ������Ա�����

	2.3 ���
			��Ŀ/�ֿ⼶��(������ .git/config �ļ�)�����ڵ�ǰ���ؿⷶΧ����Ч
				git config user.name ...
				git config user.email ...
			ϵͳ�û�����(������ ~/.gitconfig �ļ�)
				git config --global user.name ...
				git config --global user.email ...


3��״̬�鿴(�鿴���������ݴ���״̬)
	git status

4�����(���������ġ��½�/��ӡ���ӵ��ݴ���)
	git add �ļ���

5���ύ(���ݴ��������ύ�����ؿ�)
	git commit (-m "����ע��") �ļ���

6���鿴��ʷ��¼
	git log (--pretty=oneline/--oneline)
	git reflog

7����¼ǰ������
	��������ֵ������git reset --hard ��ϣ����ֵ
	ʹ��^����(ֻ�ܺ���)��git reset --hard HEAD^
	ʹ��~����(����N��)��git reset --hard HEAD~N

8��ɾ���ļ����һ�
	ɾ����
		������rm �ļ���
			  git add �ļ���
			  git commit �ļ���
	�һأ�
		ǰ�᣺ɾ��ǰ���ļ�����ʱ��״̬�ύ���˱��ؿ�
		������git reset --hard ָ��λ��(��ʷ��¼����λ��)

9���Ƚ��ļ�����
	�ļ��ڹ�������git diff �ļ���
	�ļ����ݴ�����git diff HEAD �ļ���
				  git diff HEAD~ �ļ���(����ʷ��¼�Ƚ�)
	���ļ��Ƚϲ���Ҫ���ļ���


10���鿴�ļ�����
	cat good.txt
	tail -n 3 good.txt(�鿴������)