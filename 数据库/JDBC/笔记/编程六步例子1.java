import java.sql.*;

public class Test{
	public static void main(String[] args){
		Connection conn = null;
		Statement stmt = null;
		try{
			//1��ע������
			Driver driver = new com.mysql.cj.jdbc.Driver();
			DriverManager.registerDriver(driver);
			//2����ȡ����
			/*
				url��ͳһ��Դ��λ����������ĳ����Դ�ľ���·����
				https://www.biadu.com/	�����url
				URL�����ļ������֣�
					Э��
					IP
					PORT���˿ںţ�
					��Դ��

				http://182.61.200.3:80/index.html
					http://	ͨ��Э��
					182.61.200.3	������IP��ַ
					80	������������Ķ˿ں�
					index.html	��������ĳ����Դ��
			*/
			String url = "jdbc:mysql://10.165.197.158:3306/myfirst?serverTimezone=UTC";
			String user = "root";
			String password = "123";
			conn = DriverManager.getConnection(url,user,password);
			//3����ȡ���ݿ��������
			stmt = conn.createStatement();

			//4��ִ��sql
			//	 ����в���Ҫ�ṩ�ֺ�;
			String sql = "insert into dept(deptno,dname,loc) values(50,'���²�','����')";
			//	 ִ��DML��䣨insert delete update��
			//	 ����ֵ�ǡ�Ӱ�����ݿ��еļ�¼������
			int count = stmt.executeUpdate(sql);
			System.out.println(count == 1? "����ɹ�":"����ʧ��");

			//5�������ѯ�����

		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			//6���ͷ���Դ
			//	 Ϊ�˱�֤��Դһ���ͷţ���finally����йر���Դ
			//	 ���Ҵ�С�������ιر�
			//	 ע��ֱ����try...catch
			try{
				if(stmt != null){
					stmt.close();
				}
			}catch(SQLException e){
				e.printStackTrace();
			}
			try{
				if(conn != null){
					conn.close();
				}
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
}