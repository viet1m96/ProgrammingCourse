package commands;

import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.UserException;
import exceptions.user_exceptions.WrongInputException;
import iostream.Receiver;
import packets.Request;

/**
 * The {@code ShowCommand} class represents a command that displays all elements of the collection
 * in a string representation to the standard output stream. It extends the {@link Command} class and
 * uses a {@link Receiver} object to perform the display operation.
 */
public class ShowCommand extends Command {

    /**
     * Constructs a new {@code ShowCommand} object.
     * The constructor sets the name, argument description (which is empty, as no arguments are required), and
     * description of the command.
     */
    public ShowCommand() {
        super("show", "", "display all elements of the collection in a string representation to the standard output stream");
    }

    /**
     * The {@link Receiver} object used to perform the display of the collection elements.
     */
    private Receiver receiver;

    /**
     * Sets the {@link Receiver} object to be used by this command.
     *
     * @param receiver The {@link Receiver} object to set. This receiver will handle the display of the collection.
     */
    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Executes the show command.
     * This method calls the {@link Receiver#show()} method to display the collection elements to the standard output.
     * It also checks for invalid input (an argument provided when none is expected).
     *
     * @param request The {@link Request} object containing the command and any arguments.
     * @throws UserException If the input is invalid (e.g., an argument is provided when not expected).
     * @throws LogException If there is an error during the display process (e.g., data access issues, formatting errors).
     */
    @Override
    public void execute(Request request) throws UserException, LogException {
        if (request.getArgument() != null) {
            throw new WrongInputException();
        }
        receiver.show();
    }
}