package top.darkflow.mcctf.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // 静态实例
    private static Connection connection = null;

    // 私有构造函数，防止外部实例化
    private DatabaseConnection() {
    }

    // 获取唯一连接的方法
    public static Connection getConnection() {
        if (connection == null) {
            try {
                String url = "jdbc:sqlite:plugins/MCCTF.db"; // SQLite数据库路径
                connection = DriverManager.getConnection(url);
                System.out.println("Connected to SQLite database.");
            } catch (SQLException e) {
                System.out.println("Failed to connect to the database: " + e.getMessage());
            }
        }
        return connection;
    }

    // 关闭连接的方法
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connection closed.");
            } catch (SQLException e) {
                System.out.println("Failed to close the connection: " + e.getMessage());
            }
        }
    }
}