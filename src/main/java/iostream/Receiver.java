package iostream;


import command_utilities.CommandManager;
import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.UserException;
import io_utilities.printers.RainbowPrinter;
import main_objects.CollectionManager;
import packets.Request;

public class Receiver {

    private CollectionManager collectionManager;
    private CommandManager commandManager;
    public Receiver(CollectionManager collectionManager, CommandManager commandManager) {
        this.collectionManager = collectionManager;
        this.commandManager = commandManager;
    }

    public void exit() {
        System.exit(0);
    }

    public void help() {
        commandManager.getCommandCollection().forEach((name, command) -> {command.getCommandInfo();} );
    }

    public void history() {
        commandManager.getHistory().forEach(command -> {RainbowPrinter.printResult(command.getName());});
    }

    public void info() {
        collectionManager.info();
    }

    public void clear() {
        collectionManager.clear();
    }

    public void insert(Request request) throws UserException {
        collectionManager.insert(request);
    }

    public void show() {
        collectionManager.show();
    }

    public void update(Request request) throws UserException {
        collectionManager.update(request);
    }

    public void remove_key(Request request) throws UserException {
        collectionManager.remove_key(request);
    }

    public void replace_if_lower(Request request) throws UserException {
        collectionManager.replace_if_lower(request);
    }

    public void remove_if_greater(Request request) throws UserException {
        collectionManager.remove_if_greater(request);
    }

    public void print_ascending() {
        collectionManager.print_ascending();
    }

    public void print_descending() {
        collectionManager.print_descending();
    }

    public void print_field_descending_semester_num() {
        collectionManager.print_field_descending_semester_enum();
    }

    public void execute_script() {
        RainbowPrinter.printCondition("The file is being executed...");
    }

    public void save() throws LogException {
        collectionManager.save();
    }

}