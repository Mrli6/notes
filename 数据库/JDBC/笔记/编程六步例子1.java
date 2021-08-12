import java.sql.*;

public class Test{
	public static void main(String[] args){
		Connection conn = null;
		Statement stmt = null;
		try{
			//1、注册驱动
			Driver driver = new com.mysql.cj.jdbc.Driver();
			DriverManager.registerDriver(driver);
			//2、获取连接
			/*
				url：统一资源定位符（网络中某个资源的绝对路径）
				https://www.biadu.com/	这就是url
				URL包括哪几个部分？
					协议
					IP
					PORT（端口号）
					资源名

				http://182.61.200.3:80/index.html
					http://	通信协议
					182.61.200.3	服务器IP地址
					80	服务器上软件的端口号
					index.html	服务器上某个资源名
			*/
			String url = "jdbc:mysql://10.165.197.158:3306/myfirst?serverTimezone=UTC";
			String user = "root";
			String password = "123";
			conn = DriverManager.getConnection(url,user,password);
			//3、获取数据库操作对象
			stmt = conn.createStatement();

			//4、执行sql
			//	 语句中不需要提供分号;
			String sql = "insert into dept(deptno,dname,loc) values(50,'人事部','北京')";
			//	 执行DML语句（insert delete update）
			//	 返回值是“影响数据库中的记录条数”
			int count = stmt.executeUpdate(sql);
			System.out.println(count == 1? "保存成功":"保存失败");

			//5、处理查询结果集

		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			//6、释放资源
			//	 为了保证资源一定释放，在finally语句中关闭资源
			//	 并且从小到大依次关闭
			//	 注意分别对其try...catch
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