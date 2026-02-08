package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

<<<<<<< HEAD
public class DBConnection {

    private final String URL = "jdbc:mysql://localhost:3306/fintokhrej";
    private final String USER = "root";
    private final String PASSWORD = "";

    private Connection connection;

    public DBConnection() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to the database");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public Connection getConnection() {
=======
public final class DBConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/fintokhrej?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection connection;

    private DBConnection() {}

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("✅ DB Connected");
            }
        } catch (Exception e) {
            System.err.println("❌ DB Error: " + e.getMessage());
        }
>>>>>>> db5ee53 (Template)
        return connection;
    }
}