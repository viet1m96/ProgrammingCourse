package database;

import config.AppConfig;
import enums.Color;
import enums.FormOfEducation;
import enums.Semester;
import exceptions.log_exceptions.LogException;
import logging.LogUtil;
import main_objects.Coordinates;
import main_objects.Location;
import main_objects.Person;
import main_objects.StudyGroup;
import printer_options.RainbowPrinter;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudyGroupUploader {
    private Connection connection;

    public StudyGroupUploader() {
    }

    public void init() throws LogException {
        try {
            String url = AppConfig.getDatabase_url();
            String username = AppConfig.getDatabase_user();
            String password = AppConfig.getDatabase_pass();
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            LogUtil.logTrace(e);
            throw new LogException();
        }
    }

    public List<StudyGroup> loadStudyGroups() throws LogException {
        String sql = """
                select study_groups.*, group_coordinates.*, group_admins.*, admin_locations.* from study_groups
                         left join group_coordinates on study_groups.group_id = group_coordinates.group_id
                         left join group_admins on study_groups.group_id = group_admins.group_id
                         left join admin_locations on group_admins.admin_id = admin_locations.admin_id""";
        List<StudyGroup> result = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                int line = 1;
                while (resultSet.next()) {
                    Object coordinateID = resultSet.getObject("coordinate_id");
                    Object adminID = resultSet.getObject("admin_id");
                    if(coordinateID == null || adminID == null) {
                        RainbowPrinter.printInfo("Can not load the object on the " + line + " line");
                        line++;
                        continue;
                    }
                    StudyGroup studyGroup = new StudyGroup();
                    studyGroup.setSearchKey(resultSet.getString("search_key"));
                    studyGroup.setId(resultSet.getInt("group_id"));
                    studyGroup.setName(resultSet.getString("group_name"));
                    studyGroup.setCreationDate(resultSet.getDate("creation_date").toLocalDate());
                    studyGroup.setStudentsCount(resultSet.getLong("student_counts"));
                    studyGroup.setExpelledStudents(resultSet.getLong("expelled_students"));
                    Object formEdu = resultSet.getObject("form_of_education");
                    if(formEdu == null) {
                        studyGroup.setFormOfEducation(null);
                    } else {
                        studyGroup.setFormOfEducation(FormOfEducation.valueOf(formEdu.toString()));
                    }
                    studyGroup.setSemesterEnum(Semester.valueOf(resultSet.getString("semester")));
                    studyGroup.setCreator(resultSet.getString("creator"));

                    Coordinates coordinates = new Coordinates();
                    coordinates.setId(resultSet.getInt("coordinate_id"));
                    coordinates.setX(resultSet.getInt("coordinate_x"));
                    coordinates.setY(resultSet.getInt("coordinate_y"));
                    studyGroup.setCoordinates(coordinates);

                    Person admin = new Person();
                    admin.setId(resultSet.getInt("admin_id"));
                    admin.setName(resultSet.getString("admin_name"));
                    Object birthDay = resultSet.getObject("birthday");
                    if(birthDay == null) {
                        admin.setBirthDay(null);
                    } else {
                        admin.setBirthDay((LocalDate) birthDay);
                    }
                    Object weight = resultSet.getObject("weight");
                    if(weight == null) {
                        admin.setWeight(null);
                    } else {
                        admin.setWeight((Integer) weight);
                    }
                    admin.setEyeColor(Color.valueOf(resultSet.getString("eye_color")));
                    Location location = new Location();
                    Object locationID = resultSet.getObject("location_id");
                    if(locationID == null) {
                        admin.setLocation(null);
                    } else {
                        location.setX(resultSet.getLong("x"));
                        location.setY(resultSet.getInt("y"));
                        location.setZ(resultSet.getInt("z"));
                        Object locationName = resultSet.getObject("place");
                        if(locationName == null) {
                            location.setName(null);
                        } else {
                            location.setName((String) locationName);
                        }
                        admin.setLocation(location);
                    }
                    studyGroup.setGroupAdmin(admin);
                    result.add(studyGroup);
                    line++;
                }
            }
        } catch (Exception e) {
            LogUtil.logTrace(e);
            throw new LogException();
        } finally {
            try {
                if(connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                LogUtil.logTrace(e);
            }
        }
        return result;
    }

}
