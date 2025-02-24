package read_mode;

import command_utilities.CommandClassifier;
import command_utilities.CommandManager;
import enums.NeedInput;
import enums.TypeOfGrp;
import enums.TypeOfPer;
import exceptions.LogUtil;
import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.KeyTakenException;
import exceptions.user_exceptions.UserException;
import exceptions.user_exceptions.WrongCommandException;
import exceptions.user_exceptions.WrongInputInFileException;
import io_utilities.printers.RainbowPrinter;
import io_utilities.working_with_input.*;
import iostream.Invoker;
import main_objects.StudyGroup;
import main_objects.Person;
import packets.Request;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * The {@code FileReaderMode} class implements the {@link ReadMode} interface and provides
 * functionality to read commands and data from a file and execute them.
 */
public class FileReaderMode implements ReadMode {

    private final CommandManager commandManager;
    private final CommandClassifier commandClassifier;
    private final RecursionController recursionController;

    /**
     * Constructs a new {@code FileReaderMode} object.
     * Initializes the {@link CommandManager}, {@link CommandClassifier}, and {@link RecursionController}.
     */
    public FileReaderMode() {
        commandManager = new CommandManager();
        commandClassifier = new CommandClassifier();
        recursionController = new RecursionController();
        commandManager.init();
        commandClassifier.init();
    }

    /**
     * Executes the file reading mode by reading commands and data from the specified file
     * and executing them using the provided {@link Invoker}.
     *
     * @param invoker     The {@link Invoker} object to execute commands.
     * @param nameCommand The name of the command to execute (not used in this implementation).
     * @param currentFile The path to the file to read commands from.
     * @throws UserException If the file contains invalid commands or data.
     * @throws LogException  If an I/O error occurs while reading the file.
     */
    @Override
    public void executeMode(Invoker invoker, String nameCommand, String currentFile) throws UserException, LogException {
        LinkedHashMap<String, String> currentTrace = new LinkedHashMap<>();
        currentTrace.put(currentFile, null);
        process(currentTrace, currentFile, invoker);
    }

    /**
     * Reads the next command from the input file.
     *
     * @param inputReader The {@link InputReader} object to read from the file.
     * @return The next command from the file, or {@code null} if the end of the file is reached.
     * @throws IOException          If an I/O error occurs while reading the file.
     * @throws UserException        If the command is invalid or the input is incorrect.
     * @throws LogException         If a logging error occurs.
     */
    public String getUserNextCommand(InputReader inputReader) throws IOException, UserException, LogException {
        String input = inputReader.readLine();
        if (input == null || input.isEmpty()) return null;
        if (!InputChecker.checkInput(input)) {
            throw new WrongInputInFileException();
        }
        if (!commandManager.isCommand(InputPartition.part1st(input.toLowerCase())))
            throw new WrongCommandException();
        return input;
    }

    /**
     * Builds a {@link StudyGroup} object by reading data from the input file.
     *
     * @param reader The {@link InputReader} object to read data from the file.
     * @return The constructed {@link StudyGroup} object.
     * @throws UserException If the file contains invalid data.
     * @throws LogException  If an I/O error occurs while reading the file.
     */
    public StudyGroup build(InputReader reader) throws UserException, LogException {
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
            adminInfo.add(getInputFromUserForPerson(reader, TypeOfPer.NAME));
            adminInfo.add(getInputFromUserForPerson(reader, TypeOfPer.BIRTHDAY));
            adminInfo.add(getInputFromUserForPerson(reader, TypeOfPer.WEIGHT));
            adminInfo.add(getInputFromUserForPerson(reader, TypeOfPer.COLOR));
            adminInfo.add(getInputFromUserForPerson(reader, TypeOfPer.X));
            adminInfo.add(getInputFromUserForPerson(reader, TypeOfPer.Y));
            adminInfo.add(getInputFromUserForPerson(reader, TypeOfPer.Z));
            adminInfo.add(getInputFromUserForPerson(reader, TypeOfPer.NAME));
        } catch (IOException e) {
            LogUtil.logStackTrace(e);
            throw new LogException();
        }
        return StudyGroupBuilder.parseStudyGroup(groupInfo, adminInfo);
    }

    /**
     * Reads a single input for a {@link StudyGroup} object from the input file.
     *
     * @param reader The {@link InputReader} object to read from the file.
     * @param type   The {@link TypeOfGrp} enum indicating the type of input to read.
     * @return The input read from the file.
     * @throws IOException   If an I/O error occurs while reading the file.
     * @throws UserException If the input is invalid.
     */
    public String getInputFromUserForGroup(InputReader reader, TypeOfGrp type) throws IOException, UserException {
        String str = reader.readLine();
        boolean check = ObjInputChecker.checkInputForGroupFile(str, type);
        if (check) {
            return str;
        } else {
            throw new WrongInputInFileException();
        }
    }

    /**
     * Reads a single input for a {@link Person} object from the input file.
     *
     * @param reader The {@link InputReader} object to read from the file.
     * @param type   The {@link TypeOfPer} enum indicating the type of input to read.
     * @return The input read from the file.
     * @throws IOException   If an I/O error occurs while reading the file.
     * @throws UserException If the input is invalid.
     */
    public String getInputFromUserForPerson(InputReader reader, TypeOfPer type) throws IOException, UserException {
        String str = reader.readLine();
        boolean check = ObjInputChecker.checkInputForPersonFile(str, type);
        if (check) {
            return str;
        } else {
            throw new WrongInputInFileException();
        }
    }

    /**
     * Processes the commands in the specified file.
     *
     * @param currentTrace A map to track the files currently being processed, used to detect recursion.
     * @param currentFile  The path to the file to process.
     * @param invoker      The {@link Invoker} object to execute commands.
     * @throws UserException If the file contains invalid commands or data, or if recursion is detected.
     * @throws LogException  If an I/O error occurs while reading the file.
     */
    public void process(LinkedHashMap<String, String> currentTrace, String currentFile, Invoker invoker) throws UserException, LogException {
        try {
            invoker.call("execute_script", new Request(null, null));
            InputReader inputReader = new InputReader();
            inputReader.setReader(currentFile);
            String inp;
            while ((inp = getUserNextCommand(inputReader)) != null) {
                String nameNewCommand = InputPartition.part1st(inp);
                String argument = InputPartition.part2nd(inp);
                NeedInput needInput = commandClassifier.getCommandClassifier(nameNewCommand);
                RainbowPrinter.printCondition("---" + " Current file: " + currentFile + " ---");
                switch (needInput) {
                    case NO_NEED_INPUT -> invoker.call(nameNewCommand, new Request(argument, null));
                    case NEED_INPUT -> {
                        if (!nameNewCommand.equals("execute_script")) {
                            StudyGroup studyGroup = build(inputReader);
                            try {
                                invoker.call(nameNewCommand, new Request(argument, studyGroup));
                            } catch (KeyTakenException e) {
                                RainbowPrinter.printError(e.toString());
                            }
                        } else {
                            if (recursionController.isRecursion(currentTrace, currentFile, argument)) {
                                if (recursionController.isFirstTimeDetected(currentTrace, currentFile, argument)) {
                                    Integer times = recursionController.askAction(currentTrace, currentFile, argument);
                                    if (times != null) {
                                        LinkedHashMap<String, String> newTrace = recursionController.getNewTrace(currentTrace, currentFile, argument);
                                        process(newTrace, argument, invoker);
                                    }
                                } else {
                                    if (!recursionController.stopOrNot(currentTrace, currentFile, argument)) {
                                        LinkedHashMap<String, String> newTrace = recursionController.getNewTrace(currentTrace, currentFile, argument);
                                        process(newTrace, argument, invoker);
                                    }
                                }
                            } else {
                                currentTrace.put(argument, currentFile);
                                process(currentTrace, argument, invoker);
                            }
                        }
                    }
                }
            }

        } catch (IOException e) {
            LogUtil.logStackTrace(e);
            throw new LogException();
        }
    }
}
