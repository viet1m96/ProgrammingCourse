package iostream;

import command_utilities.CommandManager;
import commands.*;
import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.UserException;
import io_utilities.printers.RainbowPrinter;
import io_utilities.working_with_input.InputPartition;
import packets.Request;


public class Invoker {
    private static Receiver receiver;
    public Invoker() {}
    public static void init() {
        CommandManager.registerCommand("help", new HelpCommand());
        CommandManager.registerCommand("clear", new ClearCommand());
        CommandManager.registerCommand("exit", new ExitCommand());
        CommandManager.registerCommand("history", new HistoryCommand());
        CommandManager.registerCommand("info", new InfoCommand());
        CommandManager.registerCommand("print_descending", new Print_descendingCommand());
        CommandManager.registerCommand("print_ascending", new Print_ascendingCommand());
        CommandManager.registerCommand("print_field_descending_semester_enum", new Print_semesterEnumCommand());
        CommandManager.registerCommand("remove_key", new Remove_keyCommand());
        CommandManager.registerCommand("save", new SaveCommand());
        CommandManager.registerCommand("show", new ShowCommand());
        CommandManager.registerCommand("update", new UpdateCommand());
        CommandManager.registerCommand("execute_script", new Execute_scriptCommand());
        CommandManager.registerCommand("insert", new InsertCommand());
        CommandManager.registerCommand("remove_greater", new Remove_greaterCommand());
        CommandManager.registerCommand("replace_if_lower", new Replace_if_lowerCommand());
        receiver = new Receiver();
    }

    public static void call(String nameCommand, Request request) throws UserException, LogException {
        RainbowPrinter.printCondition(">" + "The " + nameCommand + " command is being executed!");
        Command command = CommandManager.getCommand(nameCommand);
        command.setReceiver(receiver);
        CommandManager.pushHistory(CommandManager.getCommand(nameCommand));
        command.execute(request);
    }
}
