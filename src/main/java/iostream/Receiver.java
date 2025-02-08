package iostream;


import command_utilities.CommandManager;
import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.UserException;
import io_utilities.printers.RainbowPrinter;
import main_objects.CollectionManager;
import packets.Request;



public class Receiver {
    public Receiver() {}

    public void exit() {
        System.exit(0);
    }

    public void help() {
        CommandManager.getCommandCollection().forEach((name, command) -> {command.getCommandInfo();} );
    }

    public void history() {
        CommandManager.getHistory().forEach(command -> {RainbowPrinter.printResult(command.getName());});
    }
    public void info() {
        CollectionManager.info();
    }

    public void clear() {
        CollectionManager.clear();
    }

    public void insert(Request request) throws UserException {
        CollectionManager.insert(request);
    }

    public void show() {
        CollectionManager.show();
    }

    public void update(Request request) throws UserException {
        CollectionManager.update(request);
    }

    public void remove_key(Request request) throws UserException {
        CollectionManager.remove_key(request);
    }

    public void replace_if_lower(Request request) throws UserException {
        CollectionManager.replace_if_lower(request);
    }

    public void remove_if_greater(Request request) throws UserException {
        CollectionManager.remove_if_greater(request);
    }

    public void print_ascending() {
        CollectionManager.print_ascending();
    }

    public void print_descending() {
        CollectionManager.print_descending();
    }

    public void print_field_descending_semester_num() {
        CollectionManager.print_field_descending_semester_enum();
    }

    public void execute_script() {
        RainbowPrinter.printCondition("The file is being executed...");
    }
    public void save() throws LogException {
        CollectionManager.save();
    }

}
