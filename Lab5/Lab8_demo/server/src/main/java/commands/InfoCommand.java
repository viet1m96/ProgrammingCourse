package commands;

import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.UserException;
import goods.Request;
import goods.Response;
import handler.Receiver;

/**
 * The `InfoCommand` class represents a command to display information about the collection.
 * It extends the `Command` class and implements the execution logic.
 */
public class InfoCommand extends Command {


    /**
     * Constructs an `InfoCommand` object.
     */
    public InfoCommand() {
        super("info", "", "info_func");
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
     * Executes the info command.
     *
     * @param request The request object containing the necessary information.
     * @return The response object containing the information about the collection.
     * @throws UserException If there is an error related to the user.
     * @throws LogException  If there is an error related to logging.
     */
    @Override
    public Response execute(Request request) throws UserException, LogException {
        return receiver.info(request);
    }
}
