package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/IndyWinners";
    private static final String DB_USER = "root"; // Replace with your MySQL username
    private static final String DB_PASSWORD = "Purosess#11"; // Replace with your MySQL password
    private static final String DB_DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";

    private static DBConnection instance;
    private static Connection connection;

    /**
     * Private Constructor to force the use of the static method {@code getInstance()}
     * to ensure a single DBConnection object can exist at one time
     * @throws SQLException
     */
    private DBConnection() throws SQLException {
        try {
            Class.forName(DB_DRIVER_CLASS);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Database driver not found", e);
        }
    }

    /** Thread Safe Singleton */
    public static synchronized DBConnection getInstance() throws SQLException {
        if (instance == null || connection.isClosed()) {
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
