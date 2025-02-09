package command_utilities;

import enums.NeedInput;

import java.util.HashMap;

/**
 *  The {@code CommandClassifier} class is responsible for classifying commands
 *  and determining whether they require additional input.
 *  It uses a HashMap to store commands and their corresponding input requirements.
 */
public class CommandClassifier {

    /**
     * A HashMap that stores commands as keys and their corresponding {@link NeedInput} values.
     * This map is used to determine whether a command requires additional input from the user.
     */
    private static HashMap<String, NeedInput> commandClassifiers = new HashMap<>();

    /**
     * Initializes the command classifiers with predefined commands and their input requirements.
     * This method populates the {@code commandClassifiers} HashMap with command names and
     * their corresponding {@link NeedInput} enum values, indicating whether each command
     * requires input or not.
     */
    public static void init() {
        commandClassifiers.put("help", NeedInput.NO_NEED_INPUT);
        commandClassifiers.put("clear", NeedInput.NO_NEED_INPUT);
        commandClassifiers.put("exit", NeedInput.NO_NEED_INPUT);
        commandClassifiers.put("history", NeedInput.NO_NEED_INPUT);
        commandClassifiers.put("info", NeedInput.NO_NEED_INPUT);
        commandClassifiers.put("print_descending", NeedInput.NO_NEED_INPUT);
        commandClassifiers.put("print_ascending", NeedInput.NO_NEED_INPUT);
        commandClassifiers.put("print_field_descending_semester_enum", NeedInput.NO_NEED_INPUT);
        commandClassifiers.put("remove_key", NeedInput.NO_NEED_INPUT);
        commandClassifiers.put("save", NeedInput.NO_NEED_INPUT);
        commandClassifiers.put("show", NeedInput.NO_NEED_INPUT);
        commandClassifiers.put("update", NeedInput.NEED_INPUT); // Changed to NEED_INPUT as update usually requires input
        commandClassifiers.put("execute_script", NeedInput.NEED_INPUT);
        commandClassifiers.put("insert", NeedInput.NEED_INPUT);
        commandClassifiers.put("remove_greater", NeedInput.NEED_INPUT);
        commandClassifiers.put("replace_if_lower", NeedInput.NEED_INPUT);
    }

    /**
     * Retrieves the input requirement for a given command.
     *
     * @param command The command to classify.
     * @return The {@link NeedInput} enum value representing the input requirement for the command.
     *         Returns {@code null} if the command is not found in the classifier.
     */
    public static NeedInput getCommandClassifier(String command) {
        return commandClassifiers.get(command);
    }
}