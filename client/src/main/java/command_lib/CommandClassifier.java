package command_lib;

import enums.NeedInput;

import java.util.HashMap;

/**
 * The {@code CommandClassifier} class is responsible for classifying commands and determining
 * whether they require input or not. It uses a HashMap to store the command-to-NeedInput mapping.
 */
public class CommandClassifier {

    private final HashMap<String, NeedInput> commandClassifiers = new HashMap<>();

    /**
     * Initializes the command classifiers by registering each command and its corresponding
     * NeedInput value.
     */
    public void init() {
        registerClassifier("help", NeedInput.NO_NEED_INPUT);
        registerClassifier("clear", NeedInput.NO_NEED_INPUT);
        registerClassifier("exit", NeedInput.NO_NEED_INPUT);
        registerClassifier("history", NeedInput.NO_NEED_INPUT);
        registerClassifier("info", NeedInput.NO_NEED_INPUT);
        registerClassifier("print_descending", NeedInput.NO_NEED_INPUT);
        registerClassifier("print_ascending", NeedInput.NO_NEED_INPUT);
        registerClassifier("print_field_descending_semester_enum", NeedInput.NO_NEED_INPUT);
        registerClassifier("remove_key", NeedInput.NO_NEED_INPUT);
        registerClassifier("show", NeedInput.NO_NEED_INPUT);
        registerClassifier("update", NeedInput.NEED_INPUT);
        registerClassifier("execute_script", NeedInput.NEED_INPUT);
        registerClassifier("insert", NeedInput.NEED_INPUT);
        registerClassifier("remove_greater", NeedInput.NEED_INPUT);
        registerClassifier("replace_if_lower", NeedInput.NEED_INPUT);
    }

    /**
     * Registers a command with its corresponding NeedInput value.
     *
     * @param command   The command to register.
     * @param needInput The NeedInput value for the command.
     */
    public void registerClassifier(String command, NeedInput needInput) {
        commandClassifiers.put(command, needInput);
    }

    /**
     * Retrieves the NeedInput value for a given command.
     *
     * @param command The command to retrieve the NeedInput value for.
     * @return The NeedInput value for the command, or null if the command is not registered.
     */
    public NeedInput getCommandClassifier(String command) {
        return commandClassifiers.get(command);
    }
    public boolean isCommand(String command) {
        return commandClassifiers.containsKey(command);
    }
}
