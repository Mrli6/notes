import java.sql.*;

public class Test{
	public static void main(String[] args){
		Connection conn = null;
		Statement stmt = null;

		try{
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

			String url = "jdbc:mysql://10.165.197.158:3306/myfirst?serverTimezone=UTC";
			String user = "root";
			String password = "123";
			conn = DriverManager.getConnection(url,user,password);
			
			stmt = conn.createStatement();

			String sql = "delete from dept where deptno = 50";
			int count = stmt.executeUpdate(sql);
			System.out.println(count == 1? "³É¹¦":"Ê§°Ü");
		
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