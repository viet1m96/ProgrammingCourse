package commands;


import exceptions.user_exceptions.UserException;
import iostream.Receiver;
import packets.Request;


public class Remove_greaterCommand extends Command {
    public Remove_greaterCommand() {
        super("remove_greater", "{element}", "remove all elements from the collection that exceed the specified one");
    }
    private Receiver receiver;
    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute(Request request) throws UserException {
        receiver.remove_if_greater(request);
    }
}
