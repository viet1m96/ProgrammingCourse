package read_mode;

import authorization_lib.Account;
import exceptions.log_exceptions.LogException;
import exceptions.network_exception.NetworkException;
import exceptions.user_exceptions.UserException;
import iostream.Renderer;
import network.ClientTransporter;

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


    public void call(Renderer renderer, ClientTransporter clientTransporter, Account account, String nameCommand, String arguments) throws UserException, LogException, NetworkException {
        readModes.get(nameCommand).executeMode(renderer, clientTransporter, account, nameCommand, arguments);
    }
}
