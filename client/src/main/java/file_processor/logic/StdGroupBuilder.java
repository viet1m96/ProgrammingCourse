package file_processor.logic;

import enums.Color;
import enums.FormOfEducation;
import enums.Semester;
import exceptions.user_exceptions.UserException;
import exceptions.user_exceptions.WrongInputException;
import main_objects.Coordinates;
import main_objects.Location;
import main_objects.Person;
import main_objects.StudyGroup;
import validators.InputForAdminValidator;
import validators.InputForGroupValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class StdGroupBuilder {

    public static StudyGroup build(BufferedReader reader) throws IOException, UserException {
        List<String> groupInfo = new ArrayList<>();
        List<String> adminInfo = new ArrayList<>();
        groupInfo.add("0");
        groupInfo.add(getInputFromUser(reader, InputForGroupValidator.isValidName));
        groupInfo.add(getInputFromUser(reader, InputForGroupValidator.isValidGroupX));
        groupInfo.add(getInputFromUser(reader, InputForGroupValidator.isValidGroupY));
        groupInfo.add(java.time.LocalDate.now().toString());
        groupInfo.add(getInputFromUser(reader, InputForGroupValidator.isValidStudCount));
        groupInfo.add(getInputFromUser(reader, InputForGroupValidator.isValidStudCount));
        groupInfo.add(getInputFromUser(reader, InputForGroupValidator.isValidEduForm));
        groupInfo.add(getInputFromUser(reader, InputForGroupValidator.isValidSemester));
        adminInfo.add(getInputFromUser(reader, InputForGroupValidator.isValidName));
        adminInfo.add(getInputFromUser(reader, InputForAdminValidator.isValidBirthday));
        adminInfo.add(getInputFromUser(reader, InputForAdminValidator.isValidWeight));
        adminInfo.add(getInputFromUser(reader, InputForAdminValidator.isValidEyeColor));
        adminInfo.add(getInputFromUser(reader, InputForAdminValidator.isValidCoordinate));
        adminInfo.add(getInputFromUser(reader, InputForAdminValidator.isValidCoordinate));
        adminInfo.add(getInputFromUser(reader, InputForAdminValidator.isValidCoordinate));
        adminInfo.add(getInputFromUser(reader, InputForAdminValidator.isValidPlace));

        return parseStudyGroup(groupInfo, adminInfo);
    }


    private static String getInputFromUser(BufferedReader reader, Predicate<String> predicate) throws IOException, WrongInputException {
        String str = reader.readLine();
        boolean check = predicate.test(str);
        if (check) {
            return str;
        } else {
            throw new WrongInputException();
        }
    }

    private static StudyGroup parseStudyGroup(List<String> groupInfo, List<String> adminInfo) throws WrongInputException {
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

            LocalDate startDate = LocalDate.parse(groupInfo.get(4));
            studyGroup.setCreationDate(startDate);

            Long studentsCount = Long.parseLong(groupInfo.get(5));
            studyGroup.setStudentsCount(studentsCount);

            Long expelledStudentsCount = Long.parseLong(groupInfo.get(6));
            studyGroup.setExpelledStudents(expelledStudentsCount);

            if (groupInfo.get(7).equals("null")) {
                studyGroup.setFormOfEducation(null);
            } else {
                studyGroup.setFormOfEducation(FormOfEducation.valueOf(groupInfo.get(7).toUpperCase()));
            }

            Semester semester = Semester.valueOf(groupInfo.get(8).toUpperCase());
            studyGroup.setSemesterEnum(semester);
            studyGroup.setGroupAdmin(parsePerson(adminInfo));
        } catch (DateTimeParseException | IllegalArgumentException e) {
            throw new WrongInputException();
        }
        return studyGroup;
    }

    private static Person parsePerson(List<String> adminInfo) throws WrongInputException {
        Person admin = new Person();
        try {
            admin.setName(adminInfo.get(0));

            if (adminInfo.get(1).equals("null")) {
                admin.setBirthDay(null);
            } else {
                admin.setBirthDay(LocalDate.parse(adminInfo.get(1)));
            }

            if (adminInfo.get(2).equals("null")) {
                admin.setWeight(null);
            } else {
                admin.setWeight(Integer.parseInt(adminInfo.get(2)));
            }

            Color eyeColor = Color.valueOf(adminInfo.get(3).toUpperCase());
            admin.setEyeColor(eyeColor);
            admin.setLocation(parseLocation(adminInfo));

        } catch (DateTimeParseException | IllegalArgumentException e) {
            throw new WrongInputException();
        }
        return admin;
    }

    private static Location parseLocation(List<String> adminInfo) throws WrongInputException {
        Location location = new Location();
        try {
            if (adminInfo.get(4).equals("null") && adminInfo.get(5).equals("null") && adminInfo.get(6).equals("null")) {
                if (!adminInfo.get(7).equals("null")) throw new WrongInputException();
                return null;
            } else if (adminInfo.get(4).equals("null") || adminInfo.get(5).equals("null") || adminInfo.get(6).equals("null")) {
                throw new WrongInputException();
            } else {
                location.setX(Long.parseLong(adminInfo.get(4)));
                location.setY(Integer.parseInt(adminInfo.get(5)));
                location.setZ(Integer.parseInt(adminInfo.get(6)));
                if (adminInfo.get(7).equals("null")) {
                    location.setName(null);
                } else {
                    location.setName(adminInfo.get(7));
                }
            }
        } catch (NumberFormatException e) {
            throw new WrongInputException();
        }
        return location;
    }

}
