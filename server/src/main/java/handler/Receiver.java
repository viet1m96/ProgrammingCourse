package handler;

import authorization_lib.JwtUtil;
import database.ClientAccountWorker;
import database.CollectionManager;
import exceptions.database_exception.*;
import exceptions.log_exceptions.EnvNotExistsException;
import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.UserException;
import goods.Request;
import goods.Response;

import java.util.ArrayList;
import java.util.List;

public class Receiver {
    private final CollectionManager collectionManager;
    private final CommandManager commandManager;
    private final ClientAccountWorker clientAccountWorker;

    public Receiver(CollectionManager collectionManager, CommandManager commandManager) throws EnvNotExistsException {
        clientAccountWorker = new ClientAccountWorker();
        clientAccountWorker.init();
        this.collectionManager = collectionManager;
        this.commandManager = commandManager;
    }

    public Response help(Request request) {
        List<String> result = new ArrayList<>();
        commandManager.getCommandCollection().forEach((name, command) -> {
            result.add(command.getCommandInfo());
        });
        return new Response(new ArrayList<>(), result, request.getRemoteAddress());
    }

    public Response sign_in(Request request) throws LogException {
        List<String> arguments = request.getArguments();
        boolean exist = clientAccountWorker.checkAccount(arguments.get(0), arguments.get(1));
        if(exist) {
            String token = JwtUtil.generateToken(arguments.get(0));
            List<String> result = new ArrayList<>();
            result.add(token);
            return new Response(new ArrayList<>(), result, request.getRemoteAddress());
        } else {
            List<String> notice = new ArrayList<>();
            notice.add("Your username or password is incorrect.");
            return new Response(notice, null, request.getRemoteAddress());
        }
    }

    public Response sign_up(Request request) throws LogException {
        List<String> arguments = request.getArguments();
        boolean userExist = clientAccountWorker.checkUsername(arguments.get(0));
        if(userExist) {
            List<String> notice = new ArrayList<>();
            notice.add("Your username already exists.");
            return new Response(notice, null, request.getRemoteAddress());
        } else {
            boolean insertSuccess = clientAccountWorker.insertNewAccount(arguments.get(0), arguments.get(1));
            if(insertSuccess) {
                List<String> result = new ArrayList<>();
                String token = JwtUtil.generateToken(arguments.get(0));
                result.add(token);
                return new Response(new ArrayList<>(), result, request.getRemoteAddress());
            } else {
                List<String> notice = new ArrayList<>();
                notice.add("Your username already exists.");
                return new Response(notice, null, request.getRemoteAddress());
            }
        }
    }

    public Response info(Request request) {
        return collectionManager.info(request);
    }

    public Response clear(Request request) throws LogException, UnsuccesfulDeletionException {
        return collectionManager.clear(request);
    }

    public Response insert(Request request) throws UserException, LogException, UnsuccesfulInsertException {
        return collectionManager.insert(request);
    }

    public Response show(Request request) {
        return collectionManager.show(request);
    }

    public Response update(Request request) throws UserException, UnsuccesfulUpdateException, NotCreatorException, LogException {
        return collectionManager.update(request);
    }

    public Response remove_key(Request request) throws UserException, LogException, UnsuccesfulDeletionException, NotCreatorException {
        return collectionManager.remove_key(request);
    }

    public Response replace_if_lower(Request request) throws UserException, FailedConditionUpdateException, UnsuccesfulUpdateException, LogException, NotCreatorException {
        return collectionManager.replace_if_lower(request);
    }

    public Response remove_if_greater(Request request) throws LogException  {
        return collectionManager.remove_if_greater(request);
    }

    public Response print_ascending(Request request) throws UserException {
        return collectionManager.print_ascending(request);
    }

    public Response print_descending(Request request) throws UserException {
        return collectionManager.print_descending(request);
    }

    public Response print_field_descending_semester_enum(Request request) {
        return collectionManager.print_field_descending_semester_enum(request);
    }

}
