package handler;

import authorization_lib.JwtUtil;
import commands.Command;
import exceptions.database_exception.PostgresException;
import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.UserException;
import goods.Request;
import goods.Response;
import logging.LogUtil;


public class Invoker {
    private final CommandManager commandManager;
    private final Receiver receiver;

    public Invoker(CommandManager commandManager, Receiver receiver) {
        this.commandManager = commandManager;
        this.receiver = receiver;
    }

    public Response call(Request request) throws UserException, LogException, PostgresException {
        if (request.getToken() == null) {
            System.out.println(">" + "The " + request.getCmd() + " command is requested by " + request.getRemoteAddress());
            LogUtil.logInfo("The " + request.getCmd() + " command is requested by user: " + request.getRemoteAddress());
        } else {
            System.out.println(">" + "The " + request.getCmd() + " command is requested by user: " + JwtUtil.getUsername(request.getToken()) + " at " + request.getRemoteAddress());
            LogUtil.logInfo("The " + request.getCmd() + " command is requested by user: " + JwtUtil.getUsername(request.getToken()) + " at " + request.getRemoteAddress());
        }
        Command command = commandManager.getCommand(request.getCmd());
        command.setReceiver(receiver);
        return command.execute(request);
    }
}
