package handler;

import authorization_lib.JwtUtil;
import commands.Command;
import database.ClientAccountWorker;
import database.CollectionManager;
import database.ConnectionCreator;
import exceptions.user_exceptions.NotCreatorException;
import exceptions.database_exception.UnsuccesfulDeletionException;
import exceptions.database_exception.UnsuccesfulInsertException;
import exceptions.database_exception.UnsuccesfulUpdateException;
import exceptions.log_exceptions.EnvNotExistsException;
import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.NameTakenException;
import exceptions.user_exceptions.UserException;
import goods.Request;
import goods.Response;
import main_objects.Instruction;

import java.util.*;

public class Receiver {
    private final CollectionManager collectionManager;
    private final CommandManager commandManager;
    private final ClientAccountWorker clientAccountWorker;
    private final String baseCommands = "commands";
    private final String baseNotices = "notices";
    public Receiver(CollectionManager collectionManager, CommandManager commandManager, ConnectionCreator connectionCreator) throws EnvNotExistsException {
        clientAccountWorker = new ClientAccountWorker(connectionCreator);
        this.collectionManager = collectionManager;
        this.commandManager = commandManager;
    }

    public Response help(Request request) {
        Response response = new Response(new ArrayList<>(), null, request.getRemoteAddress(), true);
        HashMap<String, Command> commands = this.commandManager.getCommandCollection();
        List<Instruction> instructions = new ArrayList<>();
        for(Command command : commands.values()) {
            if(!command.getName().equals("show")) {
                System.out.println(Translator.getString(baseCommands, command.getName(), request.getLocale()));
                System.out.println(Translator.getString(baseCommands, command.getDescription(), request.getLocale()));
                Instruction instruction = new Instruction();
                instruction.setCommand(Translator.getString(baseCommands, command.getName(), request.getLocale()));
                instruction.setDescription(Translator.getString(baseCommands, command.getDescription(), request.getLocale()));
                String syntax = "";
                if(!command.getArgument().isEmpty()) {
                    syntax += command.getName() + " " + Translator.getString(baseCommands, command.getArgument(), request.getLocale());
                } else {
                    syntax += command.getName();
                }
                instruction.setSyntax(syntax);
                instructions.add(instruction);
            }

        }
        response.setInstructions(instructions);
        return response;
    }

    public Response sign_in(Request request) throws LogException {
        List<String> arguments = request.getArguments();
        boolean exist = clientAccountWorker.checkAccount(arguments.get(0), arguments.get(1));
        if (exist) {
            String token = JwtUtil.generateToken(arguments.get(0));
            List<String> result = new ArrayList<>();
            result.add(token);
            Response response = new Response(new ArrayList<>(), result, request.getRemoteAddress());
            response.setSuccess(Boolean.TRUE);
            return response;
        } else {
            List<String> notice = new ArrayList<>();
            notice.add(Translator.getString(baseNotices, "wrong.sign_in", request.getLocale()));
            Response response = new Response(notice, null, request.getRemoteAddress());
            response.setSuccess(Boolean.FALSE);
            return response;
        }
    }

    public Response sign_up(Request request) throws LogException, NameTakenException {
        List<String> arguments = request.getArguments();
        boolean insertSuccess = clientAccountWorker.insertNewAccount(arguments.get(0), arguments.get(1));
        if (insertSuccess) {
            List<String> result = new ArrayList<>();
            String token = JwtUtil.generateToken(arguments.get(0));
            result.add(token);

            Response response = new Response(new ArrayList<>(), result, request.getRemoteAddress());
            response.setSuccess(Boolean.TRUE);
            return response;
        } else {
            List<String> notice = new ArrayList<>();
            notice.add("name.taken");
            Response response = new Response(notice, null, request.getRemoteAddress());
            response.setSuccess(Boolean.FALSE);
            return response;
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

    public Response replace_if_lower(Request request) throws UserException, LogException, NotCreatorException, UnsuccesfulInsertException, UnsuccesfulDeletionException {
        return collectionManager.replace_if_lower(request);
    }

    public Response remove_if_greater(Request request) throws LogException {
        return collectionManager.remove_greater(request);
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
