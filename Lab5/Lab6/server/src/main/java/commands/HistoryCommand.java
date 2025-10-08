package commands;

import exceptions.user_exceptions.UserException;
import goods.Response;
import handler.Receiver;
import goods.Request;


public class HistoryCommand extends Command {

    public HistoryCommand() {
        super("history", "", "display the last 12 commands (without their arguments)");
    }

    private Receiver receiver;

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Response execute(Request request)  {
        return null;
    }
}
