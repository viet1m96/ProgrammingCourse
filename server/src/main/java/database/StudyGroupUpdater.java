package database;

import exceptions.database_exception.FailedConditionUpdateException;
import exceptions.database_exception.UnsuccesfulUpdateException;
import exceptions.log_exceptions.EnvNotExistsException;
import exceptions.log_exceptions.LogException;
import logging.LogUtil;
import main_objects.Coordinates;
import main_objects.Location;
import main_objects.Person;
import main_objects.StudyGroup;

import java.sql.*;

public class StudyGroupUpdater {
    private ConnectionPool connectionPool;
    private StudyGroupGetter studyGroupGetter;
    public StudyGroupUpdater() {}

    public void init() throws EnvNotExistsException {
        studyGroupGetter = new StudyGroupGetter();
        connectionPool = new ConnectionPool();
        connectionPool.init();
    }

    public void replaceIfLower(StudyGroup studyGroup) throws LogException, FailedConditionUpdateException, UnsuccesfulUpdateException {
        Connection connection = null;

        try {
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);
            studyGroup.setId(studyGroupGetter.getGroupID(studyGroup.getSearchKey(), connection));


            int elementAffected = replaceStudyGroupIfLower(studyGroup, connection);
            if(elementAffected == 0) {
                throw new FailedConditionUpdateException();
            }
            updateCoordinates(studyGroup, connection);
            updateGroupAdmin(studyGroup, connection);
            updateAdminLocation(studyGroup, connection);
            connection.commit();
        } catch (FailedConditionUpdateException | UnsuccesfulUpdateException e) {
            connectionPool.rollbackProcess(connection);
            throw e;
        } catch (Exception e) {
            LogUtil.logTrace(e);
            connectionPool.rollbackProcess(connection);
            throw new LogException();
        } finally {
            connectionPool.resetAutoCommit(connection);
            connectionPool.closeMaterials(connection, null, null );
        }
    }


    private int replaceStudyGroupIfLower(StudyGroup studyGroup, Connection connection) throws LogException {
        String sql = """
                    update study_groups
                    set group_name=?,
                        creation_date=?,
                        student_counts=?,
                        expelled_students=?,
                        form_of_education=CAST(? AS form_of_education),
                        semester=CAST(? AS semester)
                    where student_counts < ? and group_id = ?""";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, studyGroup.getName());
            preparedStatement.setDate(2, Date.valueOf(studyGroup.getCreationDate()));
            preparedStatement.setLong(3, studyGroup.getStudentsCount());
            preparedStatement.setLong(4, studyGroup.getExpelledStudents());
            if(studyGroup.getFormOfEducation() == null) {
                preparedStatement.setNull(5, Types.VARCHAR);
            } else {
                preparedStatement.setString(5, studyGroup.getFormOfEducation().toString());
            }
            preparedStatement.setString(6, studyGroup.getSemesterEnum().toString());
            preparedStatement.setLong(7, studyGroup.getStudentsCount());
            preparedStatement.setInt(8, studyGroup.getId());
            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            LogUtil.logTrace(e);
            throw new LogException();
        }
    }

    private void updateCoordinates(StudyGroup studyGroup, Connection connection) throws LogException, UnsuccesfulUpdateException {
        String sql = """
                update group_coordinates
                set coordinate_x = ?,
                    coordinate_y = ?
                where group_id = ?""";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            Coordinates coordinates = studyGroup.getCoordinates();
            preparedStatement.setInt(1, coordinates.getX());
            preparedStatement.setInt(2, coordinates.getY());
            preparedStatement.setInt(3, studyGroup.getId());
            if(preparedStatement.executeUpdate() < 0) throw new UnsuccesfulUpdateException();
            coordinates.setId(studyGroupGetter.getCoordinateID(studyGroup, connection));
        } catch (UnsuccesfulUpdateException e) {
            throw e;
        } catch (Exception e) {
            LogUtil.logTrace(e);
            throw new LogException();
        }
    }


    private void updateGroupAdmin(StudyGroup studyGroup, Connection connection) throws LogException, UnsuccesfulUpdateException {
        String sql = """
                update group_admins
                set admin_name = ?,
                    birthday = ?,
                    weight = ?,
                    eye_color = CAST(? AS color)
                where group_id = ?""";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            Person admin = studyGroup.getGroupAdmin();
            preparedStatement.setString(1, admin.getName());
            if(admin.getBirthDay() == null) {
                preparedStatement.setNull(2, Types.DATE);
            } else {
                preparedStatement.setDate(2, Date.valueOf(admin.getBirthDay()));
            }
            if(admin.getWeight() == null) {
                preparedStatement.setNull(3, Types.INTEGER);
            } else {
                preparedStatement.setInt(3, admin.getWeight());
            }
            preparedStatement.setString(4, admin.getEyeColor().toString());
            preparedStatement.setLong(5, studyGroup.getId());
            if(preparedStatement.executeUpdate() < 0) throw new UnsuccesfulUpdateException();
            admin.setId(studyGroupGetter.getAdminID(studyGroup, connection));
        } catch (UnsuccesfulUpdateException e) {
            throw e;
        } catch (Exception e)  {
            LogUtil.logTrace(e);
            throw new LogException();
        }
    }

    private void updateAdminLocation (StudyGroup studyGroup, Connection connection) throws LogException, UnsuccesfulUpdateException {
        Location location = studyGroup.getGroupAdmin().getLocation();
        if(location == null) return;
        String sql = """
                update admin_locations
                set x = ?,
                    y = ?,
                    z = ?,
                    place = ?
                where admin_id = ?""";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, location.getX());
            preparedStatement.setInt(2, location.getY());
            preparedStatement.setInt(3, location.getZ());
            preparedStatement.setString(4, location.getName());
            preparedStatement.setInt(5, studyGroup.getGroupAdmin().getId());
            if(preparedStatement.executeUpdate() < 0) throw new UnsuccesfulUpdateException();
        } catch (UnsuccesfulUpdateException e) {
            throw e;
        } catch (Exception e) {
            LogUtil.logTrace(e);
            throw new LogException();
        }
    }

    public void simpleUpdate(StudyGroup studyGroup) throws LogException, UnsuccesfulUpdateException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);
            studyGroup.setId(studyGroupGetter.getGroupID(studyGroup.getSearchKey(), connection));

            simpleUpdateStudyGroup(studyGroup, connection);
            updateCoordinates(studyGroup, connection);
            updateGroupAdmin(studyGroup, connection);
            updateAdminLocation(studyGroup, connection);
            connection.commit();
        } catch (UnsuccesfulUpdateException e) {
            connectionPool.rollbackProcess(connection);
            throw e;
        } catch (Exception e) {
            LogUtil.logTrace(e);
            connectionPool.rollbackProcess(connection);
            throw new LogException();
        } finally {
            connectionPool.resetAutoCommit(connection);
            connectionPool.closeMaterials(connection, null, null );
        }
    }

    private void simpleUpdateStudyGroup(StudyGroup studyGroup, Connection connection) throws LogException {
        String sql = """
                update study_groups
                set group_name=?,
                    creation_date=?,
                    student_counts=?,
                    expelled_students=?,
                    form_of_education=CAST(? AS form_of_education),
                    semester=CAST(? AS semester)
                    where group_id=?""";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, studyGroup.getName());
            preparedStatement.setDate(2, Date.valueOf(studyGroup.getCreationDate()));
            preparedStatement.setLong(3, studyGroup.getStudentsCount());
            preparedStatement.setLong(4, studyGroup.getExpelledStudents());
            if(studyGroup.getFormOfEducation() == null) {
                preparedStatement.setNull(5, Types.VARCHAR);
            } else {
                preparedStatement.setString(5, studyGroup.getSemesterEnum().toString());
            }
            preparedStatement.setString(6, studyGroup.getSemesterEnum().toString());
            preparedStatement.setInt(7, studyGroup.getId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            LogUtil.logTrace(e);
            throw new LogException();
        }
    }




}
