package commands;

import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.UserException;
import exceptions.user_exceptions.WrongInputException;
import iostream.Receiver;
import packets.Request;

public class InfoCommand extends Command {

    public InfoCommand() {
        super("info", "", "display information about the collection (type, initialization date, number of elements, etc.) to the console");
    }

    private Receiver receiver;

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute(Request request) throws UserException, LogException {
        if (request.getArgument() != null) {
            throw new WrongInputException();
        }
        receiver.info();
    }
}