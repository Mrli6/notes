import java.sql.*;
import java.util.*;

public class Test{
	public static void main(String[] args){
		//ʹ����Դ���������������ļ�
		ResourceBundle bundle = ResourceBundle.getBundle("jdbc");
		String driver = bundle.getString("driver");
		String url = bundle.getString("url");
		String user = bundle.getString("user");
		String password = bundle.getString("password");


		Connection conn = null;
		Statement stmt = null;
		try{
			Class.forName(driver);
			


			conn = DriverManager.getConnection(url,user,password);



			stmt = conn.createStatement();



			String sql = "delete from dept where deptno = 50";
			int count = stmt.executeUpdate(sql);
			System.out.println(count == 1? "����ɹ�":"����ʧ��");

		}catch(SQLException e){
			e.printStackTrace();
		}finally{
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