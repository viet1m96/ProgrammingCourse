package network;


import authorization_lib.JwtUtil;
import database.CollectionManager;
import exceptions.database_exception.PostgresException;
import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.TokenException;
import exceptions.user_exceptions.UserException;
import goods.Request;
import goods.Response;
import handler.CommandManager;
import handler.Invoker;
import handler.Receiver;
import logging.LogUtil;
import printer_options.RainbowPrinter;

import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

public class Processor implements Callable<Response> {

    private final BlockingQueue<Request> requestQueue;
    private final BlockingQueue<Response> responseQueue;

    public Processor(BlockingQueue<Request> requestQueue, BlockingQueue<Response> responseQueue){
        this.requestQueue = requestQueue;
        this.responseQueue = responseQueue;
        RainbowPrinter.printInfo("Hello! Welcome to the Application!");
    }

    private Invoker invoker;
    private Receiver receiver;
    private CommandManager commandManager;
    private CollectionManager collectionManager;

    public void init() {
        try {
            commandManager = new CommandManager();
            commandManager.init();
            collectionManager = new CollectionManager();
            receiver = new Receiver(collectionManager, commandManager);
            invoker = new Invoker(commandManager, receiver);
            collectionManager.uploadData();
            RainbowPrinter.printInfo("The process of uploading data finished!");
            LogUtil.logInfo("The process of uploading data finished!");
        } catch (LogException e) {
            RainbowPrinter.printError(e.toString());
        }
    }

    public void checkAuthorization(Request request) throws TokenException {
        try {
            JwtUtil.checkToken(request.getToken());
        } catch (TokenException e) {
            if(!request.getCmd().equals("sign_up") && !request.getCmd().equals("sign_in")) {
                throw new TokenException();
            }
        }
    }

    public Response processCommandFromUser(Request request) {
        Response result = null;
        try {
            checkAuthorization(request);
            result = invoker.call(request);
            result.addNotice("The " + request.getCmd() + " command has been successfully executed.");
        } catch(UserException | PostgresException e) {
            List<String> notice = new ArrayList<>();
            notice.add(e.toString());
            notice.add("The " + request.getCmd() + " command has been unsuccessfully executed.");
            result = new Response(notice, null, request.getRemoteAddress());
        } catch (LogException e) {
            RainbowPrinter.printError(e.toString());
            List<String> notice = new ArrayList<>();
            notice.add("There was a system error while executing the " + request.getCmd() + " command.");
            notice.add("The " + request.getCmd() + " command has been unsuccessfully executed.");
            result = new Response(notice, null, request.getRemoteAddress());
        }
        return result;
    }



    @Override
    public Response call() {
        Response result = null;
        try {
            Request request = requestQueue.take();
            result = processCommandFromUser(request);
            responseQueue.put(result);
        } catch (InterruptedException e) {
            LogUtil.logTrace(e);
            RainbowPrinter.printError("There was an error while processing the request, please look at the log file for more details.");
        }
        return result;
    }

}
