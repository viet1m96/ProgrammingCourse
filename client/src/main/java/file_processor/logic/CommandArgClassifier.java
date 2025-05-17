package file_processor.logic;

import enums.TypeOfFormatCmd;
import exceptions.user_exceptions.InputFormatException;

import java.util.HashMap;

public class CommandArgClassifier {
    private static final HashMap<String, TypeOfFormatCmd> typeOfFormatCmdMap = new HashMap<>();


    public static void init() {
        if(typeOfFormatCmdMap.isEmpty()) {
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
        }
    }
    public static void registerFormatCmd(String cmd, TypeOfFormatCmd cmdType) {
        typeOfFormatCmdMap.put(cmd, cmdType);
    }

    public static boolean checkFormat(String cmd, String arg) {
        if (!typeOfFormatCmdMap.containsKey(cmd)) {
            return false;
        }
        System.out.println(1);
        TypeOfFormatCmd cmdType = typeOfFormatCmdMap.get(cmd);
        switch (cmdType) {
            case WITH_NUMERAL_ARG:
                try {
                    int integer = Integer.parseInt(arg);
                    if (integer < 0) {
                        return false;
                    }
                } catch (NumberFormatException e) {
                    return false;
                }
                break;
            case WITH_STRING_ARG:
                if (arg == null || arg.isEmpty()) return false;
                break;
            case WITHOUT_ARG:
                if (arg != null && !arg.isEmpty()) return false;
                break;
        }
        return true;
    }

}
