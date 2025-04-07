package database;

import exceptions.database_exception.UnsuccesfulInsertException;
import exceptions.log_exceptions.EnvNotExistsException;
import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.KeyTakenException;
import logging.LogUtil;
import main_objects.Coordinates;
import main_objects.Location;
import main_objects.Person;
import main_objects.StudyGroup;

import java.sql.*;

public class StudyGroupPusher {
    private ConnectionPool connectionPool;

    public StudyGroupPusher() {}

    public void init() throws EnvNotExistsException {
        connectionPool = new ConnectionPool();
        connectionPool.init();
    }


    private void insertCoordinateGroup(StudyGroup studyGroup, Connection connection) throws LogException, UnsuccesfulInsertException {
        String sql = "insert into group_coordinates(group_id, coordinate_x, coordinate_y) values (?, ?, ?)";
        Coordinates coordinates = studyGroup.getCoordinates();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, studyGroup.getId());
            preparedStatement.setInt(2, coordinates.getX());
            preparedStatement.setInt(3, coordinates.getY());

            if (preparedStatement.executeUpdate() <= 0) {
                throw new UnsuccesfulInsertException("group_coordinate");
            }

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    coordinates.setId(resultSet.getInt(1));
                }
            }
        } catch (UnsuccesfulInsertException e) {
            throw e;
        } catch (Exception e) {
            LogUtil.logTrace(e);
            throw new LogException();
        }
    }

    private void insertGroupAdmin(StudyGroup studyGroup, Connection connection) throws LogException, UnsuccesfulInsertException {
        String sql = "insert into group_admins(group_id, admin_name, birthday, weight, eye_color) values (?, ?, ?, ?, CAST(? AS color))";
        Person admin = studyGroup.getGroupAdmin();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
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
            throw new LogException();
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
            throw new LogException();
        }
    }

    public void insertStudyGroup(StudyGroup studyGroup) throws UnsuccesfulInsertException, LogException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        synchronized (this) {
            try {
                connection = connectionPool.getConnection();
                connection.setAutoCommit(false);

                String sql = "insert into study_groups (search_key, group_name, creation_date, student_counts, expelled_students, form_of_education, semester, creator) \n" +
                        "values (?, ?, ?, ?, ?, CAST(? AS form_of_education), CAST(? AS semester), ?)";
                preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
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

                resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    Integer id = resultSet.getInt(2);
                    studyGroup.setId(id);
                } else {
                    throw new UnsuccesfulInsertException("study_group");
                }

                insertCoordinateGroup(studyGroup, connection);
                insertGroupAdmin(studyGroup, connection);
                insertAdminLocation(studyGroup.getGroupAdmin(), connection);
                connection.commit();
            } catch (UnsuccesfulInsertException e) {
                connectionPool.rollbackProcess(connection);
                throw e;
            } catch (Exception e) {
                connectionPool.rollbackProcess(connection);
                LogUtil.logTrace(e);
                throw new LogException();
            }  finally {
                connectionPool.resetAutoCommit(connection);
                connectionPool.closeMaterials(connection, preparedStatement, resultSet);
            }
        }
    }






}
