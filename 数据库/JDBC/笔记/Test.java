import java.sql.*;

public class Test{
	public static void main(String[] args){
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try{
			//ע������
			Class.forName("com.mysql.cj.jdbc.Driver");
			//��ȡ����
			conn = DriverManager.getConnection("jdbc:mysql://10.165.197.158:3306/myfirst?serverTimezone=UTC","root","123");
			//��ȡ���ݿ��������
			stmt = conn.createStatement();
			//ִ��SQL���
			String sql = "select ename as a,sal from emp";
			rs = stmt.executeQuery(sql);
			//�����ѯ�����
			while(rs.next()){
				/*
				String ename = rs.getStirng(1);
				String sal = rs.getString(2);
				*/
				//����ѯ���������ȡ����
				String ename = rs.getString("a");
				double sal = rs.getDouble("sal");
				System.out.println(ename + ", " + sal);
			}

		}catch(SQLException e){
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}finally{
			if(rs != null){
				try{
					rs.close();
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
			if(stmt != null){
				try{
					stmt.close();
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
			if(conn != null){
				try{
					conn.close();
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
		}
	}
}