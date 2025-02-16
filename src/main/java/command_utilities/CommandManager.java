package command_utilities;

import commands.*;

import java.util.*;

public class CommandManager {

    private final HashMap<String, Command> commandCollection = new HashMap<>();

    private final Queue<Command> commandHistory = new ArrayDeque<>();

    public CommandManager() {}
    
    public void init() {
        registerCommand("help", new HelpCommand());
        registerCommand("clear", new ClearCommand());
        registerCommand("exit", new ExitCommand());
        registerCommand("history", new HistoryCommand());
        registerCommand("info", new InfoCommand());
        registerCommand("print_descending", new Print_descendingCommand());
        registerCommand("print_ascending", new Print_ascendingCommand());
        registerCommand("print_field_descending_semester_enum", new Print_semesterEnumCommand());
        registerCommand("remove_key", new Remove_keyCommand());
        registerCommand("save", new SaveCommand());
        registerCommand("show", new ShowCommand());
        registerCommand("update", new UpdateCommand());
        registerCommand("execute_script", new Execute_scriptCommand());
        registerCommand("insert", new InsertCommand());
        registerCommand("remove_greater", new Remove_greaterCommand());
        registerCommand("replace_if_lower", new Replace_if_lowerCommand());
    }
    public void registerCommand(String name, Command command) {
        commandCollection.put(name, command);
    }

    public Command getCommand(String commandName) {
        return commandCollection.get(commandName);
    }

    public HashMap<String, Command> getCommandCollection() {
        return commandCollection;
    }

    public void pushHistory(Command command) {
        if (commandHistory.size() < 12) {
            commandHistory.add(command);
        } else {
            commandHistory.poll();
            commandHistory.add(command);
        }
    }

    public Queue<Command> getHistory() {
        return commandHistory;
    }

    public boolean isCommand(String commandName) {
        return commandCollection.containsKey(commandName);
    }
}