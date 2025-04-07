package commands;

import exceptions.log_exceptions.LogException;
import goods.Request;
import goods.Response;
import handler.Receiver;

public class SignInCommand extends Command {

    public SignInCommand() {
        super("sign_in", "", "sign in as an user.");
    }

    private Receiver receiver;

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Response execute(Request request) throws LogException {
        return receiver.sign_in(request);
    }
}
