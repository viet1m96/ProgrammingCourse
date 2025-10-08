package io_utilities.working_with_input;

import enums.TypeOfFormatCmd;
import exceptions.user_exceptions.InputFormatException;

import java.util.HashMap;

/**
 * The {@code FormatChecker} class is responsible for validating the format of user commands.
 * It checks whether a given command has the correct type of argument (or no argument) as defined in its internal mapping.
 */
public class FormatChecker {
    private final HashMap<String, TypeOfFormatCmd> typeOfFormatCmdMap = new HashMap<>();

    /**
     * Constructs a new {@code FormatChecker}.
     */
    public FormatChecker() {
    }

    /**
     * Initializes the command format map with predefined commands and their corresponding argument types.
     * This method registers each command with its expected format (e.g., with a numerical argument, with a string argument, or without any argument).
     */
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
        registerFormatCmd("save", TypeOfFormatCmd.WITHOUT_ARG);
        registerFormatCmd("exit", TypeOfFormatCmd.WITHOUT_ARG);
        registerFormatCmd("history", TypeOfFormatCmd.WITHOUT_ARG);
        registerFormatCmd("print_ascending", TypeOfFormatCmd.WITHOUT_ARG);
        registerFormatCmd("print_descending", TypeOfFormatCmd.WITHOUT_ARG);
        registerFormatCmd("print_field_descending_semester_enum", TypeOfFormatCmd.WITHOUT_ARG);
    }

    /**
     * Registers a command with its corresponding argument type in the command format map.
     *
     * @param cmd  The command to be registered.
     * @param type The {@link TypeOfFormatCmd} representing the expected argument type for the command.
     */
    public void registerFormatCmd(String cmd, TypeOfFormatCmd type) {
        typeOfFormatCmdMap.put(cmd, type);
    }

    /**
     * Checks if the given command and argument match the expected format.
     * It retrieves the expected argument type for the command from the command format map and validates the provided argument accordingly.
     *
     * @param cmd The command to be checked.
     * @param arg The argument provided with the command.
     * @throws InputFormatException If the command is not recognized or if the argument does not match the expected format.
     */
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
