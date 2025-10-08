package commands;

import exceptions.log_exceptions.LogException;
import iostream.Receiver;
import packets.Request;

/**
 * The {@code SaveCommand} class represents a command to save the collection to a file.
 * It extends the {@code Command} class and implements the {@code execute} method
 * to perform the save operation.
 */
public class SaveCommand extends Command {

    /**
     * Constructs a {@code SaveCommand} object with the name "save", no arguments,
     * and a description.
     */
    public SaveCommand() {
        super("save", "", "save the collection to a file");
    }

    private Receiver receiver;

    /**
     * Sets the receiver for this command. The receiver is responsible for the actual save operation.
     * @param receiver The receiver object.
     */
    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Executes the save command by calling the {@code save} method on the receiver.
     *
     * @param request The request object (not used in this command).
     * @throws LogException If there is an error during the execution of the command related to logging or file access.
     */
    @Override
    public void execute(Request request) throws LogException {
        receiver.save();
    }
}
