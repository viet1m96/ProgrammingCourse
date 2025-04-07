package commands;

import exceptions.database_exception.PostgresException;
import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.UserException;
import goods.Request;
import goods.Response;
import handler.Receiver;

public class ClearCommand extends Command {

    public ClearCommand() {
        super("clear", "", "clear the collection");
    }

    private Receiver receiver;

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Response execute(Request request) throws UserException, LogException, PostgresException {
        return receiver.clear(request);
    }
}
