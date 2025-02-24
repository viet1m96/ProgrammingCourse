package read_mode;

import enums.TypeOfGrp;
import enums.TypeOfPer;
import exceptions.LogUtil;
import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.UserException;
import exceptions.user_exceptions.WrongInputException;
import io_utilities.printers.RainbowPrinter;
import io_utilities.working_with_input.InputChecker;
import io_utilities.working_with_input.InputReader;
import io_utilities.working_with_input.ObjInputChecker;
import io_utilities.working_with_input.StudyGroupBuilder;
import iostream.Invoker;
import main_objects.Person;
import main_objects.StudyGroup;
import packets.Request;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code ConsoleReaderMode} class implements the {@link ReadMode} interface and provides
 * functionality to read input from the console and build a {@link StudyGroup} object.
 */
public class ConsoleReaderMode implements ReadMode {

    /**
     * Constructs a new {@code ConsoleReaderMode} object.
     */
    public ConsoleReaderMode() {
    }

    /**
     * Builds a {@link StudyGroup} object by reading input from the user via the console.
     *
     * @param reader The {@link InputReader} object to read input from the console.
     * @return The constructed {@link StudyGroup} object.
     * @throws UserException If the user provides invalid input.
     * @throws LogException  If an I/O error occurs while reading input.
     */
    public static StudyGroup build(InputReader reader) throws UserException, LogException {
        List<String> groupInfo = new ArrayList<>();
        List<String> adminInfo = new ArrayList<>();
        groupInfo.add("0");

        try {
            RainbowPrinter.printInfo("Reading some group info...");
            groupInfo.add(getInputFromUserForGroup(reader, TypeOfGrp.STRING, "Name of the group", "Must not be empty"));
            groupInfo.add(getInputFromUserForGroup(reader, TypeOfGrp.X, "Coordinate X of this group", "Must be a number > -678 and not be empty"));
            groupInfo.add(getInputFromUserForGroup(reader, TypeOfGrp.Y, "Coordinate Y of this group", "Must be a number > -438 and not be empty"));
            groupInfo.add(java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            groupInfo.add(getInputFromUserForGroup(reader, TypeOfGrp.COUNT, "The amount of students in this group", "Must be an integer greater than 0"));
            groupInfo.add(getInputFromUserForGroup(reader, TypeOfGrp.COUNT, "The amount of expelled students in this group", "Must be an integer greater than 0"));
            groupInfo.add(getInputFromUserForGroup(reader, TypeOfGrp.EDU, "The format of this group", "Must be one of these following types: DISTANCE_EDUCATION, FULL_TIME_EDUCATION, EVENING_CLASSES(Not necessary)"));
            groupInfo.add(getInputFromUserForGroup(reader, TypeOfGrp.SEMESTER, "Which semester ", "Must be one of these following types: FIRST, SECOND, FIFTH, SIXTH, SEVENTH"));

            RainbowPrinter.printInfo("Reading some admin info...");
            adminInfo.add(getInputFromUserForPerson(reader, TypeOfPer.NAME, "Name of the admin", "Must not be empty"));
            adminInfo.add(getInputFromUserForPerson(reader, TypeOfPer.BIRTHDAY, "Birthday", "Must be in this form: DD-MM-YYYY(Not necessary)"));
            adminInfo.add(getInputFromUserForPerson(reader, TypeOfPer.WEIGHT, "Weight", "Must be an integer greater than 0 (Not necessary)"));
            adminInfo.add(getInputFromUserForPerson(reader, TypeOfPer.COLOR, "eyeColor", "Must be one of these following types: RED, BLACK, BLUE, ORANGE, BROWN"));
            if (InputChecker.yesOrNo("share", "Location of admin:")) {
                adminInfo.add(getInputFromUserForPerson(reader, TypeOfPer.X, "Coordinate X of admin", "Must be an integer greater than 0"));
                adminInfo.add(getInputFromUserForPerson(reader, TypeOfPer.Y, "Coordinate Y of admin", "Must be an integer greater than 0"));
                adminInfo.add(getInputFromUserForPerson(reader, TypeOfPer.Z, "Coordinate Z of admin", "Must be an integer greater than 0"));
                adminInfo.add(getInputFromUserForPerson(reader, TypeOfPer.LOCATION, "The location of admin(where)", "Must not be empty(Not necessary)"));
            } else {
                for (int i = 0; i < 4; i++) adminInfo.add("null");
            }
        } catch (IOException e) {
            LogUtil.logStackTrace(e);
            throw new LogException();
        }
        return StudyGroupBuilder.parseStudyGroup(groupInfo, adminInfo);
    }

    /**
     * Gets input from the user for a {@link StudyGroup} object.
     *
     * @param reader    The {@link InputReader} object to read input from the console.
     * @param type      The {@link TypeOfGrp} enum representing the type of input to get.
     * @param notice    The message to display to the user.
     * @param condition The condition that the input must satisfy.
     * @return The input from the user.
     * @throws IOException If an I/O error occurs while reading input.
     */
    public static String getInputFromUserForGroup(InputReader reader, TypeOfGrp type, String notice, String condition) throws IOException {
        boolean check = false;
        String tmp = "";
        while (!check) {
            RainbowPrinter.printCondition(notice + '(' + condition + ')');
            try {
                String str = reader.readLine();
                check = ObjInputChecker.checkInputForGroupConsole(str, type);
                if (check) {
                    if (str == null || str.isEmpty()) {
                        tmp = "null";
                    } else {
                        tmp = str;
                    }
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
     * Gets input from the user for a {@link Person} object.
     *
     * @param reader    The {@link InputReader} object to read input from the console.
     * @param type      The {@link TypeOfPer} enum representing the type of input to get.
     * @param notice    The message to display to the user.
     * @param condition The condition that the input must satisfy.
     * @return The input from the user.
     * @throws IOException If an I/O error occurs while reading input.
     */
    public static String getInputFromUserForPerson(InputReader reader, TypeOfPer type, String notice, String condition) throws IOException {
        boolean check = false;
        String tmp = "";
        while (!check) {
            RainbowPrinter.printInfo(notice + '(' + condition + ')');
            try {
                String str = reader.readLine();
                check = ObjInputChecker.checkInputForPersonConsole(str, type);
                if (check) {
                    if (str == null || str.isEmpty()) {
                        tmp = "null";
                        break;
                    }
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
     * Executes the console reading mode by reading input from the console, building a {@link StudyGroup} object,
     * and calling the specified command with the constructed {@link Request}.
     *
     * @param invoker     The {@link Invoker} object to call the command.
     * @param nameCommand The name of the command to call.
     * @param arg         The argument for the command.
     * @throws UserException If the user provides invalid input.
     * @throws LogException  If an I/O error occurs while reading input.
     */
    @Override
    public void executeMode(Invoker invoker, String nameCommand, String arg) throws UserException, LogException {
        InputReader reader = new InputReader();
        reader.setReader();
        Request request = new Request(arg, build(reader));
        invoker.call(nameCommand, request);
    }
}
