package commands;

import exceptions.user_exceptions.UserException;
import iostream.Receiver;
import packets.Request;

/**
 * The {@code InsertCommand} class represents a command that adds a new element to the collection with a specified key.
 * It extends the {@link Command} class and utilizes a {@link Receiver} object to handle the insertion process.
 */
public class InsertCommand extends Command {

    /**
     * Constructs a new {@code InsertCommand} object.
     * The constructor sets the name, argument description (specifying the key and element), and description of the command.
     */
    public InsertCommand() {
        super("insert", "key {element}", "add a new element with the specified key");
    }

    /**
     * The {@link Receiver} object used to perform the insertion operation.
     */
    private Receiver receiver;

    /**
     * Sets the {@link Receiver} object to be used by this command.
     *
     * @param receiver The {@link Receiver} object to set. This receiver will handle the process of inserting a new element.
     */
    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Executes the insert command.
     * This method calls the {@link Receiver#insert(Request)} method to add a new element to the collection.
     * The {@link Request} object contains the key and element data.
     *
     * @param request The {@link Request} object containing the key and element data for the new element.
     * @throws UserException If there's an issue during the insertion process (e.g., invalid key format, element validation errors).
     */
    @Override
    public void execute(Request request) throws UserException {
        receiver.insert(request);
    }
}