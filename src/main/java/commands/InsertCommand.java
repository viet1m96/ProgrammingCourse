package commands;

import exceptions.user_exceptions.UserException;
import iostream.Receiver;
import packets.Request;

/**
 * The {@code InsertCommand} class represents a command to insert a new element into the collection with a specified key.
 * It extends the {@code Command} class and implements the {@code execute} method
 * to perform the insertion operation.
 */
public class InsertCommand extends Command {

    /**
     * Constructs an {@code InsertCommand} object with the name "insert", the argument "key {element}", and a description.
     */
    public InsertCommand() {
        super("insert", "key {element}", "add a new element with the specified key");
    }

    private Receiver receiver;

    /**
     * Sets the receiver for this command. The receiver is responsible for the actual insertion operation.
     * @param receiver The receiver object.
     */
    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Executes the insert command by calling the {@code insert} method on the receiver.
     *
     * @param request The request object containing the key and the element to be inserted.
     * @throws UserException If there is an error during the execution of the command related to user input or state.
     */
    @Override
    public void execute(Request request) throws UserException {
        receiver.insert(request);
    }
}
