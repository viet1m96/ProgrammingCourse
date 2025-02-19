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
import packets.Request;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FileReaderMode implements ReadMode {

    private CommandManager commandManager;
    private CommandClassifier commandClassifier;
    private RecursionController recursionController;
    public FileReaderMode() {
        commandManager = new CommandManager();
        commandClassifier = new CommandClassifier();
        recursionController = new RecursionController();
        commandManager.init();
        commandClassifier.init();
    }

    @Override
    public void executeMode(Invoker invoker, String nameCommand, String currentFile) throws UserException, LogException {
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
                            if(recursionController.controlRecursion(argument, currentFile)) {
                                executeMode(invoker, nameNewCommand, argument);
                            } else {
                                return;
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

    public static String getInputFromUserForGroup(InputReader reader, TypeOfGrp type) throws IOException, UserException {
        String str = reader.readLine();
        boolean check = ObjInputChecker.checkInputForGroupFile(str, type);
        if (check) {
            return str;
        } else {
            throw new WrongInputInFileException();
        }
    }

    public static String getInputFromUserForPerson(InputReader reader, TypeOfPer type) throws IOException, UserException {
        String str = reader.readLine();
        boolean check = ObjInputChecker.checkInputForPersonFile(str, type);
        if (check) {
            return str;
        } else {
            throw new WrongInputInFileException();
        }
    }


}