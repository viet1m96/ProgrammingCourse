package commands;

import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.UserException;
import exceptions.user_exceptions.WrongInputException;
import iostream.Receiver;
import packets.Request;

/**
 * The {@code InfoCommand} class represents a command that displays information about the collection,
 * such as its type, initialization date, and the number of elements it contains.
 * It extends the {@link Command} class and uses a {@link Receiver} object to perform the information retrieval and display.
 */
public class InfoCommand extends Command {

    /**
     * Constructs a new {@code InfoCommand} object.
     * The constructor sets the name, argument description (which is empty, as no arguments are required), and description of the command.
     */
    public InfoCommand() {
        super("info", "", "display information about the collection (type, initialization date, number of elements, etc.) to the console");
    }

    /**
     * The {@link Receiver} object used to retrieve and display the collection information.
     */
    private Receiver receiver;

    /**
     * Sets the {@link Receiver} object to be used by this command.
     *
     * @param receiver The {@link Receiver} object to set. This receiver will handle the retrieval and display of the collection information.
     */
    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Executes the info command.
     * This method calls the {@link Receiver#info()} method to retrieve and display the collection information.
     * It also checks for invalid input (an argument provided when none is expected).
     *
     * @param request The {@link Request} object containing the command and any arguments.
     * @throws UserException If the input is invalid (e.g., an argument is provided when not expected).
     * @throws LogException If an error occurs during logging within the {@link Receiver#info()} method.
     */
    @Override
    public void execute(Request request) throws UserException, LogException {
        if (request.getArgument() != null) {
            throw new WrongInputException();
        }
        receiver.info();
    }
}