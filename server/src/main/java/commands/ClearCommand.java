package commands;

import exceptions.database_exception.PostgresException;
import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.UserException;
import goods.Request;
import goods.Response;
import handler.Receiver;

/**
 * The `ClearCommand` class represents a command to clear the collection.
 * It extends the `Command` class and implements the execution logic.
 */
public class ClearCommand extends Command {

    /**
     * Constructs a `ClearCommand` object.
     */
    public ClearCommand() {
        super("clear", "", "clear the collection");
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
     * Executes the clear command.
     *
     * @param request The request object containing the necessary information.
     * @return The response object containing the result of the command execution.
     * @throws UserException     If there is an error related to the user.
     * @throws LogException      If there is an error related to logging.
     * @throws PostgresException If there is an error related to the PostgreSQL database.
     */
    @Override
    public Response execute(Request request) throws UserException, LogException, PostgresException {
        return receiver.clear(request);
    }
}
