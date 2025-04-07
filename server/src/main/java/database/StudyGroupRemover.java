package database;

import exceptions.database_exception.NotCreatorException;
import exceptions.database_exception.UnsuccesfulDeletionException;
import exceptions.log_exceptions.EnvNotExistsException;
import exceptions.log_exceptions.LogException;
import logging.LogUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class StudyGroupRemover {
    private ConnectionPool connectionPool;

    public StudyGroupRemover() {}

    public void init() throws EnvNotExistsException {
        connectionPool = new ConnectionPool();
        connectionPool.init();
    }


    public void removeKey(String searchKey) throws LogException, UnsuccesfulDeletionException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.getConnection();
            String sql = "delete from study_groups where search_key = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, searchKey);
            if(preparedStatement.executeUpdate() <= 0) throw new UnsuccesfulDeletionException();

        } catch (UnsuccesfulDeletionException e) {
            throw e;
        } catch (Exception e) {
            LogUtil.logTrace(e);
            throw new LogException();
        } finally {
            connectionPool.closeMaterials(connection, preparedStatement, null);
        }
    }

    public void clearByCreator(String creator) throws LogException, UnsuccesfulDeletionException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.getConnection();
            String sql = "delete from study_groups where creator = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, creator);
            if(preparedStatement.executeUpdate() <= 0) throw new UnsuccesfulDeletionException();

        } catch (UnsuccesfulDeletionException e) {
            throw e;
        } catch (Exception e) {
            LogUtil.logTrace(e);
            throw new LogException();
        } finally {
            connectionPool.closeMaterials(connection, preparedStatement, null);
        }
    }

    public long removeIfGreater( String creator, Long criteria) throws LogException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.getConnection();
            String sql = "delete from study_groups where creator = ? and student_counts > ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, creator);
            preparedStatement.setLong(2, criteria);
            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            LogUtil.logTrace(e);
            throw new LogException();
        } finally {
            connectionPool.closeMaterials(connection, preparedStatement, null);
        }
    }
}
