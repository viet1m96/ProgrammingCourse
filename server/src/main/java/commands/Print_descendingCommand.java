package commands;

import exceptions.user_exceptions.UserException;
import goods.Request;
import goods.Response;
import handler.Receiver;

public class Print_descendingCommand extends Command {

    public Print_descendingCommand() {
        super("print_descending", "", "display the elements of the collection in descending order");
    }

    private Receiver receiver;

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Response execute(Request request) throws UserException {
        return receiver.print_descending(request);
    }
}
