package commands;

import goods.Request;
import goods.Response;
import handler.Receiver;


public class Execute_scriptCommand extends Command {

    public Execute_scriptCommand() {
        super("execute_script", "filename", "read and execute a script from the specified file. The script contains commands in the same form as the user enters them in interactive mode");
    }

    private Receiver receiver;

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Response execute(Request request) {
        return null;
    }
}
