package commands;

import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.UserException;
import goods.Response;
import handler.Receiver;
import goods.Request;

public class ClearCommand extends Command {

    public ClearCommand() {
        super("clear", "", "clear the collection");
    }

    private Receiver receiver;

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Response execute(Request request) throws UserException, LogException {
        return receiver.clear();
    }
}
