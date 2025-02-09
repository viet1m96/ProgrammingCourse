package read_mode;

import enums.TypeOfGrp;
import enums.TypeOfPer;
import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.UserException;
import exceptions.user_exceptions.WrongInputException;
import io_utilities.printers.RainbowPrinter;
import io_utilities.working_with_input.InputReader;
import io_utilities.working_with_input.ObjInputChecker;
import iostream.Invoker;
import main_objects.Person;
import main_objects.StudyGroup;
import packets.Request;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code ConsoleReaderMode} class implements the {@link ReadMode} interface and provides a way to build a {@link StudyGroup}
 * object by reading input from the console.  It prompts the user for various attributes of the group and its administrator.
 */
public class ConsoleReaderMode implements ReadMode {

    /**
     * Constructs a new {@code ConsoleReaderMode} object.  This constructor performs no actions.
     */
    public ConsoleReaderMode() {}

    /**
     * Builds a {@link StudyGroup} object by prompting the user for input through the console.
     *
     * @param reader The {@link InputReader} to use for reading input.
     * @return A newly created {@link StudyGroup} object.
     * @throws LogException If an {@link IOException} occurs while reading input.
     */
    public static StudyGroup build(InputReader reader) throws LogException {
        List<String> groupInfo = new ArrayList<>();
        List<String> adminInfo = new ArrayList<>();
        groupInfo.add("0"); // Pre-set ID to 0; it's likely to be overwritten later.

        try {
            RainbowPrinter.printInfo("Reading some group info...");
            groupInfo.add(getInputFromUserForGroup(reader, TypeOfGrp.STRING, "Name of the group", "Must not be empty"));
            groupInfo.add(getInputFromUserForGroup(reader, TypeOfGrp.X, "Coordinate X of this group", "Must be a number > -678 and not be empty"));
            groupInfo.add(getInputFromUserForGroup(reader, TypeOfGrp.Y, "Coordinate Y of this group", "Must be a number > -438 and not be empty"));
            groupInfo.add(java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))); // Sets creation date to current date.
            groupInfo.add(getInputFromUserForGroup(reader, TypeOfGrp.COUNT, "The amount of students in this group", "Must be an integer greater than 0"));
            groupInfo.add(getInputFromUserForGroup(reader, TypeOfGrp.COUNT, "The amount of expelled students in this group", "Must be an integer greater than 0"));
            groupInfo.add(getInputFromUserForGroup(reader, TypeOfGrp.EDU, "The format of this group", "Must be one of these following types: DISTANCE_EDUCATION, FULL_TIME_EDUCATION, EVENING_CLASSES"));
            groupInfo.add(getInputFromUserForGroup(reader, TypeOfGrp.SEMESTER, "Which semester ", "Must be one of these following types: FIRST, SECOND, FIFTH, SIXTH, SEVENTH"));

            RainbowPrinter.printInfo("Reading some admin info...");
            adminInfo.add(getInputFromUserForPerson(reader, TypeOfPer.STRING, "Name of the admin", "Must not be empty"));
            adminInfo.add(getInputFromUserForPerson(reader, TypeOfPer.BIRTHDAY, "Birthday", "Must be in this form: DD-MM-YYYY"));
            adminInfo.add(getInputFromUserForPerson(reader, TypeOfPer.WEIGHT, "Weight", "Must be an integer greater than 0"));
            adminInfo.add(getInputFromUserForPerson(reader, TypeOfPer.COLOR, "eyeColor", "Must be one of these following types: RED, BLACK, BLUE, ORANGE, BROWN"));
            adminInfo.add(getInputFromUserForPerson(reader, TypeOfPer.X, "Coordinate X of admin", "Must be an integer greater than 0"));
            adminInfo.add(getInputFromUserForPerson(reader, TypeOfPer.Y, "Coordinate Y of admin", "Must be an integer greater than 0"));
            adminInfo.add(getInputFromUserForPerson(reader, TypeOfPer.Z, "Coordinate Z of admin", "Must be an integer greater than 0"));
            adminInfo.add(getInputFromUserForPerson(reader, TypeOfPer.STRING, "The location of admin(where)", "Must not be empty"));

        } catch (IOException e) {
            e.printStackTrace(); // Important to keep stack trace for debugging.
            throw new LogException();
        }
        return new StudyGroup(groupInfo, adminInfo);
    }

    /**
     * Prompts the user for input related to a {@link StudyGroup} attribute.
     *
     * @param reader    The {@link InputReader} to use for reading input.
     * @param type      The {@link TypeOfGrp} representing the type of input expected.
     * @param notice    The message to display to the user prompting for input.
     * @param condition The condition that the input must satisfy.
     * @return The input read from the user.
     * @throws IOException If an I/O error occurs while reading input.
     */
    public static String getInputFromUserForGroup(InputReader reader, TypeOfGrp type, String notice, String condition) throws IOException {
        String tmp = "";
        while (tmp.isEmpty()) {
            RainbowPrinter.printCondition(notice + '(' + condition + ')');
            try {
                String str = reader.readLine();
                boolean check = ObjInputChecker.checkInputForGroup(str, type);
                if (check) {
                    tmp = str;
                    break;
                } else {
                    throw new WrongInputException();
                }
            } catch (WrongInputException e) {
                RainbowPrinter.printError(e.toString());
            }
        }
        return tmp;
    }

    /**
     * Prompts the user for input related to a {@link Person} (group administrator) attribute.
     *
     * @param reader    The {@link InputReader} to use for reading input.
     * @param type      The {@link TypeOfPer} representing the type of input expected.
     * @param notice    The message to display to the user prompting for input.
     * @param condition The condition that the input must satisfy.
     * @return The input read from the user.
     * @throws IOException If an I/O error occurs while reading input.
     */
    public static String getInputFromUserForPerson(InputReader reader, TypeOfPer type, String notice, String condition) throws IOException {
        String tmp = "";
        while (tmp.isEmpty()) {
            RainbowPrinter.printInfo(notice + '(' + condition + ')');
            try {
                String str = reader.readLine();
                boolean check = ObjInputChecker.checkInputForPerson(str, type);
                if (check) {
                    tmp = str;
                    break;
                } else {
                    throw new WrongInputException();
                }
            } catch (WrongInputException e) {
                RainbowPrinter.printError(e.toString());
            }
        }
        return tmp;
    }

    /**
     * Executes the read mode by building a request based on console input.
     *
     * @param nameCommand The name of the command to execute.
     * @param arg         The argument for the command (can be null or empty).
     * @throws UserException  If an error occurs during command execution.
     * @throws LogException   If an error occurs during input/output operations.
     */
    @Override
    public void executeMode(String nameCommand, String arg) throws UserException, LogException {
        InputReader reader = new InputReader();
        reader.setReader();
        Request request = new Request(arg, build(reader));
        Invoker.call(nameCommand, request);
    }
}