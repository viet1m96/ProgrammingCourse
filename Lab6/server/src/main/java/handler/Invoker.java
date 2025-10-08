package handler;

import commands.Command;
import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.UserException;
import logging.LogUtil;
import goods.*;
import printer_options.RainbowPrinter;

import java.net.SocketAddress;

public class Invoker {
    private final CommandManager commandManager;
    private final Receiver receiver;

    public Invoker(CommandManager commandManager, Receiver receiver) {
        this.commandManager = commandManager;
        this.receiver = receiver;
    }

    public Response call(SocketAddress port, Request request) throws UserException, LogException {
        if(port == null) {
            RainbowPrinter.printCondition(">" + "The " + request.getCmd() + " command is requested!");
            LogUtil.logInfo("The " + request.getCmd() + " command is requested!");
        } else {
            RainbowPrinter.printCondition(">" + "The " + request.getCmd() + " command is requested by " + port);
            LogUtil.logInfo("The " + request.getCmd() + " command is requested by " + port);
        }
        Command command = commandManager.getCommand(request.getCmd());
        command.setReceiver(receiver);
        return command.execute(request);
    }
}
