import java.sql.*;

public class Test{
	public static void main(String[] args){
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try{
			//注册驱动
			Class.forName("com.mysql.cj.jdbc.Driver");
			//获取连接
			conn = DriverManager.getConnection("jdbc:mysql://10.165.197.158:3306/myfirst?serverTimezone=UTC","root","123");
			//获取数据库操作对象
			stmt = conn.createStatement();
			//执行SQL语句
			String sql = "select ename as a,sal from emp";
			rs = stmt.executeQuery(sql);
			//处理查询结果集
			while(rs.next()){
				/*
				String ename = rs.getStirng(1);
				String sal = rs.getString(2);
				*/
				//按查询后的列名获取数据
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