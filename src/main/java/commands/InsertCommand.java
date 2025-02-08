package commands;


import exceptions.user_exceptions.UserException;
import iostream.Receiver;
import packets.Request;


public class InsertCommand extends Command {
    public InsertCommand() {
        super("insert","key {element}", "add a new element with the specified key");
    }
    private Receiver receiver;
    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute(Request request) throws UserException {
        receiver.insert(request);
    }
}
