package commands;

import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.UserException;
import iostream.Receiver;
import packets.Request;
import io_utilities.printers.RainbowPrinter;

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

    public abstract void execute(Request request) throws UserException, LogException;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getArgument() {
        return argument;
    }

    public void getCommandInfo() {
        RainbowPrinter.printResult(getName() + " " + getArgument() + " : " + getDescription());
    }
}