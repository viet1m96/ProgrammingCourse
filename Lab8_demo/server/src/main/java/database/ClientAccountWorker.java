package database;

import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.NameTakenException;
import logging.LogUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The `ClientAccountWorker` class handles database operations related to client accounts.
 * It provides methods for checking existing accounts and inserting new accounts.
 */
public class ClientAccountWorker {
    private final ConnectionCreator connectionCreator;
    private final String checkAcc = "SELECT * FROM client_accounts WHERE username = ? AND password = ?";
    private final String insertNewAcc = "INSERT INTO client_accounts (username, password) VALUES (?, ?)";

    /**
     * Constructs a `ClientAccountWorker` object.
     *
     * @param connectionCreator The `ConnectionCreator` used to obtain database connections.
     */
    public ClientAccountWorker(ConnectionCreator connectionCreator) {
        this.connectionCreator = connectionCreator;
    }


    /**
     * Checks if an account with the given username and password exists in the database.
     *
     * @param username The username of the account to check.
     * @param password The password of the account to check.
     * @return `true` if an account with the given credentials exists; `false` otherwise.
     * @throws LogException If there is an error while connecting to the database or executing the query.
     */
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


    /**
     * Inserts a new account into the database.
     *
     * @param username The username of the new account.
     * @param password The password of the new account.
     * @return `true` if the account was successfully inserted; `false` otherwise.
     * @throws LogException       If there is an error while connecting to the database or executing the query.
     * @throws NameTakenException If an account with the given username already exists.
     */
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
            if (e instanceof SQLException && ((SQLException) e).getSQLState().equals("23505")) {
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
