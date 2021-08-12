import java.sql.*;

public class jdbcTest01 {
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;


        try {
            //注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");

            //获取连接
            conn = DriverManager.getConnection("jdbc:mysql://10.165.197.158:3306/myfirst?serverTimezone=UTC","root","123");

            //获取数据库操作对象
            stmt = conn.createStatement();

            //执行sql
            String sql = "select ename,sal from emp";

            rs = stmt.executeQuery(sql);

            while(rs.next()){
                System.out.print(rs.getString("ename"));
                System.out.println(rs.getString("sal"));
            }


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if(rs != null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(stmt != null){
                try {
                    stmt.close();
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
