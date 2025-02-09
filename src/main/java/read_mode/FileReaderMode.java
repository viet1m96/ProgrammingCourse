package read_mode;

import command_utilities.CommandClassifier;
import command_utilities.CommandManager;
import enums.NeedInput;
import enums.TypeOfGrp;
import enums.TypeOfPer;
import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.KeyTakenException;
import exceptions.user_exceptions.UserException;
import exceptions.user_exceptions.WrongCommandException;
import exceptions.user_exceptions.WrongInputInFileException;
import io_utilities.printers.RainbowPrinter;
import io_utilities.working_with_input.*;
import iostream.Invoker;
import main_objects.StudyGroup;
import packets.Request;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the ReadMode interface and provides the functionality
 * to read and execute commands from a file.
 */
public class FileReaderMode implements ReadMode {

    /**
     * Default constructor for the FileReaderMode class.
     */
    public FileReaderMode() {
    }

    /**
     * Executes commands read from a file.
     *
     * @param nameCommand The name of the command to execute (not used in this method).
     * @param arg         The path to the file containing the commands.
     * @throws UserException If there is an issue related to user input.
     * @throws LogException  If an I/O error occurs.
     */
    @Override
    public void executeMode(String nameCommand, String arg) throws UserException, LogException {
        try {
            if (!RecursionController.controlRecursion(arg)) return;
            Invoker.call("execute_script", new Request(null, null));
            InputReader inputReader = new InputReader();
            inputReader.setReader(arg);
            String inp;
            while ((inp = getUserNextCommand(inputReader)) != null) {
                String name = InputPartition.part1st(inp);
                String argument = InputPartition.part2nd(inp);
                NeedInput needInput = CommandClassifier.getCommandClassifier(name);
                switch (needInput) {
                    case NO_NEED_INPUT -> Invoker.call(name, new Request(argument, null));
                    case NEED_INPUT -> {
                        if (!name.equals("execute_script")) {
                            StudyGroup studyGroup = build(inputReader);
                            try {
                                Invoker.call(name, new Request(argument, studyGroup));
                            } catch (KeyTakenException e) {
                                RainbowPrinter.printError(e.toString());
                            }
                        } else {
                            executeMode(nameCommand, arg);
                        }
                    }
                }
            }
            RecursionController.dropFileName(arg);
        } catch (IOException e) {
            e.printStackTrace();
            throw new LogException();
        }

    }

    /**
     * Reads the next command from the input file.
     *
     * @param inputReader The InputReader to read from the file.
     * @return The next command as a string, or null if the end of the file is reached.
     * @throws IOException    If an I/O error occurs while reading from the file.
     * @throws UserException  If the input is invalid or the command is not recognized.
     * @throws LogException   If an unexpected error occurs.
     */
    public String getUserNextCommand(InputReader inputReader) throws IOException, UserException, LogException {
        String input = inputReader.readLine();
        if (input == null || input.isEmpty()) return null;
        if (!InputChecker.checkInput(input)) {
            throw new WrongInputInFileException();
        }
        if (!CommandManager.isCommand(InputPartition.part1st(input.toLowerCase())))
            throw new WrongCommandException();
        return input;
    }

    /**
     * Builds a StudyGroup object by reading input from the provided InputReader.
     *
     * @param reader The InputReader to read the StudyGroup details from.
     * @return A newly constructed StudyGroup object.
     * @throws UserException If the input is invalid.
     * @throws LogException  If an I/O error occurs.
     */
    public static StudyGroup build(InputReader reader) throws UserException, LogException {
        List<String> groupInfo = new ArrayList<>();
        List<String> adminInfo = new ArrayList<>();
        groupInfo.add("0");
        try {
            groupInfo.add(getInputFromUserForGroup(reader, TypeOfGrp.STRING));
            groupInfo.add(getInputFromUserForGroup(reader, TypeOfGrp.X));
            groupInfo.add(getInputFromUserForGroup(reader, TypeOfGrp.Y));
            groupInfo.add(java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            groupInfo.add(getInputFromUserForGroup(reader, TypeOfGrp.COUNT));
            groupInfo.add(getInputFromUserForGroup(reader, TypeOfGrp.COUNT));
            groupInfo.add(getInputFromUserForGroup(reader, TypeOfGrp.EDU));
            groupInfo.add(getInputFromUserForGroup(reader, TypeOfGrp.SEMESTER));
            adminInfo.add(getInputFromUserForPerson(reader, TypeOfPer.STRING));
            adminInfo.add(getInputFromUserForPerson(reader, TypeOfPer.BIRTHDAY));
            adminInfo.add(getInputFromUserForPerson(reader, TypeOfPer.WEIGHT));
            adminInfo.add(getInputFromUserForPerson(reader, TypeOfPer.COLOR));
            adminInfo.add(getInputFromUserForPerson(reader, TypeOfPer.X));
            adminInfo.add(getInputFromUserForPerson(reader, TypeOfPer.Y));
            adminInfo.add(getInputFromUserForPerson(reader, TypeOfPer.Z));
            adminInfo.add(getInputFromUserForPerson(reader, TypeOfPer.STRING));
        } catch (IOException e) {
            e.printStackTrace();
            throw new LogException();
        }
        return new StudyGroup(groupInfo, adminInfo);
    }

    /**
     * Reads input from the user for a specific StudyGroup field.
     *
     * @param reader The InputReader to read from.
     * @param type   The TypeOfGrp enum representing the field to read.
     * @return The input string read from the reader.
     * @throws IOException   If an I/O error occurs.
     * @throws UserException If the input is invalid.
     */
    public static String getInputFromUserForGroup(InputReader reader, TypeOfGrp type) throws IOException, UserException {
        String str = reader.readLine();
        boolean check = ObjInputChecker.checkInputForGroup(str, type);
        if (check) {
            return str;
        } else {
            throw new WrongInputInFileException();
        }
    }

    /**
     * Reads input from the user for a specific Person field.
     *
     * @param reader The InputReader to read from.
     * @param type   The TypeOfPer enum representing the field to read.
     * @return The input string read from the reader.
     * @throws IOException   If an I/O error occurs.
     * @throws UserException If the input is invalid.
     */
    public static String getInputFromUserForPerson(InputReader reader, TypeOfPer type) throws IOException, UserException {
        String str = reader.readLine();
        boolean check = ObjInputChecker.checkInputForPerson(str, type);
        if (check) {
            return str;
        } else {
            throw new WrongInputInFileException();
        }
    }


}