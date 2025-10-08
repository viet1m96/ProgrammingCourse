package commands;

import iostream.Receiver;
import packets.Request;

/**
 * The {@code Execute_scriptCommand} class represents a command to execute a script from a file.
 * It extends the {@code Command} class and implements the {@code execute} method
 * to perform the script execution operation.
 */
public class Execute_scriptCommand extends Command {

    /**
     * Constructs an {@code Execute_scriptCommand} object with the name "execute_script",
     * the argument "filename", and a description.
     */
    public Execute_scriptCommand() {
        super("execute_script", "filename", "read and execute a script from the specified file. The script contains commands in the same form as the user enters them in interactive mode");
    }

    private Receiver receiver;

    /**
     * Sets the receiver for this command.  The receiver is responsible for the actual script execution.
     * @param receiver The receiver object.
     */
    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Executes the execute_script command by calling the {@code execute_script} method on the receiver.
     *
     * @param request The request object (not used in this command).
     */
    @Override
    public void execute(Request request) {
        receiver.execute_script();
    }
}
