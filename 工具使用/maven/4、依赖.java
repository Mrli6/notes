1��Maven����������Ϣʱ�ᵽ���زֿ��в��ұ�������jar��
	�����Լ�������Maven���̣�ʹ��mvn install���װ��Ϳ��Խ���ֿ�

2�������ķ�Χ
	2.1 compile��Χ����
		���������Ƿ���Ч��  ��Ч
		�Բ��Գ����Ƿ���Ч����Ч
		�Ƿ��������		����
		�Ƿ���벿��		����
		�������ӣ�spring-core

	2.2 test��Χ����
		��Ч
		��Ч
		������
		������
		�������ӣ�junit

	2.3 provided��Χ����
		��Ч
		��Ч
		������
		������
		�������ӣ�servlet-api.jar

3�������Ĵ�����
	test��provideû�д�����

4�������ų�
	<exclusions>
		<exclusion>
			<groupId></groupId>
			<artifactId></artifactId>
		</exclusion>
	</exclusions>

5��ͳһ�汾��
	<properties>
		<name>4.0.0.RELEASE</name>
	</properties>

	<version>${name}</version>