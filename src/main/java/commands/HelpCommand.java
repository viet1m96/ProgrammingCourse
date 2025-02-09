package commands;


import exceptions.user_exceptions.UserException;
import exceptions.user_exceptions.WrongInputException;
import iostream.Receiver;
import packets.Request;

/**
 * The {@code HelpCommand} class represents a command that displays information about available commands.
 * It extends the {@link Command} class and utilizes a {@link Receiver} object to perform the help operation.
 */
public class HelpCommand extends Command {

    /**
     * Constructs a new {@code HelpCommand} object.
     * The constructor sets the name, argument description (which is empty, as no arguments are required), and description of the command.
     */
    public HelpCommand() {
        super("help", "", "displays the information of available commands");
    }

    /**
     * The {@link Receiver} object used to perform the help operation.
     */
    private Receiver receiver;

    /**
     * Sets the {@link Receiver} object to be used by this command.
     *
     * @param receiver The {@link Receiver} object to set. This receiver will handle the actual help information display.
     */
    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Executes the help command.
     * This method calls the {@link Receiver#help()} method to display the help information.
     * It also checks for invalid input (an argument provided when none is expected).
     *
     * @param request The {@link Request} object containing the command and any arguments.
     * @throws UserException If the input is invalid (e.g., an argument is provided when not expected).
     */
    @Override
    public void execute(Request request) throws UserException {
        if (request.getArgument() != null) {
            throw new WrongInputException();
        }
        receiver.help();
    }
}