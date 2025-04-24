package commands;

import goods.Request;
import goods.Response;
import handler.Receiver;

/**
 * The `SignOutCommand` class represents a command to sign out the current user.
 * It extends the `Command` class.
 */
public class SignOutCommand extends Command {
    /**
     * Constructs a `SignOutCommand` object.
     */
    public SignOutCommand() {
        super("sign_out", "", "sign out from current account");
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
     * Executes the sign-out command.
     *
     * @param request The request object (not used in this implementation).
     * @return Returns null as the sign-out command's logic might be handled elsewhere (e.g., in the receiver).
     */
    @Override
    public Response execute(Request request) {
        return null;
    }
}
