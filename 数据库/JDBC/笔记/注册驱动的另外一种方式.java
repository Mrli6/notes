import java.sql.*;

public class Test{
	public static void main(String[] args){
		try{
			//���ã���Ϊ�������ַ������ַ�������д�������ļ���
			//���·�������Ҫ���շ���ֵ����Ϊ����ֻ������������ض���
			Class.forName("com.mysql.jdbc.Driver");
		}catch(SQLException e){
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}
}