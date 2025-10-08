package commands;

import exceptions.user_exceptions.UserException;
import goods.Request;
import goods.Response;
import handler.Receiver;

/**
 * The `Print_ascendingCommand` class represents a command to display the elements of the collection in ascending order.
 * It extends the `Command` class and implements the execution logic.
 */
public class Print_ascendingCommand extends Command {

    /**
     * Constructs a `Print_ascendingCommand` object.
     */
    public Print_ascendingCommand() {
        super("print_ascending", "", "display the elements of the collection in ascending order");
    }

    private Receiver receiver;

    /**
     * Sets the receiver for this command.
     *
     * @param receiver The receiver to set.
     */
    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Executes the print_ascending command.
     *
     * @param request The request object containing the necessary information.
     * @return The response object containing the elements of the collection in ascending order.
     * @throws UserException If there is an error related to the user.
     */
    @Override
    public Response execute(Request request) throws UserException {
        return receiver.print_ascending(request);
    }
}
