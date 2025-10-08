package commands;

import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.NameTakenException;
import goods.Request;
import goods.Response;
import handler.Receiver;

/**
 * The `SignUpCommand` class represents a command to sign up a new user account.
 * It extends the `Command` class and implements the execution logic.
 */
public class SignUpCommand extends Command {

    /**
     * Constructs a `SignUpCommand` object.
     */
    public SignUpCommand() {
        super("sign_up", "", "sign_up_func");
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
     * Executes the sign-up command.
     *
     * @param request The request object containing the user's sign-up information.
     * @return The response object indicating the success or failure of the sign-up attempt.
     * @throws LogException       If there is an error related to logging the sign-up attempt.
     * @throws NameTakenException If the username is already taken.
     */
    @Override
    public Response execute(Request request) throws LogException, NameTakenException {
        return receiver.sign_up(request);
    }
}
