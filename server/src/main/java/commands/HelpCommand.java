package commands;


import exceptions.user_exceptions.UserException;
import goods.Request;
import goods.Response;
import handler.Receiver;

/**
 * The `HelpCommand` class represents a command to display information about available commands.
 * It extends the `Command` class and implements the execution logic.
 */
public class HelpCommand extends Command {

    /**
     * Constructs a `HelpCommand` object.
     */
    public HelpCommand() {
        super("help", "", "help_func");
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
     * Executes the help command.
     *
     * @param request The request object containing the necessary information.
     * @return The response object containing the information about available commands.
     * @throws UserException If there is an error related to the user.
     */
    @Override
    public Response execute(Request request) throws UserException {
        return receiver.help(request);
    }
}
