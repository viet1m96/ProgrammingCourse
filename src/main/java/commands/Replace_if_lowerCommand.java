package commands;

import exceptions.user_exceptions.UserException;
import iostream.Receiver;
import packets.Request;

/**
 * The {@code Replace_if_lowerCommand} class represents a command that replaces the value of an element with the given key
 * if the new element's value is less than the old one. It extends the {@link Command} class and uses a
 * {@link Receiver} object to perform the replacement operation.
 */
public class Replace_if_lowerCommand extends Command {

    /**
     * Constructs a new {@code Replace_if_lowerCommand} object.
     * The constructor sets the name, argument description (specifying the key of the element to replace and the new element itself),
     * and description of the command.
     */
    public Replace_if_lowerCommand() {
        super("replace_if_lower", "key {element}", "replace the value by key if the new value is less than the old one");
    }

    /**
     * The {@link Receiver} object used to perform the replacement of the element if the new value is lower.
     */
    private Receiver receiver;

    /**
     * Sets the {@link Receiver} object to be used by this command.
     *
     * @param receiver The {@link Receiver} object to set. This receiver will handle the replacement logic.
     */
    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Executes the replace_if_lower command.
     * This method calls the {@link Receiver#replace_if_lower(Request)} method to replace the element with the specified
     * key if the new element contained in the {@link Request} is lower than the existing element.
     *
     * @param request The {@link Request} object containing the key of the element to replace and the new element.
     * @throws UserException If there's an issue during the replacement process (e.g., invalid key format, element validation errors, data access errors).
     */
    @Override
    public void execute(Request request) throws UserException {
        receiver.replace_if_lower(request);
    }
}