package database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import config.AppConfig;
import exceptions.log_exceptions.EnvNotExistsException;
import exceptions.log_exceptions.LogException;
import logging.LogUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionPool {
    private HikariDataSource dataSource;

    public ConnectionPool() {}

    public void init() throws EnvNotExistsException {
        HikariConfig config = new HikariConfig();
        String url = AppConfig.getDatabase_url();
        String username = AppConfig.getDatabase_user();
        String password = AppConfig.getDatabase_pass();
        config.setDriverClassName("org.postgresql.Driver");
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setMaximumPoolSize(3);
        config.setMinimumIdle(1);
        dataSource = new HikariDataSource(config);
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void closeMaterials(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) throws LogException {
        try {
            if (resultSet != null) resultSet.close();
        } catch (SQLException e) {
            LogUtil.logTrace(e);
            throw new LogException();
        }
        try {
            if (preparedStatement != null) preparedStatement.close();
        } catch (SQLException e) {
            LogUtil.logTrace(e);
            throw new LogException();
        }
        try {
            if(connection != null) connection.close();
        } catch (SQLException e) {
            LogUtil.logTrace(e);
            throw new LogException();
        }
    }

    public void rollbackProcess(Connection connection) throws LogException {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                LogUtil.logTrace(rollbackEx);
                throw new LogException();
            }
        }
    }

    public void resetAutoCommit(Connection connection) throws LogException {
        if (connection != null) {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException resetEx) {
                LogUtil.logTrace(resetEx);
                throw new LogException();
            }
        }
    }

}
