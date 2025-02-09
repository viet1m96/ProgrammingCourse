package commands;

import exceptions.user_exceptions.UserException;
import exceptions.user_exceptions.WrongInputException;
import iostream.Receiver;
import packets.Request;

/**
 * The {@code Print_semesterEnumCommand} class represents a command that displays the values of the `semesterEnum`
 * field for all elements in the collection, in descending order.  It extends the {@link Command} class and uses a
 * {@link Receiver} object to perform the retrieval, sorting, and printing operations.
 */
public class Print_semesterEnumCommand extends Command {

    /**
     * Constructs a new {@code Print_semesterEnumCommand} object.
     * The constructor sets the name, argument description (which is empty, as no arguments are required), and description of the command.
     */
    public Print_semesterEnumCommand() {
        super("print_field_descending_semester_enum", "", "display the values of the semesterEnum field of all elements in descending order");
    }

    /**
     * The {@link Receiver} object used to retrieve, sort, and display the `semesterEnum` field values in descending order.
     */
    private Receiver receiver;

    /**
     * Sets the {@link Receiver} object to be used by this command.
     *
     * @param receiver The {@link Receiver} object to set. This receiver will handle the retrieval, sorting, and display of the `semesterEnum` values.
     */
    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Executes the print_semesterEnum command.
     * This method calls the {@link Receiver#print_field_descending_semester_num()} method to retrieve, sort, and
     * display the `semesterEnum` field values in descending order.  It also checks for invalid input (an argument
     * provided when none is expected).
     *
     * @param request The {@link Request} object containing the command and any arguments.
     * @throws UserException If the input is invalid (e.g., an argument is provided when not expected).
     */
    @Override
    public void execute(Request request) throws UserException {
        if (request.getArgument() != null) {
            throw new WrongInputException();
        }
        receiver.print_field_descending_semester_num();
    }
}