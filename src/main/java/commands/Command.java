package commands;

import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.UserException;
import iostream.Receiver;
import packets.Request;
import io_utilities.printers.RainbowPrinter;

/**
 * The abstract {@code Command} class serves as the base class for all command implementations.
 * It defines the common properties and methods that all commands should have.
 */
public abstract class Command {

    private final String name;

    private final String argument;

    private final String description;

    /**
     * Constructs a {@code Command} object with the specified name, argument, and description.
     *
     * @param name        The name of the command.
     * @param argument    The argument(s) the command takes (can be empty string if no arguments).
     * @param description A brief description of what the command does.
     */
    public Command(String name, String argument, String description) {
        this.name = name;
        this.argument = argument;
        this.description = description;
    }

    /**
     * Sets the receiver for this command.  The receiver is the object that will perform the action
     * associated with the command. This is an abstract method that must be implemented by subclasses.
     *
     * @param receiver The receiver object.
     */
    public abstract void setReceiver(Receiver receiver);

    /**
     * Executes the command. This is an abstract method that must be implemented by subclasses.
     *
     * @param request The request object containing any necessary data for the command execution.
     * @throws UserException If there is an error during the execution of the command related to user input or state.
     * @throws LogException  If there is an error related to logging.
     */
    public abstract void execute(Request request) throws UserException, LogException;

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
     * Gets the argument(s) of the command.
     *
     * @return The argument(s) of the command.
     */
    public String getArgument() {
        return argument;
    }

    /**
     * Prints information about the command, including its name, argument(s), and description, to the console
     * using the {@code RainbowPrinter}.
     */
    public void getCommandInfo() {
        RainbowPrinter.printResult(getName() + " " + getArgument() + " : " + getDescription());
    }
}
