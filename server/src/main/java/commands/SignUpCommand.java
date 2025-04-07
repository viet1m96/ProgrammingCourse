package commands;

import exceptions.log_exceptions.LogException;
import goods.Request;
import goods.Response;
import handler.Receiver;

public class SignUpCommand extends Command {

    public SignUpCommand() {
        super("sign_up", "", "sign up a new account.");
    }

    private Receiver receiver;

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Response execute(Request request) throws LogException {
        return receiver.sign_up(request);
    }
}
