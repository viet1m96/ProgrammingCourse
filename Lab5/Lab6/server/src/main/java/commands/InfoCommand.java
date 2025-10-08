package commands;

import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.UserException;
import handler.Receiver;
import goods.*;

public class InfoCommand extends Command {


    public InfoCommand() {
        super("info", "", "display information about the collection (type, initialization date, number of elements, etc.) to the console");
    }

    private Receiver receiver;


    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }


    @Override
    public Response execute(Request request) throws UserException, LogException {
        return receiver.info();
    }
}
