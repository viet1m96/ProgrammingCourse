package io_utilities.working_with_input;

import enums.Color;
import enums.FormOfEducation;
import enums.Semester;
import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.UserException;
import exceptions.user_exceptions.WrongInputException;
import exceptions.user_exceptions.WrongInputInFileException;
import main_objects.Coordinates;
import main_objects.Location;
import main_objects.Person;
import main_objects.StudyGroup;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class StudyGroupBuilder {
    public static StudyGroup parseStudyGroup(List<String> groupInfo, List<String> adminInfo) throws UserException, LogException {
        StudyGroup studyGroup = new StudyGroup();
        try {
            Integer id = Integer.parseInt(groupInfo.get(0));
            studyGroup.setId(id);

            String name = groupInfo.get(1);
            studyGroup.setName(name);

            Integer x = Integer.parseInt(groupInfo.get(2));
            Integer y = Integer.parseInt(groupInfo.get(3));
            Coordinates coordinates = new Coordinates(x, y);
            studyGroup.setCoordinates(coordinates);

            LocalDate startDate = LocalDate.parse(groupInfo.get(4), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            studyGroup.setCreationDate(startDate);

            Long studentsCount = Long.parseLong(groupInfo.get(5));
            studyGroup.setStudentsCount(studentsCount);

            Long expelledStudentsCount = Long.parseLong(groupInfo.get(6));
            studyGroup.setExpelledStudents(expelledStudentsCount);
            
            if(groupInfo.get(7).equals("null")) {
                studyGroup.setFormOfEducation(null);
            } else {
                studyGroup.setFormOfEducation(FormOfEducation.valueOf(groupInfo.get(7).toUpperCase()));
            }
            
            Semester semester = Semester.valueOf(groupInfo.get(8).toUpperCase());
            studyGroup.setSemesterEnum(semester);
            studyGroup.setGroupAdmin(parsePerson(adminInfo));
        } catch (DateTimeParseException | IllegalArgumentException e) {
            e.printStackTrace();
            throw new LogException();
        }
        return studyGroup;
    }

    private static Person parsePerson(List<String> adminInfo) throws UserException, LogException {
        Person admin = new Person();
        try {
            admin.setName(adminInfo.get(0));

            if(adminInfo.get(1).equals("null")) {
                admin.setBirthDay(null);
            } else {
                admin.setBirthDay(LocalDate.parse(adminInfo.get(1), DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            }

            if(adminInfo.get(2).equals("null")) {
                admin.setWeight(null);
            } else {
                admin.setWeight(Integer.parseInt(adminInfo.get(2)));
            }

            Color eyeColor = Color.valueOf(adminInfo.get(3).toUpperCase());
            admin.setEyeColor(eyeColor);
            admin.setLocation(parseLocation(adminInfo));

        } catch (DateTimeParseException | IllegalArgumentException e) {
            e.printStackTrace();
            throw new LogException();
        }
        return admin;
    }

    private static Location parseLocation(List<String> adminInfo) throws LogException, UserException {
        Location location = new Location();
        try {
            if(adminInfo.get(4).equals("null") && adminInfo.get(5).equals("null") && adminInfo.get(6).equals("null")) {
                if(!adminInfo.get(7).equals("null")) throw new WrongInputInFileException();
                location = null;
            } else if(adminInfo.get(4).equals("null") || adminInfo.get(5).equals("null") || adminInfo.get(6).equals("null")) {
                throw new WrongInputInFileException();
            } else {
                location.setX(Long.parseLong(adminInfo.get(4)));
                location.setY(Integer.parseInt(adminInfo.get(5)));
                location.setZ(Integer.parseInt(adminInfo.get(6)));
                if(adminInfo.get(7).equals("null")) {
                    location.setName(null);
                } else {
                    location.setName(adminInfo.get(7));
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new LogException();
        }
        return location;
    }
}
