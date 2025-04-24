package database;

import exceptions.database_exception.UnsuccesfulDeletionException;
import exceptions.log_exceptions.LogException;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class StudyGroupRemover {
    private String removeByKey = "delete from study_groups where search_key = ?";
    private String clearByCreator = "delete from study_groups where creator = ?";
    private String removeIfGreater = "delete from study_groups where creator = ? and student_counts > ?";

    public StudyGroupRemover() {
    }


    public void removeByKey(String searchKey, Connection connection) throws Exception, LogException, UnsuccesfulDeletionException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(removeByKey)) {
            preparedStatement.setString(1, searchKey);
            if (preparedStatement.executeUpdate() < 0) throw new UnsuccesfulDeletionException();

        }
    }

    public Integer clearByCreator(String creator, Connection connection) throws Exception, LogException, UnsuccesfulDeletionException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(clearByCreator)) {
            int rowAffected;
            preparedStatement.setString(1, creator);
            rowAffected = preparedStatement.executeUpdate();
            if (rowAffected < 0) throw new UnsuccesfulDeletionException();
            return rowAffected;
        }
    }

    public long removeIfGreater(String creator, Long criteria, Connection connection) throws LogException, Exception {
        try (PreparedStatement preparedStatement = connection.prepareStatement(removeIfGreater)) {
            preparedStatement.setString(1, creator);
            preparedStatement.setLong(2, criteria);
            return preparedStatement.executeUpdate();
        }
    }
}
