package commands;

import exceptions.log_exceptions.LogException;
import iostream.Receiver;
import packets.Request;

/**
 * The {@code SaveCommand} class represents a command that saves the current collection to a file.
 * It extends the {@link Command} class and utilizes a {@link Receiver} object to perform the saving operation.
 */
public class SaveCommand extends Command {

    /**
     * Constructs a new {@code SaveCommand} object.
     * The constructor sets the name, argument description (which is empty, as no arguments are required), and
     * description of the command.
     */
    public SaveCommand() {
        super("save", "", "save the collection to a file");
    }

    /**
     * The {@link Receiver} object used to perform the saving of the collection to a file.
     */
    private Receiver receiver;

    /**
     * Sets the {@link Receiver} object to be used by this command.
     *
     * @param receiver The {@link Receiver} object to set. This receiver will handle the saving of the collection.
     */
    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Executes the save command.
     * This method calls the {@link Receiver#save()} method to save the collection to a file.
     *
     * @param request The {@link Request} object (not used in this command, but required by the {@link Command} interface).
     * @throws LogException If there is an error during the saving process (e.g., file access issues, serialization errors).
     */
    @Override
    public void execute(Request request) throws LogException {
        receiver.save();
    }
}