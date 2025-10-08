package commands;

import exceptions.user_exceptions.UserException;
import handler.Receiver;
import goods.*;

public class InsertCommand extends Command {

    public InsertCommand() {
        super("insert", "key {element}", "add a new element with the specified key");
    }

    private Receiver receiver;

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Response execute(Request request) throws UserException {
        return receiver.insert(request);
    }
}
