package Tests;

import Database.DBConnection;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DBConnectionTest {

    @Test
    void testGetInstanceSingleton() throws SQLException {
        // Act
        DBConnection instance1 = DBConnection.getInstance();
        DBConnection instance2 = DBConnection.getInstance();

        // Assert
        assertNotNull(instance1, "Instance1 should not be null");
        assertNotNull(instance2, "Instance2 should not be null");
        assertSame(instance1, instance2, "Both instances should refer to the same object");
    }

    @Test
    void testGetConnection() throws SQLException {
        // Arrange
        DBConnection dbConnection = DBConnection.getInstance();

        // Act
        Connection connection = dbConnection.getConnection();

        // Assert
        assertNotNull(connection, "Connection should not be null");
        assertFalse(connection.isClosed(), "Connection should be open");
    }

    @Test
    void testSingletonIfConnectionClosed() throws SQLException {
        // Arrange
        DBConnection instance1 = DBConnection.getInstance();
        Connection connection = instance1.getConnection();

        // Act
        connection.close(); // Simulate connection closure
        DBConnection instance2 = DBConnection.getInstance();

        // Assert
        assertNotNull(instance2, "New instance should not be null");
        assertNotSame(instance1, instance2, "New instance should be created if connection is closed");
        assertFalse(instance2.getConnection().isClosed(), "New connection should be open");
    }
}