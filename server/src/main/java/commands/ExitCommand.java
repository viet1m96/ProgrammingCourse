package commands;

import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.UserException;
import handler.Receiver;
import goods.*;

public class ExitCommand extends Command {

    public ExitCommand() {
        super("exit", "", "exit the program (without saving to a file)");
    }

    private Receiver receiver;

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Response execute(Request request) throws UserException, LogException {
        receiver.exit();
        return null;
    }
}
