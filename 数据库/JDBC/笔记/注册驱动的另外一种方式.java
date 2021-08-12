import java.sql.*;

public class Test{
	public static void main(String[] args){
		try{
			//常用，因为参数是字符串，字符串可以写到配置文件中
			//以下方法不需要接收返回值，因为我们只想用它的类加载动作
			Class.forName("com.mysql.jdbc.Driver");
		}catch(SQLException e){
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}
}