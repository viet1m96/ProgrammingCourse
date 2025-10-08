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

/**
 * The `DatabaseManager` class manages interactions with the database for `StudyGroup` objects.
 * It provides methods for inserting, removing, updating, and retrieving `StudyGroup` data.
 */
public class DatabaseManager {
    private final ConnectionCreator connectionCreator;
    private final StudyGroupPusher studyGroupPusher;
    private final StudyGroupRemover studyGroupRemover;
    private final StudyGroupUpdater studyGroupUpdater;
    private final StudyGroupUploader studyGroupUploader;

    /**
     * Constructs a `DatabaseManager` object.
     *
     * @param connectionCreator The `ConnectionCreator` used to obtain database connections.
     * @throws EnvNotExistsException If any of the required environment variables are not set.
     */
    public DatabaseManager(ConnectionCreator connectionCreator) throws EnvNotExistsException {
        this.connectionCreator = connectionCreator;
        studyGroupPusher = new StudyGroupPusher();
        studyGroupRemover = new StudyGroupRemover();
        studyGroupUpdater = new StudyGroupUpdater();
        studyGroupUploader = new StudyGroupUploader();
    }

    /**
     * Inserts a new `StudyGroup` into the database.
     *
     * @param studyGroup The `StudyGroup` object to insert.
     * @throws NameTakenException         If there is a name conflict.
     * @throws LogException               If there is an error during the insertion process.
     * @throws UnsuccesfulInsertException If the insertion fails.
     */
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
        } finally {
            connectionCreator.resetAutoCommit(connection);
            connectionCreator.closeMaterial(connection);
        }
    }

    /**
     * Removes a `StudyGroup` from the database by its search key.
     *
     * @param searchKey The search key of the `StudyGroup` to remove.
     * @throws LogException                 If there is an error during the removal process.
     * @throws UnsuccesfulDeletionException If the deletion fails.
     */
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

    /**
     * Clears `StudyGroup`s from the database created by a specific creator.
     *
     * @param creator The creator to clear `StudyGroup`s for.
     * @return The number of rows affected by the clear operation.
     * @throws LogException                 If there is an error during the clearing process.
     * @throws UnsuccesfulDeletionException If the deletion fails.
     */
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

    /**
     * Removes `StudyGroup`s from the database if they have a studentsCount greater than the specified criteria.
     *
     * @param creator  The creator of the `StudyGroup`s to remove.
     * @param criteria The criteria to compare the studentsCount against.
     * @return The number of rows affected by the removal operation.
     * @throws LogException If there is an error during the removal process.
     */
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

    /**
     * Updates a `StudyGroup` in the database.
     *
     * @param studyGroup The `StudyGroup` object to update.
     * @throws LogException               If there is an error during the update process.
     * @throws UnsuccesfulUpdateException If the update fails.
     */
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

    /**
     * Uploads `StudyGroup` data from the database.
     *
     * @return A list of `StudyGroup` objects loaded from the database.
     * @throws LogException If there is an error during the data upload process.
     */
    public List<StudyGroup> uploadData() throws LogException {
        try (Connection connection = connectionCreator.getConnection()) {
            List<StudyGroup> results = studyGroupUploader.loadStudyGroups(connection);
            return results;
        } catch (Exception e) {
            LogUtil.logTrace(e);
            throw new LogException("Data upload failed");
        }
    }

    /**
     * Replaces a `StudyGroup` in the database if it is lower than the existing one.
     *
     * @param studyGroup The `StudyGroup` object to replace with.
     * @throws LogException                 If there is an error during the replacement process.
     * @throws UnsuccesfulInsertException   If the insertion fails.
     * @throws UnsuccesfulDeletionException If the deletion fails.
     */
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
