package read_mode;

import authorization_lib.Account;
import enums.TypeOfGrp;
import enums.TypeOfPer;
import exceptions.log_exceptions.LogException;
import exceptions.network_exception.NetworkException;
import exceptions.user_exceptions.UserException;
import exceptions.user_exceptions.WrongInputException;
import goods.*;
import input_validators.InputChecker;
import input_validators.ObjInputChecker;
import input_validators.StudyGroupBuilder;
import iostream.Renderer;
import logging.LogUtil;
import main_objects.StudyGroup;
import network.ClientTransporter;
import printer_options.RainbowPrinter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
;
import java.util.ArrayList;
import java.util.List;

public class ConsoleReaderMode implements ReadMode {

    public ConsoleReaderMode() {
    }


    public StudyGroup build(BufferedReader reader) throws UserException, LogException {
        List<String> groupInfo = new ArrayList<>();
        List<String> adminInfo = new ArrayList<>();
        groupInfo.add("0");
        try {
            RainbowPrinter.printInfo("Reading some group info...");
            groupInfo.add(getInputFromUserForGroup(reader, TypeOfGrp.STRING, "Name of the group", "Must not be empty"));
            groupInfo.add(getInputFromUserForGroup(reader, TypeOfGrp.X, "Coordinate X of this group", "Must be a number > -678 and not be empty"));
            groupInfo.add(getInputFromUserForGroup(reader, TypeOfGrp.Y, "Coordinate Y of this group", "Must be a number > -438 and not be empty"));
            groupInfo.add(java.time.LocalDate.now().toString());
            groupInfo.add(getInputFromUserForGroup(reader, TypeOfGrp.COUNT, "The amount of students in this group", "Must be an integer greater than 0"));
            groupInfo.add(getInputFromUserForGroup(reader, TypeOfGrp.COUNT, "The amount of expelled students in this group", "Must be an integer greater than 0"));
            groupInfo.add(getInputFromUserForGroup(reader, TypeOfGrp.EDU, "The format of this group", "Must be one of these following types: DISTANCE_EDUCATION, FULL_TIME_EDUCATION, EVENING_CLASSES(Not necessary)"));
            groupInfo.add(getInputFromUserForGroup(reader, TypeOfGrp.SEMESTER, "Which semester ", "Must be one of these following types: FIRST, SECOND, FIFTH, SIXTH, SEVENTH"));

            RainbowPrinter.printInfo("Reading some admin info...");
            adminInfo.add(getInputFromUserForPerson(reader, TypeOfPer.NAME, "Name of the admin", "Must not be empty"));
            adminInfo.add(getInputFromUserForPerson(reader, TypeOfPer.BIRTHDAY, "Birthday", "Must be in this form: YYYY-MM-DD(Not necessary)"));
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
            LogUtil.logTrace(e);
            throw new LogException();
        }
        return StudyGroupBuilder.parseStudyGroup(groupInfo, adminInfo);
    }

    public String getInputFromUserForGroup(BufferedReader reader, TypeOfGrp type, String notice, String condition) throws IOException {
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

    public String getInputFromUserForPerson(BufferedReader reader, TypeOfPer type, String notice, String condition) throws IOException {
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

    @Override
    public void executeMode(Renderer renderer, ClientTransporter clientTransporter, Account account, String nameCommand, String argCommand) throws UserException, LogException, NetworkException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<String> arguments = new ArrayList<>();
        arguments.add(argCommand);
        Request request = new Request(nameCommand, arguments, build(br), account.getToken());
        Response response = clientTransporter.transport(request);
        renderer.printResult(response);
        renderer.printNotice(response);
    }
}
