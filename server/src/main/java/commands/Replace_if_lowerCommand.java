package commands;

import exceptions.database_exception.PostgresException;
import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.UserException;
import goods.Request;
import goods.Response;
import handler.Receiver;

/**
 * The {@code Replace_if_lowerCommand} class represents a command to replace the value associated with a given key
 * if the new value is less than the old value.
 * It extends the {@code Command} class and implements the {@code execute} method
 * to perform the replacement operation.
 */
public class Replace_if_lowerCommand extends Command {

    /**
     * Constructs a {@code Replace_if_lowerCommand} object with the name "replace_if_lower", the arguments "key {element}",
     * and a description.
     */
    public Replace_if_lowerCommand() {
        super("replace_if_lower", "search_key_with_element", "replace_if_lower_func");
    }

    private Receiver receiver;

    /**
     * Sets the receiver for this command. The receiver is responsible for the actual replacement operation.
     * @param receiver The receiver object.
     */
    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Executes the replace_if_lower command by calling the {@code replace_if_lower} method on the receiver.
     *
     * @param request The request object containing the key and the new element.
     * @throws UserException If there is an error during the execution of the command related to user input or state.
     */
    @Override
    public Response execute(Request request) throws UserException, LogException, PostgresException {
        return receiver.replace_if_lower(request);
    }
}
