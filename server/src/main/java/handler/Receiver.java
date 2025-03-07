package handler;

import database.CollectionManager;
import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.UserException;
import logging.LogUtil;
import goods.Response;
import java.util.ArrayList;
import java.util.List;

import goods.*;

public class Receiver {

    private final CollectionManager collectionManager;
    private final CommandManager commandManager;

    public Receiver(CollectionManager collectionManager, CommandManager commandManager) {
        this.collectionManager = collectionManager;
        this.commandManager = commandManager;
    }

    public void exit() throws UserException, LogException {
        LogUtil.logInfo("The exit command was requested, backup process is being executed.");
        save();
        LogUtil.logInfo("The exit command was executed.");
        LogUtil.logInfo("The server was stopped!");
        System.exit(0);
    }

    public Response help() {
        List<String> result = new ArrayList<>();
        commandManager.getCommandCollection().forEach((name, command) -> {
            if (!name.equals("save")) {
                result.add(command.getCommandInfo());
            }
        });
        return new Response(new ArrayList<>(), result);
    }
    
    public Response info() {
        return collectionManager.info();
    }

    public Response clear() {
        return collectionManager.clear();
    }

    public Response insert(Request request) throws UserException {
        return collectionManager.insert(request);
    }

    public Response show() {
        return collectionManager.show();
    }

    public Response update(Request request) throws UserException {
        return collectionManager.update(request);
    }

    public Response remove_key(Request request) throws UserException {
        return collectionManager.remove_key(request);
    }

    public Response replace_if_lower(Request request) throws UserException {
        return collectionManager.replace_if_lower(request);
    }

    public Response remove_if_greater(Request request)  {
        return collectionManager.remove_if_greater(request);
    }

    public Response print_ascending() {
        return collectionManager.print_ascending();
    }

    public Response print_descending() {
        return collectionManager.print_descending();
    }

    public Response print_field_descending_semester_enum() {
        return collectionManager.print_field_descending_semester_enum();
    }

    public Response save() throws LogException {
        return collectionManager.save();
    }
}
