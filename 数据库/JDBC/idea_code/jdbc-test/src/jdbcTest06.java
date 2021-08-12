import java.sql.*;

public class jdbcTest06 {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = tools.toolDriver();

            String sql = "select ename,job from emp where job like ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,"%m%");

            rs = ps.executeQuery();

            while(rs.next()){
                System.out.print(rs.getString("ename"));
                System.out.print("\t");
                System.out.println(rs.getString("job"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            tools.toolClose(conn,ps,rs);
        }
    }

}

class tools{
    private tools(){}

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection toolDriver() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/myfirst?serverTimezone=UTC","root","123");
    }

    public static void toolClose(Connection conn, PreparedStatement ps, ResultSet rs){
        try {
            if(rs != null){
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if(ps != null){
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if(conn != null){
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}