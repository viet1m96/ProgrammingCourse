package commands;

import exceptions.user_exceptions.UserException;
import exceptions.user_exceptions.WrongInputException;
import iostream.Receiver;
import packets.Request;

public class Print_descendingCommand extends Command {

    public Print_descendingCommand() {
        super("print_descending", "", "display the elements of the collection in descending order");
    }

    private Receiver receiver;

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute(Request request) throws UserException {

        receiver.print_descending();
    }
}