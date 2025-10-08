package database;


import exceptions.database_exception.UnsuccesfulUpdateException;

import exceptions.log_exceptions.LogException;
import logging.LogUtil;
import main_objects.Coordinates;
import main_objects.Location;
import main_objects.Person;
import main_objects.StudyGroup;

import java.sql.*;

public class StudyGroupUpdater {
    private String updateQuery = """
            update study_groups
            set group_name=?,
                creation_date=?,
                student_counts=?,
                expelled_students=?,
                form_of_education=CAST(? AS form_of_education),
                semester=CAST(? AS semester)
                where group_id=?""";
    private String updateCoordinate = """
            update group_coordinates
            set coordinate_x = ?,
                coordinate_y = ?
            where group_id = ?""";
    private String updateGroupAdmin = """
            update group_admins
            set admin_name = ?,
                birthday = ?,
                weight = ?,
                eye_color = CAST(? AS color)
            where group_id = ?""";
    private String updateAdminLocation = """
            update admin_locations
            set x = ?,
                y = ?,
                z = ?,
                place = ?
            where admin_id = ?""";

    public StudyGroupUpdater() {
    }

    private void updateCoordinates(StudyGroup studyGroup, Connection connection) throws LogException, UnsuccesfulUpdateException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateCoordinate)) {
            Coordinates coordinates = studyGroup.getCoordinates();
            preparedStatement.setInt(1, coordinates.getX());
            preparedStatement.setInt(2, coordinates.getY());
            preparedStatement.setInt(3, studyGroup.getId());
            if (preparedStatement.executeUpdate() < 0) throw new UnsuccesfulUpdateException();
        } catch (UnsuccesfulUpdateException e) {
            throw e;
        } catch (Exception e) {
            LogUtil.logTrace(e);
            throw new LogException("Coordinate Group update failed");
        }
    }


    private void updateGroupAdmin(StudyGroup studyGroup, Connection connection) throws LogException, UnsuccesfulUpdateException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateGroupAdmin)) {
            Person admin = studyGroup.getGroupAdmin();
            preparedStatement.setString(1, admin.getName());
            if (admin.getBirthDay() == null) {
                preparedStatement.setNull(2, Types.DATE);
            } else {
                preparedStatement.setDate(2, Date.valueOf(admin.getBirthDay()));
            }
            if (admin.getWeight() == null) {
                preparedStatement.setNull(3, Types.INTEGER);
            } else {
                preparedStatement.setInt(3, admin.getWeight());
            }
            preparedStatement.setString(4, admin.getEyeColor().toString());
            preparedStatement.setLong(5, studyGroup.getId());
            if (preparedStatement.executeUpdate() < 0) throw new UnsuccesfulUpdateException();
        } catch (UnsuccesfulUpdateException e) {
            throw e;
        } catch (Exception e) {
            LogUtil.logTrace(e);
            throw new LogException("Admin update failed");
        }
    }

    private void updateAdminLocation(StudyGroup studyGroup, Connection connection) throws LogException, UnsuccesfulUpdateException {
        Location location = studyGroup.getGroupAdmin().getLocation();
        if (location == null) return;
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateAdminLocation)) {
            preparedStatement.setLong(1, location.getX());
            preparedStatement.setInt(2, location.getY());
            preparedStatement.setInt(3, location.getZ());
            preparedStatement.setString(4, location.getName());
            preparedStatement.setInt(5, studyGroup.getGroupAdmin().getId());
            if (preparedStatement.executeUpdate() < 0) throw new UnsuccesfulUpdateException();
        } catch (UnsuccesfulUpdateException e) {
            throw e;
        } catch (Exception e) {
            LogUtil.logTrace(e);
            throw new LogException("Admin Location update failed");
        }
    }

    public void simpleUpdate(StudyGroup studyGroup, Connection connection) throws LogException, UnsuccesfulUpdateException, Exception {
        simpleUpdateStudyGroup(studyGroup, connection);
        updateCoordinates(studyGroup, connection);
        updateGroupAdmin(studyGroup, connection);
        updateAdminLocation(studyGroup, connection);
    }

    private void simpleUpdateStudyGroup(StudyGroup studyGroup, Connection connection) throws LogException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, studyGroup.getName());
            preparedStatement.setDate(2, Date.valueOf(studyGroup.getCreationDate()));
            preparedStatement.setLong(3, studyGroup.getStudentsCount());
            preparedStatement.setLong(4, studyGroup.getExpelledStudents());
            if (studyGroup.getFormOfEducation() == null) {
                preparedStatement.setNull(5, Types.VARCHAR);
            } else {
                preparedStatement.setString(5, studyGroup.getFormOfEducation().toString());
            }
            preparedStatement.setString(6, studyGroup.getSemesterEnum().toString());
            preparedStatement.setInt(7, studyGroup.getId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            LogUtil.logTrace(e);
            throw new LogException("Simple update Study Group failed");
        }
    }


}
