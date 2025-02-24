package commands;

import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.UserException;
import iostream.Receiver;
import packets.Request;

/**
 * The {@code ClearCommand} class represents a command to clear the collection.
 * It extends the {@code Command} class and implements the {@code execute} method
 * to perform the clearing operation.
 */
public class ClearCommand extends Command {

    /**
     * Constructs a {@code ClearCommand} object with the name "clear", no arguments, and a description.
     */
    public ClearCommand() {
        super("clear", "", "clear the collection");
    }

    private Receiver receiver;

    /**
     * Sets the receiver for this command.  The receiver is responsible for the actual clearing operation.
     * @param receiver The receiver object.
     */
    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Executes the clear command by calling the {@code clear} method on the receiver.
     *
     * @param request The request object (not used in this command).
     * @throws UserException If there is an error during the execution of the command related to user input or state.
     * @throws LogException  If there is an error related to logging.
     */
    @Override
    public void execute(Request request) throws UserException, LogException {
        receiver.clear();
    }
}
