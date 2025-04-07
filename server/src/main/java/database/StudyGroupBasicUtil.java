package database;

import exceptions.database_exception.NotCreatorException;
import exceptions.log_exceptions.EnvNotExistsException;
import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.KeyTakenException;
import exceptions.user_exceptions.WrongKeyException;
import logging.LogUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StudyGroupBasicUtil {
    private ConnectionPool connectionPool;

    public StudyGroupBasicUtil() {}

    public void init() throws EnvNotExistsException {
        connectionPool = new ConnectionPool();
        connectionPool.init();
    }

    public void checkCreator(String searchKey, String creator) throws LogException, NotCreatorException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.getConnection();
            String sql = "SELECT 1 FROM study_groups WHERE search_key = ? AND creator = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, searchKey);
            preparedStatement.setString(2, creator);
            if(!preparedStatement.executeQuery().next()) throw new NotCreatorException();
        } catch (NotCreatorException e) {
            throw e;
        } catch (Exception e) {
            LogUtil.logTrace(e);
            throw new LogException();
        } finally {
            connectionPool.closeMaterials(connection, preparedStatement, null);
        }
    }

    public void checkKeyExists(String searchKey) throws LogException, WrongKeyException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();
            String sql = "select 1 from study_groups where search_key = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, searchKey);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) throw new WrongKeyException();
        } catch (WrongKeyException e) {
            throw e;
        } catch (Exception e) {
            LogUtil.logTrace(e);
            throw new LogException();
        } finally {
            connectionPool.closeMaterials(connection, preparedStatement, resultSet);
        }
    }

    public void checkUsedSearchKey(String searchKey) throws LogException, KeyTakenException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.getConnection();
            String sql = "select 1 from study_groups where search_key = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, searchKey);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) throw new KeyTakenException();
        } catch (KeyTakenException e) {
            throw e;
        } catch (Exception e) {
            LogUtil.logTrace(e);
            throw new LogException();
        } finally {
            connectionPool.closeMaterials(connection, preparedStatement, resultSet);
        }
    }
}
