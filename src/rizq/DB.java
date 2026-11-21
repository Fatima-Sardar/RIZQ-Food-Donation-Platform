package rizq;
import java.sql.*;
public class DB {
    private static final String URL = System.getenv().getOrDefault("RIZQ_DB_URL",
        "jdbc:mysql://localhost:3306/rizq_db?serverTimezone=UTC");
    private static final String USER = System.getenv().getOrDefault("RIZQ_DB_USER", "root");
    private static final String PASS = System.getenv().getOrDefault("RIZQ_DB_PASSWORD", "");
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
