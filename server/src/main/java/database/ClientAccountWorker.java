package database;

import exceptions.log_exceptions.EnvNotExistsException;
import exceptions.log_exceptions.LogException;
import logging.LogUtil;


import java.sql.*;

public class ClientAccountWorker {
    private ConnectionPool connectionPool;

    public ClientAccountWorker() {}

    public void init() throws EnvNotExistsException {
        connectionPool = new ConnectionPool();
        connectionPool.init();
    }


    public boolean checkAccount(String username, String password) throws LogException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();

            String sql = "SELECT * FROM client_accounts WHERE username = ? AND password = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            LogUtil.logTrace(e);
            throw new LogException();
        } finally {
            connectionPool.closeMaterials(connection, preparedStatement, resultSet);
        }
    }

    public boolean checkUsername(String username) throws LogException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();
            String sql = "SELECT * FROM client_accounts WHERE username = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);

            resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch(SQLException e) {
            LogUtil.logTrace(e);
            throw new LogException();
        } finally {
            connectionPool.closeMaterials(connection, preparedStatement, resultSet);
        }
    }

    public boolean insertNewAccount(String username, String password) throws LogException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int rowsAffected = 0;

        synchronized (this) {
            try {
                connection = connectionPool.getConnection();
                connection.setAutoCommit(false);

                String sql = "INSERT INTO client_accounts (username, password) VALUES (?, ?)";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                rowsAffected = preparedStatement.executeUpdate();

                connection.commit();
                return rowsAffected > 0;

            } catch (SQLException e) {
                connectionPool.rollbackProcess(connection);
                LogUtil.logTrace(e);
                throw new LogException();
            } finally {
                connectionPool.resetAutoCommit(connection);
                connectionPool.closeMaterials(connection, preparedStatement, null);
            }
        }
    }


}
