package commands;

import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.UserException;
import handler.Receiver;
import goods.*;

/**
 * The {@code ShowCommand} class represents a command to display all elements of the collection
 * in a string representation to the standard output stream.
 * It extends the {@code Command} class and implements the {@code execute} method
 * to perform the show operation.
 */
public class ShowCommand extends Command {

    /**
     * Constructs a {@code ShowCommand} object with the name "show", no arguments,
     * and a description.
     */
    public ShowCommand() {
        super("show", "", "display all elements of the collection in a string representation to the standard output stream");
    }

    private Receiver receiver;

    /**
     * Sets the receiver for this command. The receiver is responsible for the actual show operation.
     * @param receiver The receiver object.
     */
    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Executes the show command by calling the {@code show} method on the receiver.
     *
     * @param request The request object (not used in this command).
     * @throws UserException If there is an error during the execution of the command related to user input or state.
     * @throws LogException If there is an error during the execution of the command related to logging.
     */
    @Override
    public Response execute(Request request) throws UserException, LogException {
        return receiver.show();
    }
}
