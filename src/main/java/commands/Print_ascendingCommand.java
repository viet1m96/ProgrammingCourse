package commands;

import exceptions.user_exceptions.UserException;
import iostream.Receiver;
import packets.Request;

/**
 * The {@code Print_ascendingCommand} class represents a command to display the elements of the collection in ascending order.
 * It extends the {@code Command} class and implements the {@code execute} method
 * to perform the ascending print operation.
 */
public class Print_ascendingCommand extends Command {

    /**
     * Constructs a {@code Print_ascendingCommand} object with the name "print_ascending", no arguments, and a description.
     */
    public Print_ascendingCommand() {
        super("print_ascending", "", "display the elements of the collection in ascending order");
    }

    private Receiver receiver;

    /**
     * Sets the receiver for this command. The receiver is responsible for the actual ascending print operation.
     * @param receiver The receiver object.
     */
    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Executes the print_ascending command by calling the {@code print_ascending} method on the receiver.
     *
     * @param request The request object (not used in this command).
     * @throws UserException If there is an error during the execution of the command related to user input or state.
     */
    @Override
    public void execute(Request request) throws UserException {

        receiver.print_ascending();
    }
}
