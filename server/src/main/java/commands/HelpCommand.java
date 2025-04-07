package commands;


import exceptions.user_exceptions.UserException;
import goods.Request;
import goods.Response;
import handler.Receiver;

public class HelpCommand extends Command {

    public HelpCommand() {
        super("help", "", "displays the information of available commands");
    }

    private Receiver receiver;

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Response execute(Request request) throws UserException {
        return receiver.help(request);
    }
}
