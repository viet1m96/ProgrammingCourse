package commands;

import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.UserException;
import iostream.Receiver;
import packets.Request;
import io_utilities.printers.RainbowPrinter;

/**
 *  The abstract {@code Command} class serves as the base class for all command implementations.
 *  It defines the common properties of a command, such as name, argument description, and description,
 *  and provides abstract methods for setting the receiver and executing the command.
 */
public abstract class Command {

    /**
     * The name of the command.
     */
    private String name;

    /**
     * A description of the argument(s) that the command accepts (e.g., "<key>").  It can be an empty string if no arguments are needed.
     */
    private String argument;

    /**
     * A detailed description of what the command does.
     */
    private String description;

    /**
     * Constructs a new {@code Command} object.
     *
     * @param name        The name of the command.
     * @param argument    A description of the argument(s) that the command accepts.
     * @param description A detailed description of what the command does.
     */
    public Command(String name, String argument, String description) {
        this.name = name;
        this.argument = argument;
        this.description = description;
    }

    /**
     * Abstract method to set the {@link Receiver} object that will perform the actual command execution.
     * Subclasses must implement this method to provide a specific receiver.
     *
     * @param receiver The {@link Receiver} object to set.
     */
    public abstract void setReceiver(Receiver receiver);

    /**
     * Abstract method to execute the command.
     * Subclasses must implement this method to provide the specific command logic.
     *
     * @param request The {@link Request} object containing any necessary information for executing the command.
     * @throws UserException If there's an error related to user input or command usage.
     * @throws LogException  If there's an error during logging.
     */
    public abstract void execute(Request request) throws UserException, LogException;

    /**
     * Returns the name of the command.
     *
     * @return The name of the command.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the description of the command.
     *
     * @return The description of the command.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the argument description of the command.
     *
     * @return The argument description of the command.
     */
    public String getArgument() {
        return argument;
    }

    /**
     * Prints information about the command, including its name, arguments, and description, using the {@link RainbowPrinter}.
     */
    public void getCommandInfo() {
        RainbowPrinter.printResult(getName() + " " + getArgument() + " : " + getDescription());
    }
}