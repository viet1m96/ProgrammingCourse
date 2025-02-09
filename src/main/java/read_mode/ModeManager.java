package read_mode;

import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.UserException;

import java.util.HashMap;

/**
 * The {@code ModeManager} class manages different {@link ReadMode} implementations,
 * providing a way to execute commands based on their specified read mode.
 * It uses a HashMap to store the mapping between command names and their corresponding ReadMode instances.
 */
public class ModeManager {
    private static final HashMap<String, ReadMode> readModes = new HashMap<>();

    /**
     * Initializes the {@code readModes} HashMap with instances of specific {@link ReadMode} implementations
     * for different commands.
     */
    public static void init() {
        readModes.put("execute_script", new FileReaderMode());
        readModes.put("insert", new ConsoleReaderMode());
        readModes.put("remove_greater", new ConsoleReaderMode());
        readModes.put("replace_if_lower", new ConsoleReaderMode());
    }

    /**
     * Executes a command based on its associated {@link ReadMode}.
     *
     * @param nameCommand The name of the command to execute.  This is used as the key to retrieve the correct ReadMode from the {@code readModes} map.
     * @param arguments The arguments to pass to the {@code executeMode} method of the retrieved {@link ReadMode}.
     * @throws UserException If there is an issue with user-provided input during command execution.
     * @throws LogException If there is an issue related to logging or I/O operations during command execution.
     * @throws NullPointerException if the `nameCommand` does not have a corresponding {@link ReadMode} initialized.
     */
    public static void call(String nameCommand, String arguments) throws UserException, LogException {
        readModes.get(nameCommand).executeMode(nameCommand, arguments);
    }
}