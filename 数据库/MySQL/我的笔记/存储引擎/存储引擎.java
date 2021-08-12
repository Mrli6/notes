8���洢���棨�˽����ݣ�

8.1��ʲô�Ǵ洢���棬��ʲô���أ�
	�洢������MySQL�����е�һ������������ݿ���û�С���Oracle���У����ǲ���������֣�
	�洢����������ָ߶˴����ϵ��Ρ�
	ʵ���ϴ洢������һ����洢/��֯���ݵķ�ʽ��
	��ͬ�Ĵ洢���棬��洢���ݵķ�ʽ��ͬ��

8.2����ô�������/ָ�����洢���桱�أ�
	show create table t_student;

	�����ڽ����ʱ�����ָ���洢���档
	CREATE TABLE `t_student` (
	  `no` int(11) NOT NULL AUTO_INCREMENT,
	  `name` varchar(255) DEFAULT NULL,
	  `cno` int(11) DEFAULT NULL,
	  PRIMARY KEY (`no`),
	  KEY `cno` (`cno`),
	  CONSTRAINT `t_student_ibfk_1` FOREIGN KEY (`cno`) REFERENCES `t_class` (`classno`)
	) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8

	�ڽ����ʱ����������С���ŵ�")"���ұ�ʹ�ã�
		ENGINE��ָ���洢���档
		CHARSET��ָ�����ű���ַ����뷽ʽ��
	
		���ۣ�
			mysqlĬ�ϵĴ洢�����ǣ�InnoDB
			mysqlĬ�ϵ��ַ����뷽ʽ�ǣ�utf8
	
	����ʱָ���洢���棬�Լ��ַ����뷽ʽ��
	create table t_product(
		id int primary key,
		name varchar(255)
	)engine=InnoDB default charset=gbk;

8.3����ô�鿴mysql֧����Щ�洢�����أ�

mysql> select version();
+-----------+
| version() |
+-----------+
| 5.5.36    |
+-----------+

��� show engines \G

*************************** 1. row ***************************
      Engine: FEDERATED
     Support: NO
     Comment: Federated MySQL storage engine
Transactions: NULL
          XA: NULL
  Savepoints: NULL
*************************** 2. row ***************************
      Engine: MRG_MYISAM
     Support: YES
     Comment: Collection of identical MyISAM tables
Transactions: NO
          XA: NO
  Savepoints: NO
*************************** 3. row ***************************
      Engine: MyISAM
     Support: YES
     Comment: MyISAM storage engine
Transactions: NO
          XA: NO
  Savepoints: NO
*************************** 4. row ***************************
      Engine: BLACKHOLE
     Support: YES
     Comment: /dev/null storage engine (anything you write to it disappears
Transactions: NO
          XA: NO
  Savepoints: NO
*************************** 5. row ***************************
      Engine: CSV
     Support: YES
     Comment: CSV storage engine
Transactions: NO
          XA: NO
  Savepoints: NO
*************************** 6. row ***************************
      Engine: MEMORY
     Support: YES
     Comment: Hash based, stored in memory, useful for temporary tables
Transactions: NO
          XA: NO
  Savepoints: NO
*************************** 7. row ***************************
      Engine: ARCHIVE
     Support: YES
     Comment: Archive storage engine
Transactions: NO
          XA: NO
  Savepoints: NO
*************************** 8. row ***************************
      Engine: InnoDB
     Support: DEFAULT
     Comment: Supports transactions, row-level locking, and foreign keys
Transactions: YES
          XA: YES
  Savepoints: YES
*************************** 9. row ***************************
      Engine: PERFORMANCE_SCHEMA
     Support: YES
     Comment: Performance Schema
Transactions: NO
          XA: NO
  Savepoints: NO

mysql֧�־Ŵ�洢���棬��ǰ5.5.36֧��8�����汾��֧ͬ�������ͬ��

8.4������mysql���õĴ洢�������һ��

MyISAM�洢���棿
	������ı��������������
		ʹ�������ļ���ʾÿ����
			��ʽ�ļ� �� �洢��ṹ�Ķ��壨mytable.frm��
			�����ļ� �� �洢���е����ݣ�mytable.MYD��
			�����ļ� �� �洢����������mytable.MYI����������һ�����Ŀ¼����Сɨ�跶Χ����߲�ѯЧ�ʵ�һ�ֻ��ơ�
		�ɱ�ת��Ϊѹ����ֻ��������ʡ�ռ�

		��ʾһ�£�
			����һ�ű���˵��ֻҪ��������
			���߼���uniqueԼ�����ֶ��ϻ��Զ�����������

		MyISAM�洢�����ص㣺
			�ɱ�ת��Ϊѹ����ֻ��������ʡ�ռ�
			�������ִ洢��������ƣ�������
		
		MyISAM��֧��������ƣ���ȫ�Ե͡�

InnoDB�洢���棿
	����mysqlĬ�ϵĴ洢���棬ͬʱҲ��һ���������Ĵ洢���档
	InnoDB֧������֧�����ݿ�������Զ��ָ����ơ�
	InnoDB�洢��������Ҫ���ص��ǣ��ǳ���ȫ��

	������ı����������Ҫ������
		�C ÿ�� InnoDB �������ݿ�Ŀ¼����.frm ��ʽ�ļ���ʾ
		�C InnoDB ��ռ� tablespace �����ڴ洢������ݣ���ռ���һ���߼����ơ���ռ�洢����+��������

		�C �ṩһ��������¼�����Ի����־�ļ�
		�C �� COMMIT(�ύ)��SAVEPOINT ��ROLLBACK(�ع�)֧��������
		�C �ṩȫ ACID ����
		�C �� MySQL �������������ṩ�Զ��ָ�
		�C ��汾��MVCC�����м�����
		�C ֧����������õ������ԣ���������ɾ���͸���
	
	InnoDB�����ص����֧������
		�Ա�֤���ݵİ�ȫ��Ч�ʲ��Ǻܸߣ�����Ҳ����ѹ��������ת��Ϊֻ����
		���ܺܺõĽ�ʡ�洢�ռ䡣

MEMORY�洢���棿
	ʹ�� MEMORY �洢����ı������ݴ洢���ڴ��У����еĳ��ȹ̶���
	�������ص�ʹ�� MEMORY �洢����ǳ��졣

	MEMORY �洢�������ı��������������
		�C �����ݿ�Ŀ¼�ڣ�ÿ�������.frm ��ʽ���ļ���ʾ��
		�C �����ݼ��������洢���ڴ��С���Ŀ�ľ��ǿ죬��ѯ�죡��
		�C �������ơ�
		�C ���ܰ��� TEXT �� BLOB �ֶΡ�

	MEMORY �洢������ǰ����ΪHEAP ���档

	MEMORY�����ŵ㣺��ѯЧ������ߵġ�����Ҫ��Ӳ�̽�����
	MEMORY����ȱ�㣺����ȫ���ػ�֮��������ʧ����Ϊ���ݺ������������ڴ浱�С�