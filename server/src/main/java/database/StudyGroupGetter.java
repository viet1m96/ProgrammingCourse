package database;

import exceptions.log_exceptions.LogException;
import logging.LogUtil;
import main_objects.StudyGroup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class StudyGroupGetter {

    public Integer getGroupID(String searchKey, Connection connection) throws LogException {
        String sql = "SELECT group_id FROM study_groups WHERE search_key = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, searchKey);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("group_id");
                }
            }
        } catch (Exception e) {
            LogUtil.logTrace(e);
            throw new LogException();
        }
        return null;
    }

    public Integer getAdminID(StudyGroup studyGroup, Connection connection) throws LogException {
        String sql = "SELECT admin_id FROM group_admins WHERE group_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, studyGroup.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("admin_id");
                }
            }
        } catch (Exception e) {
            LogUtil.logTrace(e);
            throw new LogException();
        }
        return null;
    }

    public Integer getCoordinateID(StudyGroup studyGroup, Connection connection) throws LogException {
        String sql = "SELECT coordinate_id FROM group_coordinates WHERE group_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, studyGroup.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("coordinate_id");
                }
            }
        } catch (Exception e) {
            LogUtil.logTrace(e);
            throw new LogException();
        }
        return null;
    }
}
