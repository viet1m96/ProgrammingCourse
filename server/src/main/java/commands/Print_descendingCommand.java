package commands;

import exceptions.user_exceptions.UserException;
import goods.Request;
import goods.Response;
import handler.Receiver;

/**
 * The `Print_descendingCommand` class represents a command to display the elements of the collection in descending order.
 * It extends the `Command` class and implements the execution logic.
 */
public class Print_descendingCommand extends Command {

    /**
     * Constructs a `Print_descendingCommand` object.
     */
    public Print_descendingCommand() {
        super("print_descending", "", "display the elements of the collection in descending order");
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
     * Executes the print_descending command.
     *
     * @param request The request object containing the necessary information.
     * @return The response object containing the elements of the collection in descending order.
     * @throws UserException If there is an error related to the user.
     */
    @Override
    public Response execute(Request request) throws UserException {
        return receiver.print_descending(request);
    }
}
