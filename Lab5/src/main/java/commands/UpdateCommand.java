package commands;

import exceptions.user_exceptions.UserException;
import iostream.Receiver;
import packets.Request;

/**
 * The {@code UpdateCommand} class represents a command to update the value of the collection element
 * whose id is equal to the specified one.
 * It extends the {@code Command} class and implements the {@code execute} method
 * to perform the update operation.
 */
public class UpdateCommand extends Command {

    /**
     * Constructs a {@code UpdateCommand} object with the name "update id {element}", no arguments,
     * and a description.
     */
    public UpdateCommand() {
        super("update id {element}", "", "update the value of the collection element whose id is equal to the specified one");
    }

    private Receiver receiver;

    /**
     * Sets the receiver for this command. The receiver is responsible for the actual update operation.
     * @param receiver The receiver object.
     */
    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Executes the update command by calling the {@code update} method on the receiver.
     *
     * @param request The request object containing the id and the new element.
     * @throws UserException If there is an error during the execution of the command related to user input or state.
     */
    @Override
    public void execute(Request request) throws UserException {
        receiver.update(request);
    }
}
