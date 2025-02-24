package commands;

import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.UserException;
import iostream.Receiver;
import packets.Request;

/**
 * The {@code InfoCommand} class represents a command to display information about the collection.
 * It extends the {@code Command} class and implements the {@code execute} method
 * to perform the information display operation.
 */
public class InfoCommand extends Command {

    /**
     * Constructs an {@code InfoCommand} object with the name "info", no arguments, and a description.
     */
    public InfoCommand() {
        super("info", "", "display information about the collection (type, initialization date, number of elements, etc.) to the console");
    }

    private Receiver receiver;

    /**
     * Sets the receiver for this command. The receiver is responsible for the actual information display operation.
     * @param receiver The receiver object.
     */
    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Executes the info command by calling the {@code info} method on the receiver.
     *
     * @param request The request object (not used in this command).
     * @throws UserException If there is an error during the execution of the command related to user input or state.
     * @throws LogException  If there is an error related to logging.
     */
    @Override
    public void execute(Request request) throws UserException, LogException {

        receiver.info();
    }
}
