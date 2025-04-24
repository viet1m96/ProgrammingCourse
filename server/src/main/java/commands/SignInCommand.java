package commands;

import exceptions.log_exceptions.LogException;
import goods.Request;
import goods.Response;
import handler.Receiver;

/**
 * The `SignInCommand` class represents a command to sign in a user.
 * It extends the `Command` class and implements the execution logic.
 */
public class SignInCommand extends Command {

    /**
     * Constructs a `SignInCommand` object.
     */
    public SignInCommand() {
        super("sign_in", "", "sign in as an user.");
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
     * Executes the sign-in command.
     *
     * @param request The request object containing the user's sign-in credentials.
     * @return The response object indicating the success or failure of the sign-in attempt.
     * @throws LogException If there is an error related to logging the sign-in attempt.
     */
    @Override
    public Response execute(Request request) throws LogException {
        return receiver.sign_in(request);
    }
}
