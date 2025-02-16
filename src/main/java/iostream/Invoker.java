package iostream;

import command_utilities.CommandManager;
import commands.*;
import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.UserException;
import io_utilities.printers.RainbowPrinter;
import packets.Request;


public class Invoker {
    private CommandManager commandManager;
    private Receiver receiver;
    public Invoker(CommandManager commandManager, Receiver receiver) {
        this.commandManager = commandManager;
        this.receiver = receiver;
    }

    public void call(String nameCommand, Request request) throws UserException, LogException {
        RainbowPrinter.printCondition(">" + "The " + nameCommand + " command is being executed!");
        Command command = commandManager.getCommand(nameCommand);
        command.setReceiver(receiver);
        commandManager.pushHistory(commandManager.getCommand(nameCommand));
        command.execute(request);
    }

}