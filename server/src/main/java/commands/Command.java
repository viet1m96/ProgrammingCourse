package commands;

import exceptions.database_exception.PostgresException;
import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.UserException;
import goods.Request;
import goods.Response;
import handler.Receiver;

public abstract class Command {

    private final String name;

    private final String argument;

    private final String description;

    public Command(String name, String argument, String description) {
        this.name = name;
        this.argument = argument;
        this.description = description;
    }

    public abstract void setReceiver(Receiver receiver);

    public abstract Response execute(Request request) throws UserException, LogException, PostgresException;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getArgument() {
        return argument;
    }

    public String getCommandInfo() {
        StringBuilder builder = new StringBuilder();
        builder.append("(+)");
        builder.append(getName()).append(" ").append(getArgument()).append(" : ").append(getDescription());
        return builder.toString();
    }
}
