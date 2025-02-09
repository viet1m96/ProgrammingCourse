package commands;

import iostream.Receiver;
import packets.Request;

/**
 * The {@code Execute_scriptCommand} class represents a command that executes a script from a specified file.
 * The script should contain commands in the same format as they would be entered interactively.
 * It extends the {@link Command} class and uses a {@link Receiver} object to handle the script execution.
 */
public class Execute_scriptCommand extends Command {

    /**
     * Constructs a new {@code Execute_scriptCommand} object.
     * The constructor sets the name, argument description (filename), and description of the command.
     */
    public Execute_scriptCommand() {
        super("execute_script", "filename", "read and execute a script from the specified file. The script contains commands in the same form as the user enters them in interactive mode");
    }

    /**
     * The {@link Receiver} object used to execute the script.
     */
    private Receiver receiver;

    /**
     * Sets the {@link Receiver} object to be used by this command.
     *
     * @param receiver The {@link Receiver} object to set.  This receiver will handle the actual script execution.
     */
    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Executes the script command.
     * This method calls the {@link Receiver#execute_script()} method to execute the script.
     *
     * @param request The {@link Request} object (although it's currently not used in this implementation).  In a more complete implementation, the filename would likely be extracted from the {@code request}.
     */
    @Override
    public void execute(Request request) {
        receiver.execute_script();
    }
}