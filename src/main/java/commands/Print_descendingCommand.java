package commands;

import exceptions.user_exceptions.UserException;
import exceptions.user_exceptions.WrongInputException;
import iostream.Receiver;
import packets.Request;

/**
 * The {@code Print_descendingCommand} class represents a command that displays the elements of the collection in descending order.
 * It extends the {@link Command} class and uses a {@link Receiver} object to perform the sorting and printing operations.
 */
public class Print_descendingCommand extends Command {

    /**
     * Constructs a new {@code Print_descendingCommand} object.
     * The constructor sets the name, argument description (which is empty, as no arguments are required), and description of the command.
     */
    public Print_descendingCommand() {
        super("print_descending", "", "display the elements of the collection in descending order");
    }

    /**
     * The {@link Receiver} object used to sort and display the collection elements in descending order.
     */
    private Receiver receiver;

    /**
     * Sets the {@link Receiver} object to be used by this command.
     *
     * @param receiver The {@link Receiver} object to set. This receiver will handle the sorting and display of the elements.
     */
    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Executes the print_descending command.
     * This method calls the {@link Receiver#print_descending()} method to sort and display the collection elements in descending order.
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
        receiver.print_descending();
    }
}