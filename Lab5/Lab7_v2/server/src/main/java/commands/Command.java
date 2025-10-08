package commands;

import exceptions.database_exception.PostgresException;
import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.UserException;
import goods.Request;
import goods.Response;
import handler.Receiver;

/**
 * The `Command` abstract class serves as a base class for all command objects.
 * It defines the common properties and methods for commands.
 */
public abstract class Command {

    private final String name;

    private final String argument;

    private final String description;

    /**
     * Constructs a `Command` object.
     *
     * @param name        The name of the command.
     * @param argument    The argument of the command.
     * @param description The description of the command.
     */
    public Command(String name, String argument, String description) {
        this.name = name;
        this.argument = argument;
        this.description = description;
    }

    /**
     * Sets the receiver for this command.  This method must be implemented by subclasses.
     *
     * @param receiver The receiver to set.
     */
    public abstract void setReceiver(Receiver receiver);

    /**
     * Executes the command. This method must be implemented by subclasses.
     *
     * @param request The request object containing the necessary information.
     * @return The response object containing the result of the command execution.
     * @throws UserException     If there is an error related to the user.
     * @throws LogException      If there is an error related to logging.
     * @throws PostgresException If there is an error related to the PostgreSQL database.
     */
    public abstract Response execute(Request request) throws UserException, LogException, PostgresException;

    /**
     * Gets the name of the command.
     *
     * @return The name of the command.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the description of the command.
     *
     * @return The description of the command.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the argument of the command.
     *
     * @return The argument of the command.
     */
    public String getArgument() {
        return argument;
    }

    /**
     * Gets the command information in a formatted string.
     *
     * @return The command information.
     */
    public String getCommandInfo() {
        StringBuilder builder = new StringBuilder();
        builder.append("(+)");
        builder.append(getName()).append(" ").append(getArgument()).append(" : ").append(getDescription());
        return builder.toString();
    }
}
