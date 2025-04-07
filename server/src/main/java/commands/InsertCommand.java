package commands;

import exceptions.database_exception.PostgresException;
import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.UserException;
import goods.Request;
import goods.Response;
import handler.Receiver;

public class InsertCommand extends Command {

    public InsertCommand() {
        super("insert", "key {element}", "add a new element with the specified key");
    }

    private Receiver receiver;

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Response execute(Request request) throws UserException, LogException, PostgresException {
        return receiver.insert(request);
    }
}
