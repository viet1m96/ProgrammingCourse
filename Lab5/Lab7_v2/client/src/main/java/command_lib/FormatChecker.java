package command_lib;

import enums.TypeOfFormatCmd;
import exceptions.user_exceptions.InputFormatException;

import java.util.HashMap;

public class FormatChecker {
    private final HashMap<String, TypeOfFormatCmd> typeOfFormatCmdMap = new HashMap<>();

    public FormatChecker() {
    }

    public void init() {
        registerFormatCmd("insert", TypeOfFormatCmd.WITH_NUMERAL_ARG);
        registerFormatCmd("update", TypeOfFormatCmd.WITH_NUMERAL_ARG);
        registerFormatCmd("remove_key", TypeOfFormatCmd.WITH_NUMERAL_ARG);
        registerFormatCmd("replace_if_lower", TypeOfFormatCmd.WITH_NUMERAL_ARG);
        registerFormatCmd("execute_script", TypeOfFormatCmd.WITH_STRING_ARG);
        registerFormatCmd("remove_greater", TypeOfFormatCmd.WITHOUT_ARG);
        registerFormatCmd("help", TypeOfFormatCmd.WITHOUT_ARG);
        registerFormatCmd("info", TypeOfFormatCmd.WITHOUT_ARG);
        registerFormatCmd("show", TypeOfFormatCmd.WITHOUT_ARG);
        registerFormatCmd("clear", TypeOfFormatCmd.WITHOUT_ARG);
        registerFormatCmd("exit", TypeOfFormatCmd.WITHOUT_ARG);
        registerFormatCmd("history", TypeOfFormatCmd.WITHOUT_ARG);
        registerFormatCmd("print_ascending", TypeOfFormatCmd.WITHOUT_ARG);
        registerFormatCmd("print_descending", TypeOfFormatCmd.WITHOUT_ARG);
        registerFormatCmd("print_field_descending_semester_enum", TypeOfFormatCmd.WITHOUT_ARG);
        registerFormatCmd("sign_in", TypeOfFormatCmd.WITHOUT_ARG);
        registerFormatCmd("sign_out", TypeOfFormatCmd.WITHOUT_ARG);
        registerFormatCmd("sign_up", TypeOfFormatCmd.WITHOUT_ARG);
    }

    public void registerFormatCmd(String cmd, TypeOfFormatCmd type) {
        typeOfFormatCmdMap.put(cmd, type);
    }

    public void checkFormat(String cmd, String arg) throws InputFormatException {
        if (!typeOfFormatCmdMap.containsKey(cmd)) throw new InputFormatException();
        TypeOfFormatCmd cmdType = typeOfFormatCmdMap.get(cmd);
        switch (cmdType) {
            case WITH_NUMERAL_ARG:
                try {
                    int integer = Integer.parseInt(arg);
                    if (integer < 0) {
                        throw new InputFormatException();
                    }
                } catch (NumberFormatException e) {
                    throw new InputFormatException();
                }
                break;
            case WITH_STRING_ARG:
                if (arg == null || arg.isEmpty()) throw new InputFormatException();
                break;
            case WITHOUT_ARG:
                if (arg != null && !arg.isEmpty()) throw new InputFormatException();
                break;
        }
    }
}
