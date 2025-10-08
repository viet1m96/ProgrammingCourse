package commands;

import exceptions.user_exceptions.UserException;
import goods.Request;
import goods.Response;
import handler.Receiver;

/**
 * The {@code Print_semesterEnumCommand} class represents a command to display the values of the semesterEnum field
 * of all elements in the collection in descending order.
 * It extends the {@code Command} class and implements the {@code execute} method
 * to perform the descending semesterEnum print operation.
 */
public class Print_semesterEnumCommand extends Command {

    /**
     * Constructs a {@code Print_semesterEnumCommand} object with the name "print_field_descending_semester_enum",
     * no arguments, and a description.
     */
    public Print_semesterEnumCommand() {
        super("print_field_descending_semester_enum", "", "print_field_descending_semester_enum_func");
    }

    private Receiver receiver;

    /**
     * Sets the receiver for this command. The receiver is responsible for the actual descending semesterEnum print operation.
     * @param receiver The receiver object.
     */
    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Executes the print_field_descending_semester_enum command by calling the {@code print_field_descending_semester_num} method on the receiver.
     *
     * @param request The request object (not used in this command).
     * @throws UserException If there is an error during the execution of the command related to user input or state.
     */
    @Override
    public Response execute(Request request) throws UserException {
        return receiver.print_field_descending_semester_enum(request);
    }
}
