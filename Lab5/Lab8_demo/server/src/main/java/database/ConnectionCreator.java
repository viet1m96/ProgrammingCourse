package database;

import config.AppConfig;
import exceptions.log_exceptions.EnvNotExistsException;
import exceptions.log_exceptions.LogException;
import logging.LogUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The `ConnectionCreator` class is responsible for creating and managing database connections.
 * It provides methods for obtaining a connection, closing connections, and handling transactions.
 */
public class ConnectionCreator {
    /**
     * Constructs a `ConnectionCreator` object.
     */
    public ConnectionCreator() {
    }

    private String url;
    private String username;
    private String password;

    /**
     * Initializes the connection parameters from the application configuration.
     *
     * @throws EnvNotExistsException If any of the required environment variables are not set.
     */
    public void init() throws EnvNotExistsException {
        url = AppConfig.getDatabase_url();
        username = AppConfig.getDatabase_user();
        password = AppConfig.getDatabase_pass();
    }

    /**
     * Obtains a database connection.
     *
     * @return A `Connection` object representing the database connection.
     * @throws SQLException If a database access error occurs.
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    /**
     * Closes a database connection.
     *
     * @param connection The `Connection` object to close.
     * @throws LogException If an error occurs while closing the connection.
     */
    public void closeMaterial(Connection connection) throws LogException {
        try {
            if (connection != null) connection.close();
        } catch (SQLException e) {
            LogUtil.logTrace(e);
            throw new LogException("Close connection failed");
        }
    }

    /**
     * Rolls back a database transaction.
     *
     * @param connection The `Connection` object for which to rollback the transaction.
     * @throws LogException If an error occurs while rolling back the transaction.
     */
    public void rollbackProcess(Connection connection) throws LogException {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                LogUtil.logTrace(rollbackEx);
                throw new LogException("Rollback failed");
            }
        }
    }

    /**
     * Resets the auto-commit mode of a database connection.
     *
     * @param connection The `Connection` object for which to reset the auto-commit mode.
     * @throws LogException If an error occurs while resetting the auto-commit mode.
     */
    public void resetAutoCommit(Connection connection) throws LogException {
        if (connection != null) {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException resetEx) {
                LogUtil.logTrace(resetEx);
                throw new LogException("Reset auto-commit failed");
            }
        }
    }
}
