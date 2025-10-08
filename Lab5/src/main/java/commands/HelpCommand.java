package commands;


import exceptions.user_exceptions.UserException;
import iostream.Receiver;
import packets.Request;

/**
 * The {@code HelpCommand} class represents a command to display information about available commands.
 * It extends the {@code Command} class and implements the {@code execute} method
 * to perform the help display operation.
 */
public class HelpCommand extends Command {

    /**
     * Constructs a {@code HelpCommand} object with the name "help", no arguments, and a description.
     */
    public HelpCommand() {
        super("help", "", "displays the information of available commands");
    }

    private Receiver receiver;

    /**
     * Sets the receiver for this command. The receiver is responsible for the actual help display operation.
     * @param receiver The receiver object.
     */
    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Executes the help command by calling the {@code help} method on the receiver.
     *
     * @param request The request object (not used in this command).
     * @throws UserException If there is an error during the execution of the command related to user input or state.
     */
    @Override
    public void execute(Request request) throws UserException {

        receiver.help();
    }
}
