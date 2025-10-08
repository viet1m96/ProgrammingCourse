package commands;

import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.UserException;
import goods.Request;
import goods.Response;
import handler.Receiver;

/**
 * The {@code Remove_greaterCommand} class represents a command to remove all elements from the collection
 * that are greater than the specified element.
 * It extends the {@code Command} class and implements the {@code execute} method
 * to perform the removal operation.
 */
public class Remove_greaterCommand extends Command {

    /**
     * Constructs a {@code Remove_greaterCommand} object with the name "remove_greater", the argument "{element}",
     * and a description.
     */
    public Remove_greaterCommand() {
        super("remove_greater", "only_element", "remove_greater_func");
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
     * Executes the remove_greater command by calling the {@code remove_if_greater} method on the receiver.
     *
     * @param request The request object containing the element to compare against.
     * @throws UserException If there is an error during the execution of the command related to user input or state.
     */
    @Override
    public Response execute(Request request) throws LogException {

        return receiver.remove_if_greater(request);
    }
}
