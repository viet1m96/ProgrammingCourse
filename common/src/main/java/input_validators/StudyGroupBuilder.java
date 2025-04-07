package input_validators;

import enums.Color;
import enums.FormOfEducation;
import enums.Semester;
import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.UserException;
import exceptions.user_exceptions.WrongInputInFileException;
import main_objects.Coordinates;
import main_objects.Location;
import main_objects.Person;
import main_objects.StudyGroup;
import printer_options.RainbowPrinter;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * The {@code StudyGroupBuilder} class provides static methods for parsing data from a list of strings and constructing
 * {@link StudyGroup} and related objects ({@link Person}, {@link Location}, {@link Coordinates}).
 * It handles parsing and validation of data, including handling "null" values from files and throwing appropriate exceptions
 * if parsing fails or data is invalid.
 */
public class StudyGroupBuilder {

    /**
     * Parses a list of strings representing StudyGroup and Person (admin) data and constructs a {@link StudyGroup} object.
     *
     * @param groupInfo  A list of strings containing the StudyGroup information. The order of elements is assumed to be:
     *                   [id, name, coordinateX, coordinateY, creationDate, studentsCount, expelledStudentsCount, formOfEducation, semester].
     * @param adminInfo  A list of strings containing the Person (admin) information. The order of elements is assumed to be:
     *                   [name, birthday, weight, eyeColor, locationX, locationY, locationZ, locationName].
     * @return A {@link StudyGroup} object constructed from the parsed data.
     * @throws UserException If there is an issue with the user-provided data (e.g., incorrect format).
     * @throws LogException  If there is an issue during logging or parsing.
     */
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
            e.printStackTrace();//replace later
            throw new LogException();
        }
        return studyGroup;
    }

    /**
     * Parses a list of strings representing Person data and constructs a {@link Person} object.
     *
     * @param adminInfo A list of strings containing the Person information. The order of elements is assumed to be:
     *                  [name, birthday, weight, eyeColor, locationX, locationY, locationZ, locationName].
     * @return A {@link Person} object constructed from the parsed data.
     * @throws UserException If there is an issue with the user-provided data (e.g., incorrect format).
     * @throws LogException  If there is an issue during logging or parsing.
     */
    private static Person parsePerson(List<String> adminInfo) throws UserException, LogException {
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
            e.printStackTrace();//replace later
            throw new LogException();
        }
        return admin;
    }

    /**
     * Parses a list of strings representing Location data and constructs a {@link Location} object.
     *
     * @param adminInfo A list of strings containing the Location information within the Person's admin info. The order of elements is assumed to be:
     *                  [..., locationX, locationY, locationZ, locationName].
     * @return A {@link Location} object constructed from the parsed data, or null if all location fields are "null".
     * @throws LogException  If there is an issue during logging or parsing.
     * @throws UserException If there is an issue with the user-provided data (e.g., inconsistent null values).
     */
    private static Location parseLocation(List<String> adminInfo) throws LogException, UserException {
        Location location = new Location();
        try {
            if (adminInfo.get(4).equals("null") && adminInfo.get(5).equals("null") && adminInfo.get(6).equals("null")) {
                if (!adminInfo.get(7).equals("null")) throw new WrongInputInFileException();
                return null; //  Return null if all location fields are null.
            } else if (adminInfo.get(4).equals("null") || adminInfo.get(5).equals("null") || adminInfo.get(6).equals("null")) {
                throw new WrongInputInFileException(); //  Throw exception if some location fields are null, but not all.
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
            RainbowPrinter.printError(e.toString());
            throw new LogException();
        }
        return location;
    }
}
