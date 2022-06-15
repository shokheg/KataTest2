package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/mydbtest";
    private static final String USER = "root";
    private static final String PASS = "90866651";

    public static Connection getConn() {
        //Class.forName("com.mysql.jdbc.Driver");   //deprecated
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return conn;

        // реализуйте настройку соеденения с БД
    }

    public static void closeConn(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Connection is closed");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }
}
