package commands;

import exceptions.user_exceptions.UserException;
import iostream.Receiver;
import packets.Request;

/**
 * The {@code HistoryCommand} class represents a command to display the last 12 commands executed.
 * It extends the {@code Command} class and implements the {@code execute} method
 * to perform the history display operation.
 */
public class HistoryCommand extends Command {

    /**
     * Constructs a {@code HistoryCommand} object with the name "history", no arguments, and a description.
     */
    public HistoryCommand() {
        super("history", "", "display the last 12 commands (without their arguments)");
    }

    private Receiver receiver;

    /**
     * Sets the receiver for this command. The receiver is responsible for the actual history display operation.
     * @param receiver The receiver object.
     */
    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Executes the history command by calling the {@code history} method on the receiver.
     *
     * @param request The request object (not used in this command).
     * @throws UserException If there is an error during the execution of the command related to user input or state.
     */
    @Override
    public void execute(Request request) throws UserException {

        receiver.history();
    }
}
