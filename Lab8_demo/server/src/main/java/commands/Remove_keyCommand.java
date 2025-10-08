package commands;

import exceptions.database_exception.PostgresException;
import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.UserException;
import goods.Request;
import goods.Response;
import handler.Receiver;

/**
 * The {@code Remove_keyCommand} class represents a command to remove an element from the collection by its key.
 * It extends the {@code Command} class and implements the {@code execute} method
 * to perform the removal operation.
 */
public class Remove_keyCommand extends Command {

    /**
     * Constructs a {@code Remove_keyCommand} object with the name "remove_key", the argument "key",
     * and a description.
     */
    public Remove_keyCommand() {
        super("remove_key", "search_key_without_element", "remove_key_func");
    }

    private Receiver receiver;

    /**
     * Sets the receiver for this command. The receiver is responsible for the actual removal operation.
     * @param receiver The receiver object.
     */
    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Executes the remove_key command by calling the {@code remove_key} method on the receiver.
     *
     * @param request The request object containing the key of the element to be removed.
     * @throws UserException If there is an error during the execution of the command related to user input or state.
     */
    @Override
    public Response execute(Request request) throws UserException, LogException, PostgresException {
        return receiver.remove_key(request);
    }
}
