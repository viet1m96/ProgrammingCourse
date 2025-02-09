package commands;

import exceptions.user_exceptions.UserException;
import exceptions.user_exceptions.WrongInputException;
import iostream.Receiver;
import packets.Request;

/**
 * The {@code HistoryCommand} class represents a command that displays the last 12 commands entered (without their arguments).
 * It extends the {@link Command} class and uses a {@link Receiver} object to perform the history retrieval and display.
 */
public class HistoryCommand extends Command {

    /**
     * Constructs a new {@code HistoryCommand} object.
     * The constructor sets the name, argument description (which is empty, as no arguments are required), and description of the command.
     */
    public HistoryCommand() {
        super("history", "", "display the last 12 commands (without their arguments)");
    }

    /**
     * The {@link Receiver} object used to retrieve and display the command history.
     */
    private Receiver receiver;

    /**
     * Sets the {@link Receiver} object to be used by this command.
     *
     * @param receiver The {@link Receiver} object to set. This receiver will handle the retrieval and display of the command history.
     */
    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Executes the history command.
     * This method calls the {@link Receiver#history()} method to retrieve and display the command history.
     * It also checks for invalid input (an argument provided when none is expected).
     *
     * @param request The {@link Request} object containing the command and any arguments.
     * @throws UserException If the input is invalid (e.g., an argument is provided when not expected).
     */
    @Override
    public void execute(Request request) throws UserException {
        if (request.getArgument() != null) {
            throw new WrongInputException();
        }
        receiver.history();
    }
}