package read_mode;

import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.UserException;

/**
 * Defines the contract for reading and executing commands.  Implementations handle command input from various sources.
 */
public interface ReadMode {

    /**
     * Executes a command.
     *
     * @param nameCommand The command's name.
     * @param arg The command's arguments.
     * @throws UserException If user input is invalid.
     * @throws LogException If a logging or I/O error occurs.
     */
    void executeMode(String nameCommand, String arg) throws UserException, LogException;
}