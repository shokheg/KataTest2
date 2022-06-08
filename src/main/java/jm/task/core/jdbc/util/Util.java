package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/mydbtest";
    private static final String USER = "root";
    private static final String PASS = "90866651";

    public static Connection getConn() throws SQLException, ClassNotFoundException {
        //Class.forName("com.mysql.jdbc.Driver");   //deprecated
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        return conn;

        // реализуйте настройку соеденения с БД
    }
}
