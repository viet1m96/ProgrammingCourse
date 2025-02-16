package commands;

import exceptions.user_exceptions.UserException;
import iostream.Receiver;
import packets.Request;

public class Replace_if_lowerCommand extends Command {

    public Replace_if_lowerCommand() {
        super("replace_if_lower", "key {element}", "replace the value by key if the new value is less than the old one");
    }

    private Receiver receiver;

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute(Request request) throws UserException {
        receiver.replace_if_lower(request);
    }
}