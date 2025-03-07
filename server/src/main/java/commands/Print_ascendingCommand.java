package commands;

import exceptions.user_exceptions.UserException;
import handler.Receiver;
import goods.*;

public class Print_ascendingCommand extends Command {

    public Print_ascendingCommand() {
        super("print_ascending", "", "display the elements of the collection in ascending order");
    }

    private Receiver receiver;

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Response execute(Request request) throws UserException {
        return receiver.print_ascending();
    }
}
