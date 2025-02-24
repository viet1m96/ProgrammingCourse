package commands;

import exceptions.user_exceptions.UserException;
import iostream.Receiver;
import packets.Request;

/**
 * The {@code Print_descendingCommand} class represents a command to display the elements of the collection in descending order.
 * It extends the {@code Command} class and implements the {@code execute} method
 * to perform the descending print operation.
 */
public class Print_descendingCommand extends Command {

    /**
     * Constructs a {@code Print_descendingCommand} object with the name "print_descending", no arguments, and a description.
     */
    public Print_descendingCommand() {
        super("print_descending", "", "display the elements of the collection in descending order");
    }

    private Receiver receiver;

    /**
     * Sets the receiver for this command. The receiver is responsible for the actual descending print operation.
     * @param receiver The receiver object.
     */
    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Executes the print_descending command by calling the {@code print_descending} method on the receiver.
     *
     * @param request The request object (not used in this command).
     * @throws UserException If there is an error during the execution of the command related to user input or state.
     */
    @Override
    public void execute(Request request) throws UserException {

        receiver.print_descending();
    }
}
