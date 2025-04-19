package database;

import config.AppConfig;
import exceptions.log_exceptions.EnvNotExistsException;
import exceptions.log_exceptions.LogException;
import logging.LogUtil;

import java.sql.*;

public class ConnectionCreator {
    public ConnectionCreator() {}

    private String url;
    private String username;
    private String password;

    public void init() throws EnvNotExistsException {
        url = AppConfig.getDatabase_url();
        username = AppConfig.getDatabase_user();
        password = AppConfig.getDatabase_pass();
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public void closeMaterial(Connection connection) throws LogException {
        try {
            if(connection != null) connection.close();
        } catch (SQLException e) {
            LogUtil.logTrace(e);
            throw new LogException("Close connection failed");
        }
    }

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
