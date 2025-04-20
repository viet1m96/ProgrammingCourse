package commands;

import goods.Request;
import goods.Response;
import handler.Receiver;

public class SignOutCommand extends Command {
    public SignOutCommand() {
        super("sign_out", "", "sign out from current account");
    }

    private Receiver receiver;

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Response execute(Request request)  {
        return null;
    }
}
