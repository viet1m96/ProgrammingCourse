package network;


import authorization_lib.JwtUtil;
import database.CollectionManager;
import database.ConnectionCreator;
import database.DatabaseManager;
import exceptions.database_exception.PostgresException;
import exceptions.log_exceptions.EnvNotExistsException;
import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.TokenException;
import exceptions.user_exceptions.UserException;
import goods.Request;
import goods.Response;
import handler.CommandManager;
import handler.Invoker;
import handler.Receiver;
import handler.Translator;
import logging.LogUtil;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

public class Processor implements Callable<Response> {

    private final BlockingQueue<Request> requestQueue;
    private final BlockingQueue<Response> responseQueue;
    private final String baseCommands = "commands";
    private final String baseNotices = "notices";
    public Processor(BlockingQueue<Request> requestQueue, BlockingQueue<Response> responseQueue) {
        this.requestQueue = requestQueue;
        this.responseQueue = responseQueue;
        System.out.println("Hello! Welcome to the Application!");
    }

    private Invoker invoker;
    private Receiver receiver;
    private CommandManager commandManager;
    private DatabaseManager databaseManager;
    private CollectionManager collectionManager;
    private ConnectionCreator connectionCreator;

    public void init() throws EnvNotExistsException {
        try {
            commandManager = new CommandManager();
            commandManager.init();
            connectionCreator = new ConnectionCreator();
            connectionCreator.init();
            databaseManager = new DatabaseManager(connectionCreator);
            collectionManager = new CollectionManager(databaseManager);
            receiver = new Receiver(collectionManager, commandManager, connectionCreator);
            invoker = new Invoker(commandManager, receiver);
            collectionManager.uploadData();
            System.out.println("The process of uploading data finished!");
            LogUtil.logInfo("The process of uploading data finished!");
        } catch (LogException e) {
            System.out.println(e.getMessage());
        }
    }

    public void checkAuthorization(Request request) throws TokenException {
        try {
            if(request.getToken() == null || request.getToken().isEmpty()) throw new TokenException();
            JwtUtil.checkToken(request.getToken());
        } catch (TokenException e) {
            if (!request.getCmd().equals("sign_up") && !request.getCmd().equals("sign_in")) {
                throw new TokenException();
            }
        }
    }

    public Response processCommandFromUser(Request request) {
        Response result = null;
        Locale locale = request.getLocale();
        String cmd = Translator.getString(baseCommands, request.getCmd(), request.getLocale());
        try {
            checkAuthorization(request);
            result = invoker.call(request);
            result.addNotice(cmd + " " + Translator.getString(baseNotices, "successfully", locale));
        } catch (UserException | PostgresException e) {
            List<String> notice = new ArrayList<>();
            notice.add(Translator.getString(baseNotices, e.toString(), locale));// hien tai co moi keytaken voi nametaken
            notice.add(cmd + " " + Translator.getString(baseNotices, "unsuccessfully", locale));
            result = new Response(notice, null, request.getRemoteAddress(), false);
        } catch (LogException e) {
            System.out.println(e.getMessage());
            System.out.println(e.toString());
            List<String> notice = new ArrayList<>();
            notice.add(Translator.getString(baseNotices, "system.error", locale) + " " + cmd);
            notice.add(cmd + " " + Translator.getString(baseNotices, "unsuccessfully", locale));
            result = new Response(notice, null, request.getRemoteAddress(), false);
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
            System.out.println("There was an error while processing the request, please look at the log file for more details.");
        }
        return result;
    }

}
