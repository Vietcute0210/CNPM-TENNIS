package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DAO {
    public static Connection con;

    public DAO() {
        if (con == null) {
            String dbUrl = "jdbc:mysql://localhost:3306/db_tennis_booking?useUnicode=true&characterEncoding=UTF-8";
            String dbClass = "com.mysql.cj.jdbc.Driver";

            try {
                Class.forName(dbClass);
                con = DriverManager.getConnection(dbUrl, "root", "123456");
                System.out.println("Kết nối database thành công!");
            } catch (Exception e) {
                System.out.println("Kết nối database thất bại!");
                e.printStackTrace();
            }
        }
    }
}
