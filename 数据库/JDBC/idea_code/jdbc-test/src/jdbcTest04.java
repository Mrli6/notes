import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class jdbcTest04 {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            //注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");

            //获取连接
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/myfirst?serverTimezone=UTC","root","123");

            //获取预编译数据库操作对象
            String sql = "update dept set loc = ? where deptno = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,"beijing");
            ps.setInt(2,50);

            //执行sql
            ps.executeUpdate();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            if(ps != null){
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
