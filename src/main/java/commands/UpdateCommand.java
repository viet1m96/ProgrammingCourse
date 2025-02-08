package commands;


import exceptions.user_exceptions.UserException;
import iostream.Receiver;
import packets.Request;


public class UpdateCommand extends Command {
    public UpdateCommand() {
        super("update id {element}", "", "update the value of the collection element whose id is equal to the specified one");
    }
    private Receiver receiver;
    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute(Request request) throws UserException {
        receiver.update(request);
    }
}
