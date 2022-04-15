import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class mysql_use {
    static String dburl = "jdbc:mysql://127.0.0.1:53306?useSSL=false";
    static String jdbc_driver = "com.mysql.jdbc.Driver";
    static String user = "root";
//    static String password = "ysq315766";
    static String password = "315766";

    public static void main(String[] args) {
        try {
            Class.forName(jdbc_driver);
            System.out.println("连接数据库...");
            Connection conn = DriverManager.getConnection(dburl, user, password);
//            String sql = "select * from my_test.person;";
            String sql = "select * from test_docker.activities_info;";
            Statement cur = conn.createStatement();
            ResultSet ret = cur.executeQuery(sql);
            System.out.println(ret);
            while (ret.next()) {
//                String md5 = ret.getString("id");
                String advertiser_id = ret.getString("advertiser_id");
//                String filename = ret.getString("Email");
                String log_time = ret.getString("log_time");
                System.out.println(advertiser_id + log_time+"44444444");
            }
        } catch (Exception se) {
            System.out.println("error info :"+se);
        } finally {
            System.out.println("null");
        }
    }

}
