package commands;


import exceptions.user_exceptions.UserException;
import iostream.Receiver;
import packets.Request;


public class Remove_keyCommand extends Command {
    public Remove_keyCommand() {
        super("remove_key", "key", "remove an element from the collection by its key");
    }
    private Receiver receiver;
    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute(Request request) throws UserException {
        receiver.remove_key(request);
    }
}
