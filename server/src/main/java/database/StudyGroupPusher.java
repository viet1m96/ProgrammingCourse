package database;

import exceptions.database_exception.UnsuccesfulInsertException;
import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.NameTakenException;
import exceptions.user_exceptions.UserException;
import logging.LogUtil;
import main_objects.Coordinates;
import main_objects.Location;
import main_objects.Person;
import main_objects.StudyGroup;

import java.sql.*;

public class StudyGroupPusher {
    private final String insertCoordinate = "insert into group_coordinates(group_id, coordinate_x, coordinate_y) values (?, ?, ?)";
    private final String insertGroupAdmin = "insert into group_admins(group_id, admin_name, birthday, weight, eye_color) values (?, ?, ?, ?, CAST(? AS color))";
    private final String insertStudyGroup = "insert into study_groups (search_key, group_name, creation_date, student_counts, expelled_students, form_of_education, semester, creator) \n" +
            "values (?, ?, ?, ?, ?, CAST(? AS form_of_education), CAST(? AS semester), ?)";
    
    public StudyGroupPusher() {
    }

    private void insertCoordinateGroup(StudyGroup studyGroup, Connection connection) throws LogException, UnsuccesfulInsertException {
        Coordinates coordinates = studyGroup.getCoordinates();
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertCoordinate)) {
            preparedStatement.setInt(1, studyGroup.getId());
            preparedStatement.setInt(2, coordinates.getX());
            preparedStatement.setInt(3, coordinates.getY());

            if (preparedStatement.executeUpdate() <= 0) {
                throw new UnsuccesfulInsertException("group_coordinate");
            }
        } catch (UnsuccesfulInsertException e) {
            throw e;
        } catch (Exception e) {
            LogUtil.logTrace(e);
            throw new LogException("Coordinate insertion to dtb failed");
        }
    }

    private void insertGroupAdmin(StudyGroup studyGroup, Connection connection) throws LogException, UnsuccesfulInsertException {
        Person admin = studyGroup.getGroupAdmin();
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertGroupAdmin, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, studyGroup.getId());
            preparedStatement.setString(2, admin.getName());
            if (admin.getBirthDay() == null) {
                preparedStatement.setNull(3, Types.DATE);
            } else {
                preparedStatement.setDate(3, Date.valueOf(admin.getBirthDay()));
            }
            if (admin.getWeight() == null) {
                preparedStatement.setNull(4, Types.INTEGER);
            } else {
                preparedStatement.setInt(4, admin.getWeight());
            }
            preparedStatement.setString(5, admin.getEyeColor().toString());

            if (preparedStatement.executeUpdate() <= 0) {
                throw new UnsuccesfulInsertException("group_admin");
            }

            try(ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    studyGroup.getGroupAdmin().setId(resultSet.getInt(1));
                }
            }
        } catch (UnsuccesfulInsertException e) {
            throw e;
        } catch (Exception e) {
            LogUtil.logTrace(e);
            throw new LogException("Group Admin insertion to dtb failed");
        }
    }

    private void insertAdminLocation(Person admin, Connection connection) throws LogException, UnsuccesfulInsertException {
        if (admin.getLocation() == null) return;

        Location location = admin.getLocation();
        String sql = "insert into admin_locations (admin_id, x, y, z, place) values (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, admin.getId());
            preparedStatement.setLong(2, location.getX());
            preparedStatement.setInt(3, location.getY());
            preparedStatement.setInt(4, location.getZ());
            if (location.getName() == null) {
                preparedStatement.setNull(5, Types.VARCHAR);
            } else {
                preparedStatement.setString(5, location.getName());
            }

            if (preparedStatement.executeUpdate() <= 0) {
                throw new UnsuccesfulInsertException("admin_location");
            }
        } catch (UnsuccesfulInsertException e) {
            throw e;
        } catch (Exception e) {
            LogUtil.logTrace(e);
            throw new LogException("Admin Location insertion to dtb failed");
        }
    }

    public void insertStudyGroup(StudyGroup studyGroup, Connection connection) throws Exception, UnsuccesfulInsertException, LogException, NameTakenException {

        synchronized (this) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertStudyGroup, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, studyGroup.getSearchKey());
                preparedStatement.setString(2, studyGroup.getName());
                preparedStatement.setDate(3, Date.valueOf(studyGroup.getCreationDate()));
                preparedStatement.setLong(4, studyGroup.getStudentsCount());
                preparedStatement.setLong(5, studyGroup.getExpelledStudents());
                if(studyGroup.getFormOfEducation() == null) {
                    preparedStatement.setNull(6, Types.VARCHAR);
                } else {
                    preparedStatement.setString(6, studyGroup.getFormOfEducation().toString());
                }
                preparedStatement.setString(7, studyGroup.getSemesterEnum().toString());
                preparedStatement.setString(8, studyGroup.getCreator());
                if (preparedStatement.executeUpdate() <= 0) throw new UnsuccesfulInsertException("study_group");

                try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        Integer id = resultSet.getInt(2);
                        studyGroup.setId(id);
                    } else {
                        throw new UnsuccesfulInsertException("study_group");
                    }
                }
                insertCoordinateGroup(studyGroup, connection);
                insertGroupAdmin(studyGroup, connection);
                insertAdminLocation(studyGroup.getGroupAdmin(), connection);
            }
        }
    }






}
