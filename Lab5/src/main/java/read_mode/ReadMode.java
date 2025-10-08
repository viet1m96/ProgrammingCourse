package read_mode;

import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.UserException;
import iostream.Invoker;

/**
 * The {@code ReadMode} interface defines the contract for different reading modes used by the application.
 * Implementing classes are responsible for reading input, processing it, and executing commands.
 */
public interface ReadMode {

    /**
     * Executes the reading mode, processing input and executing the specified command.
     *
     * @param invoker     The {@link Invoker} object used to execute commands.
     * @param nameCommand The name of the command to execute.
     * @param arg         The argument for the command.
     * @throws UserException If the reading mode encounters a user-related error.
     * @throws LogException  If the reading mode encounters a logging-related error.
     */
    void executeMode(Invoker invoker, String nameCommand, String arg) throws UserException, LogException;
}
