package handler;


import database.CollectionManager;
import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.UserException;
import logging.LogUtil;
import goods.*;
import printer_options.RainbowPrinter;

import java.net.SocketAddress;

public class Handler {
    public Handler(){
        RainbowPrinter.printInfo("Hello! Welcome to the Application!");
        RainbowPrinter.printInfo("Please type 'save' to save the data or type 'exit' to exit.");
    }

    private Invoker invoker;
    private Receiver receiver;
    private CommandManager commandManager;
    private CollectionManager collectionManager;

    public void init(String fileName) {
        try {
            commandManager = new CommandManager();
            commandManager.init();
            collectionManager = new CollectionManager(fileName);
            receiver = new Receiver(collectionManager, commandManager);
            invoker = new Invoker(commandManager, receiver);
            collectionManager.uploadData();
            RainbowPrinter.printInfo("The process of uploading data finished!");
            LogUtil.logInfo("The process of uploading data finished!");
        } catch (LogException e) {
            RainbowPrinter.printError(e.toString());
        }
    }

    public Response processCommandFromUser(SocketAddress port, Request request) throws UserException, LogException {
        return invoker.call(port, request);
    }

    public void processCommandFromServer(Request request) {
        try {
            String cmd = request.getCmd();
            switch (cmd) {
                case "save", "exit":
                    invoker.call(null, request);
                    RainbowPrinter.printInfo("The command was successfully executed!");
                    LogUtil.logInfo("The command was successfully executed!");
                    break;
                default:
                    RainbowPrinter.printError("This is not a valid command for server!");
                    LogUtil.logInfo("An invalid command was not executed!");
            }
        } catch (UserException | LogException e) {
            RainbowPrinter.printError(e.toString());
        }
    }

}
