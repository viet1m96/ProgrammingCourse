package commands;

import exceptions.user_exceptions.UserException;
import iostream.Receiver;
import packets.Request;

/**
 * The {@code Remove_keyCommand} class represents a command that removes an element from the collection based on its key.
 * It extends the {@link Command} class and uses a {@link Receiver} object to perform the removal operation.
 */
public class Remove_keyCommand extends Command {

    /**
     * Constructs a new {@code Remove_keyCommand} object.
     * The constructor sets the name, argument description (specifying the key of the element to remove), and
     * description of the command.
     */
    public Remove_keyCommand() {
        super("remove_key", "key", "remove an element from the collection by its key");
    }

    /**
     * The {@link Receiver} object used to perform the removal of an element based on its key.
     */
    private Receiver receiver;

    /**
     * Sets the {@link Receiver} object to be used by this command.
     *
     * @param receiver The {@link Receiver} object to set. This receiver will handle the removal of the element.
     */
    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Executes the remove_key command.
     * This method calls the {@link Receiver#remove_key(Request)} method to remove the element with the key
     * contained in the {@link Request}.
     *
     * @param request The {@link Request} object containing the key of the element to remove.
     * @throws UserException If there's an issue during the removal process (e.g., invalid key format, element not found, data access errors).
     */
    @Override
    public void execute(Request request) throws UserException {
        receiver.remove_key(request);
    }
}