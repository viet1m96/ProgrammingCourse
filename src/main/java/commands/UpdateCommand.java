package commands;

import exceptions.user_exceptions.UserException;
import iostream.Receiver;
import packets.Request;

/**
 * The {@code UpdateCommand} class represents a command that updates the value of a collection element whose ID
 * is equal to the specified one. It extends the {@link Command} class and uses a {@link Receiver} object
 * to perform the update operation.
 */
public class UpdateCommand extends Command {

    /**
     * Constructs a new {@code UpdateCommand} object.
     * The constructor sets the name, argument description (specifying the ID of the element to update and the new element data),
     * and description of the command.
     */
    public UpdateCommand() {
        super("update id {element}", "", "update the value of the collection element whose id is equal to the specified one");
    }

    /**
     * The {@link Receiver} object used to perform the update of the collection element.
     */
    private Receiver receiver;

    /**
     * Sets the {@link Receiver} object to be used by this command.
     *
     * @param receiver The {@link Receiver} object to set. This receiver will handle the update operation.
     */
    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Executes the update command.
     * This method calls the {@link Receiver#update(Request)} method to update the element with the specified ID
     * using the new element data contained in the {@link Request}.
     *
     * @param request The {@link Request} object containing the ID of the element to update and the new element data.
     * @throws UserException If there's an issue during the update process (e.g., invalid ID format, element validation errors, data access errors, element not found).
     */
    @Override
    public void execute(Request request) throws UserException {
        receiver.update(request);
    }
}