package commands;

import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.UserException;
import exceptions.user_exceptions.WrongInputException;
import iostream.Receiver;
import packets.Request;

/**
 * The {@code ClearCommand} class represents a command that clears the collection of elements.
 * It extends the {@link Command} class and implements the {@link #execute(Request)} method
 * to perform the clearing operation using a {@link Receiver} object.
 */
public class ClearCommand extends Command {

    /**
     * Constructs a new {@code ClearCommand} object.
     * The constructor sets the name, argument description, and description of the command.
     */
    public ClearCommand() {
        super("clear", "", "clear the collection");
    }

    /**
     * The {@link Receiver} object used to perform the actual clearing operation on the collection.
     */
    private Receiver receiver;

    /**
     * Sets the {@link Receiver} object to be used by this command.
     *
     * @param receiver The {@link Receiver} object to set.  This receiver will handle the actual clearing of the collection.
     */
    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Executes the clear command.
     * This method clears the collection by calling the {@link Receiver#clear()} method.
     * It also checks for invalid input (an argument provided when none is expected).
     *
     * @param request The {@link Request} object containing the command and any arguments.
     * @throws UserException If the input is invalid (e.g., an argument is provided when not expected).
     * @throws LogException  If an error occurs during the logging process (if logging is implemented in {@code Receiver}).
     */
    @Override
    public void execute(Request request) throws UserException, LogException {
        if (request.getArgument() != null) {
            throw new WrongInputException();
        }
        receiver.clear();
    }
}