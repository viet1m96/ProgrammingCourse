package database;

import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.NameTakenException;
import logging.LogUtil;


import java.sql.*;

public class ClientAccountWorker {
    private final ConnectionCreator connectionCreator;
    private final String checkAcc = "SELECT * FROM client_accounts WHERE username = ? AND password = ?";
    private final String insertNewAcc = "INSERT INTO client_accounts (username, password) VALUES (?, ?)";
    public ClientAccountWorker(ConnectionCreator connectionCreator) {
        this.connectionCreator = connectionCreator;
    }



    public boolean checkAccount(String username, String password) throws LogException {
            Connection connection = null;
            try {
                connection = connectionCreator.getConnection();
            } catch (Exception e) {
                LogUtil.logTrace(e);
                throw new LogException("Opening connection in checkAccount failed");
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement(checkAcc);) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next();
                }
            } catch (Exception e) {
                LogUtil.logTrace(e);
                throw new LogException("AccountChecking failed");
            } finally {
                connectionCreator.closeMaterial(connection);
            }
    }


    public boolean insertNewAccount(String username, String password) throws LogException, NameTakenException {
            Connection connection = null;
            try {
                connection = connectionCreator.getConnection();
            } catch (Exception e) {
                LogUtil.logTrace(e);
                throw new LogException("Opening connection in checkAccount failed");
            }
            int rowsAffected = 0;
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertNewAcc)) {
                connection.setAutoCommit(false);

                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                rowsAffected = preparedStatement.executeUpdate();
                connection.commit();
                return rowsAffected > 0;

            } catch (Exception e) {
                connectionCreator.rollbackProcess(connection);
                if(e instanceof SQLException && ((SQLException) e).getSQLState().equals("23505")) {
                    throw new NameTakenException();
                }
                LogUtil.logTrace(e);
                throw new LogException("New Account Insertion failed");
            } finally {
                connectionCreator.resetAutoCommit(connection);
                connectionCreator.closeMaterial(connection);
            }

    }


}
