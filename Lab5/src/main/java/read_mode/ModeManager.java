package read_mode;

import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.UserException;
import iostream.Invoker;

import java.util.HashMap;

/**
 * The {@code ModeManager} class manages different reading modes for commands, such as reading from the console
 * or reading from a file.  It allows associating specific commands with specific {@link ReadMode} implementations.
 */
public class ModeManager {
    private final HashMap<String, ReadMode> readModes = new HashMap<>();

    /**
     * Initializes the {@code ModeManager} by registering the available reading modes for different commands.
     * The following commands are registered:
     * <ul>
     *     <li>"execute_script": {@link FileReaderMode} (for reading commands from a file)</li>
     *     <li>"insert": {@link ConsoleReaderMode} (for reading data from the console)</li>
     *     <li>"remove_greater": {@link ConsoleReaderMode} (for reading data from the console)</li>
     *     <li>"replace_if_lower": {@link ConsoleReaderMode} (for reading data from the console)</li>
     *     <li>"update": {@link ConsoleReaderMode} (for reading data from the console)</li>
     * </ul>
     */
    public void init() {
        registerReadMode("execute_script", new FileReaderMode());
        registerReadMode("insert", new ConsoleReaderMode());
        registerReadMode("remove_greater", new ConsoleReaderMode());
        registerReadMode("replace_if_lower", new ConsoleReaderMode());
        registerReadMode("update", new ConsoleReaderMode());
    }

    /**
     * Registers a {@link ReadMode} for a specific command.
     *
     * @param command  The name of the command.
     * @param readMode The {@link ReadMode} implementation to use for the command.
     */
    public void registerReadMode(String command, ReadMode readMode) {
        readModes.put(command, readMode);
    }

    /**
     * Calls the {@link ReadMode} associated with the specified command to execute the command.
     *
     * @param invoker     The {@link Invoker} object to execute the command.
     * @param nameCommand The name of the command to execute.
     * @param arguments   The arguments for the command.
     * @throws UserException If the command encounters a user-related error during execution.
     * @throws LogException  If the command encounters a logging-related error during execution.
     */
    public void call(Invoker invoker, String nameCommand, String arguments) throws UserException, LogException {
        readModes.get(nameCommand).executeMode(invoker, nameCommand, arguments);
    }
}
