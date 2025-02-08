package commands;

import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.UserException;
import exceptions.user_exceptions.WrongInputException;
import iostream.Receiver;
import packets.Request;


public class ShowCommand extends Command {
    public ShowCommand() {
        super("show", "", "display all elements of the collection in a string representation to the standard output stream");
    }
    private Receiver receiver;
    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute(Request request) throws UserException, LogException {
        if(request.getArgument() != null) throw new WrongInputException();
        receiver.show();
    }
}
