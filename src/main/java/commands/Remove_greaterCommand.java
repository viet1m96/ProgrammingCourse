package commands;

import exceptions.user_exceptions.UserException;
import exceptions.user_exceptions.WrongInputException;
import iostream.Receiver;
import packets.Request;

/**
 * The {@code Remove_greaterCommand} class represents a command that removes all elements from the collection
 * that are greater than a specified element.  It extends the {@link Command} class and uses a {@link Receiver}
 * object to perform the removal operation.
 */
public class Remove_greaterCommand extends Command {

    /**
     * Constructs a new {@code Remove_greaterCommand} object.
     * The constructor sets the name, argument description (specifying the element to compare against), and
     * description of the command.
     */
    public Remove_greaterCommand() {
        super("remove_greater", "{element}", "remove all elements from the collection that exceed the specified one");
    }

    /**
     * The {@link Receiver} object used to perform the removal of elements greater than the specified element.
     */
    private Receiver receiver;

    /**
     * Sets the {@link Receiver} object to be used by this command.
     *
     * @param receiver The {@link Receiver} object to set. This receiver will handle the removal of elements.
     */
    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Executes the remove_greater command.
     * This method calls the {@link Receiver#remove_if_greater(Request)} method to remove elements from the
     * collection that are greater than the element contained in the {@link Request}.
     *
     * @param request The {@link Request} object containing the element to compare against.
     * @throws UserException If there's an issue during the removal process (e.g., invalid element format, data access errors).
     */
    @Override
    public void execute(Request request) throws UserException {
        if (request.getArgument() != null) {
            throw new WrongInputException();
        }
        receiver.remove_if_greater(request);
    }
}