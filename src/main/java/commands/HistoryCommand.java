package commands;


import exceptions.user_exceptions.UserException;
import exceptions.user_exceptions.WrongInputException;
import iostream.Receiver;
import packets.Request;

public class HistoryCommand extends Command {
    public HistoryCommand() {
        super("history", "", "display the last 12 commands (without their arguments)");
    }
    private Receiver receiver;
    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute(Request request) throws UserException {
        if(request.getArgument() != null) throw new WrongInputException();
        receiver.history();
    }
}
