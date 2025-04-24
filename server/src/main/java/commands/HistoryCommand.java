package commands;

import goods.Request;
import goods.Response;
import handler.Receiver;


/**
 * The `HistoryCommand` class represents a command to display the last 12 commands entered.
 * It extends the `Command` class.
 */
public class HistoryCommand extends Command {

    /**
     * Constructs a `HistoryCommand` object.
     */
    public HistoryCommand() {
        super("history", "", "display the last 12 commands (without their arguments)");
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
     * Executes the history command.
     *
     * @param request The request object (not used in this implementation).
     * @return Returns null as the history command's logic might be handled elsewhere (e.g., in the receiver).
     */
    @Override
    public Response execute(Request request) {
        return null;
    }
}
