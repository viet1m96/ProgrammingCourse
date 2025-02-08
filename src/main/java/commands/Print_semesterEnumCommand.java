package commands;


import exceptions.user_exceptions.UserException;
import exceptions.user_exceptions.WrongInputException;
import iostream.Receiver;
import packets.Request;


public class Print_semesterEnumCommand extends Command {
    public Print_semesterEnumCommand() {
        super("print_field_descending_semester_enum", "", "display the values of the semesterEnum field of all elements in descending order");
    }
    private Receiver receiver;
    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute(Request request) throws UserException {
        if(request.getArgument() != null) throw new WrongInputException();
        receiver.print_field_descending_semester_num();
    }
}
