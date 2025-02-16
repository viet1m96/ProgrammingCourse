package commands;

import exceptions.log_exceptions.LogException;
import iostream.Receiver;
import packets.Request;

public class SaveCommand extends Command {

    public SaveCommand() {
        super("save", "", "save the collection to a file");
    }

    private Receiver receiver;

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute(Request request) throws LogException {
        receiver.save();
    }
}