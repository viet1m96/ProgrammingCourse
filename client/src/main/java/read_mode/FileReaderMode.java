package read_mode;

import authorization_lib.Account;
import command_lib.CommandClassifier;
import enums.NeedInput;
import enums.TypeOfGrp;
import enums.TypeOfPer;
import exceptions.log_exceptions.LogException;
import exceptions.network_exception.NetworkException;
import exceptions.user_exceptions.UserException;
import exceptions.user_exceptions.WrongCommandException;
import exceptions.user_exceptions.WrongInputInFileException;
import goods.*;
import input_validators.*;
import iostream.Renderer;
import logging.LogUtil;
import main_objects.StudyGroup;
import network.ClientTransporter;
import printer_options.RainbowPrinter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class FileReaderMode implements ReadMode {

    private final CommandClassifier commandClassifier;
    private final RecursionController recursionController;

    public FileReaderMode() {
        commandClassifier = new CommandClassifier();
        recursionController = new RecursionController();
        commandClassifier.init();
    }


    @Override
    public void executeMode(Renderer renderer, ClientTransporter clientTransporter, Account account, String nameCommand, String currentFile) throws UserException, LogException, NetworkException {
        LinkedHashMap<String, String> currentTrace = new LinkedHashMap<>();
        currentTrace.put(currentFile, null);
        process(currentTrace, currentFile, account, clientTransporter, renderer);
    }

    public String getUserNextCommand(BufferedReader inputReader) throws IOException, UserException {
        String input = inputReader.readLine();
        if (input == null || input.isEmpty()) return null;
        if (!InputChecker.checkInput(input)) {
            throw new WrongInputInFileException();
        }
        if (!commandClassifier.isCommand(InputPartition.part1st(input.toLowerCase())))
            throw new WrongCommandException();
        return input;
    }

    public StudyGroup build(BufferedReader reader) throws UserException, LogException {
        List<String> groupInfo = new ArrayList<>();
        List<String> adminInfo = new ArrayList<>();
        groupInfo.add("0");
        try {
            groupInfo.add(getInputFromUserForGroup(reader, TypeOfGrp.STRING));
            groupInfo.add(getInputFromUserForGroup(reader, TypeOfGrp.X));
            groupInfo.add(getInputFromUserForGroup(reader, TypeOfGrp.Y));
            groupInfo.add(java.time.LocalDate.now().toString());
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
            LogUtil.logTrace(e);
            throw new LogException("Study Group Build failed");
        }
        return StudyGroupBuilder.parseStudyGroup(groupInfo, adminInfo);
    }

    public String getInputFromUserForGroup(BufferedReader reader, TypeOfGrp type) throws IOException, UserException {
        String str = reader.readLine();
        boolean check = ObjInputChecker.checkInputForGroupFile(str, type);
        if (check) {
            return str;
        } else {
            throw new WrongInputInFileException();
        }
    }

    public String getInputFromUserForPerson(BufferedReader reader, TypeOfPer type) throws IOException, UserException {
        String str = reader.readLine();
        boolean check = ObjInputChecker.checkInputForPersonFile(str, type);
        if (check) {
            return str;
        } else {
            throw new WrongInputInFileException();
        }
    }

    public void process(LinkedHashMap<String, String> currentTrace, String currentFile, Account account, ClientTransporter clientTransporter, Renderer renderer) throws UserException, LogException, NetworkException {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(currentFile)));
            String inp;
            while ((inp = getUserNextCommand(br)) != null) {
                String nameNewCommand = InputPartition.part1st(inp);
                String argument = InputPartition.part2nd(inp);
                List<String> arguments = new ArrayList<>();
                arguments.add(argument);
                NeedInput needInput = commandClassifier.getCommandClassifier(nameNewCommand);
                RainbowPrinter.printCondition("---" + " Current file: " + currentFile + " ---");
                switch (needInput) {
                    case NO_NEED_INPUT -> {
                        Response response = clientTransporter.transport(new Request(nameNewCommand, arguments, account.getToken()));
                        renderer.printResult(response);
                        renderer.printNotice(response);
                    }
                    case NEED_INPUT -> {
                        if (!nameNewCommand.equals("execute_script")) {
                            StudyGroup studyGroup = build(br);
                            Response response = clientTransporter.transport(new Request(nameNewCommand, arguments, studyGroup, account.getToken()));
                            renderer.printResult(response);
                            renderer.printNotice(response);
                        } else {
                            if (recursionController.isRecursion(currentTrace, currentFile, argument)) {
                                if (recursionController.isFirstTimeDetected(currentTrace, currentFile, argument)) {
                                    Integer times = recursionController.askAction(currentTrace, currentFile, argument);
                                    if (times != null) {
                                        LinkedHashMap<String, String> newTrace = recursionController.getNewTrace(currentTrace, currentFile, argument);
                                        process(newTrace, argument, account, clientTransporter, renderer);
                                    }
                                } else {
                                    if (!recursionController.stopOrNot(currentTrace, currentFile, argument)) {
                                        LinkedHashMap<String, String> newTrace = recursionController.getNewTrace(currentTrace, currentFile, argument);
                                        process(newTrace, argument, account, clientTransporter, renderer);
                                    }
                                }
                            } else {
                                currentTrace.put(argument, currentFile);
                                process(currentTrace, argument, account, clientTransporter, renderer);
                            }
                        }
                    }
                }
            }

        } catch (IOException e) {
            LogUtil.logTrace(e);
            throw new LogException("Reading file failed");
        }
    }
}
