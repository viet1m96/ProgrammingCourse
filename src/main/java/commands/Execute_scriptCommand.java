package commands;

import iostream.Receiver;
import packets.Request;

public class Execute_scriptCommand extends Command {

    public Execute_scriptCommand() {
        super("execute_script", "filename", "read and execute a script from the specified file. The script contains commands in the same form as the user enters them in interactive mode");
    }

    private Receiver receiver;

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute(Request request) {
        receiver.execute_script();
    }
}