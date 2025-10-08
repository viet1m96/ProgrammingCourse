package commands;

import goods.Request;
import goods.Response;
import handler.Receiver;

/**
 * The `Execute_scriptCommand` class represents a command to execute a script from a file.
 * It extends the `Command` class and implements the execution logic.
 */
public class Execute_scriptCommand extends Command {

    /**
     * Constructs an `Execute_scriptCommand` object.
     */
    public Execute_scriptCommand() {
        super("execute_script", "filename", "execute_script_func");
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
     * Executes the execute_script command.
     *
     * @param request The request object containing the necessary information.
     * @return The response object containing the result of the command execution.  Returns null as the execution is handled differently.
     */
    @Override
    public Response execute(Request request) {
        return null;
    }
}
