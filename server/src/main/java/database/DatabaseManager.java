package database;

import exceptions.database_exception.UnsuccesfulDeletionException;
import exceptions.database_exception.UnsuccesfulInsertException;
import exceptions.database_exception.UnsuccesfulUpdateException;
import exceptions.log_exceptions.EnvNotExistsException;
import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.NameTakenException;
import logging.LogUtil;
import main_objects.StudyGroup;

import java.sql.Connection;
import java.util.List;

public class DatabaseManager {
    private final ConnectionCreator connectionCreator;
    private final StudyGroupPusher studyGroupPusher;
    private final StudyGroupRemover studyGroupRemover;
    private final StudyGroupUpdater studyGroupUpdater;
    private final StudyGroupUploader studyGroupUploader;

    public DatabaseManager(ConnectionCreator connectionCreator) throws EnvNotExistsException {
       this.connectionCreator = connectionCreator;
        studyGroupPusher = new StudyGroupPusher();
        studyGroupRemover = new StudyGroupRemover();
        studyGroupUpdater = new StudyGroupUpdater();
        studyGroupUploader = new StudyGroupUploader();
    }

    public void insertStudyGroup(StudyGroup studyGroup) throws NameTakenException, LogException, UnsuccesfulInsertException {
        Connection connection = null;
        try {
            connection = connectionCreator.getConnection();
            connection.setAutoCommit(false);

            studyGroupPusher.insertStudyGroup(studyGroup, connection);
            connection.commit();
        } catch (UnsuccesfulInsertException e) {
            connectionCreator.rollbackProcess(connection);
            throw e;
        } catch (Exception e) {
            connectionCreator.rollbackProcess(connection);
            LogUtil.logTrace(e);
            throw new LogException("Study Group insertion to dtb failed");
        }  finally {
            connectionCreator.resetAutoCommit(connection);
            connectionCreator.closeMaterial(connection);
        }
    }

    public void removeByKey(String searchKey) throws LogException, UnsuccesfulDeletionException {
        Connection connection = null;
        try {
            connection = connectionCreator.getConnection();
            connection.setAutoCommit(false);

            studyGroupRemover.removeByKey(searchKey, connection);
            connection.commit();
        } catch (UnsuccesfulDeletionException e) {
            connectionCreator.rollbackProcess(connection);
            throw e;
        } catch (Exception e) {
            connectionCreator.rollbackProcess(connection);
            LogUtil.logTrace(e);
            throw new LogException("Remove Key from dtb failed");
        } finally {
            connectionCreator.resetAutoCommit(connection);
            connectionCreator.closeMaterial(connection);
        }
    }

    public Integer clearByCreator(String creator) throws LogException, UnsuccesfulDeletionException {
        Connection connection = null;
        try {
            connection = connectionCreator.getConnection();
            connection.setAutoCommit(false);

            int rowAffected = studyGroupRemover.clearByCreator(creator, connection);
            connection.commit();
            return rowAffected;
        } catch (UnsuccesfulDeletionException e) {
            connectionCreator.rollbackProcess(connection);
            throw e;
        } catch (Exception e) {
            connectionCreator.rollbackProcess(connection);
            LogUtil.logTrace(e);
            throw new LogException("Clear from dtb failed");
        } finally {
            connectionCreator.resetAutoCommit(connection);
            connectionCreator.closeMaterial(connection);
        }
    }

    public long removeIfGreater(String creator, Long criteria) throws LogException {
        Connection connection = null;
        try {
            connection = connectionCreator.getConnection();
            connection.setAutoCommit(false);

            long rowAffected = studyGroupRemover.removeIfGreater(creator, criteria, connection);
            connection.commit();
            return rowAffected;
        } catch (Exception e) {
            connectionCreator.rollbackProcess(connection);
            LogUtil.logTrace(e);
            throw new LogException("RemoveIfGreater from dtb failed");
        } finally {
            connectionCreator.resetAutoCommit(connection);
            connectionCreator.closeMaterial(connection);
        }
    }

    public void updateStudyGroup(StudyGroup studyGroup) throws LogException, UnsuccesfulUpdateException {
        Connection connection = null;
        try {
            connection = connectionCreator.getConnection();
            connection.setAutoCommit(false);

            studyGroupUpdater.simpleUpdate(studyGroup, connection);
            connection.commit();
        } catch (UnsuccesfulUpdateException e) {
            connectionCreator.rollbackProcess(connection);
            throw e;
        } catch (Exception e) {
            LogUtil.logTrace(e);
            connectionCreator.rollbackProcess(connection);
            throw new LogException("Simple update failed");
        } finally {
            connectionCreator.resetAutoCommit(connection);
            connectionCreator.closeMaterial(connection);
        }
    }

    public List<StudyGroup> uploadData() throws LogException {
        try (Connection connection = connectionCreator.getConnection()) {
            List<StudyGroup> results = studyGroupUploader.loadStudyGroups(connection);
            return results;
        } catch (Exception e) {
            LogUtil.logTrace(e);
            throw new LogException("Data upload failed");
        }
    }

    public void replaceIfLower(StudyGroup studyGroup) throws LogException, UnsuccesfulInsertException, UnsuccesfulDeletionException {
        Connection connection = null;
        try {
            connection = connectionCreator.getConnection();
            connection.setAutoCommit(false);

            studyGroupRemover.removeByKey(studyGroup.getSearchKey(), connection);
            studyGroupPusher.insertStudyGroup(studyGroup, connection);
        } catch (UnsuccesfulDeletionException | UnsuccesfulInsertException e) {
            connectionCreator.rollbackProcess(connection);
            throw e;
        } catch (Exception e) {
            connectionCreator.rollbackProcess(connection);
            LogUtil.logTrace(e);
            throw new LogException("Clear from dtb failed");
        } finally {
            connectionCreator.resetAutoCommit(connection);
            connectionCreator.closeMaterial(connection);
        }
    }


}
