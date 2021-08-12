/*
    使用preparedStatement时，注意占位符不要出现在 字段名和表名 上
*/
import java.sql.*;

public class jdbcTest02 {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            //注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");

            //获取连接
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/myfirst?serverTimezone=UTC","root","123");

            /* 下面的输出结果为三个ename ， 使用preparedStatement时，注意占位符不要出现在 字段名和表名 上
            String sql = "select ? from emp where deptno = 10";
            ps = conn.prepareStatement(sql);
            ps.setString(1,"ename");

            rs = ps.executeQuery();

            while(rs.next()){
                System.out.println(rs.getString("ename"));
            }
            */

            //获取预编译的数据库操作对象
            String sql = "select ename from emp where deptno = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,10);

            //执行sql语句
            rs = ps.executeQuery();

            //处理查询结果集
            while(rs.next()){
                System.out.println(rs.getString("ename"));
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            //释放连接
            if(rs != null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
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
