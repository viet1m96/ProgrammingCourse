package command_utilities;

import commands.*;

import java.util.*;

/**
 * The {@code CommandManager} class manages the registration, retrieval, and history of commands.
 * It stores commands in a HashMap for quick access and maintains a queue of recently executed commands.
 */
public class CommandManager {

    private final HashMap<String, Command> commandCollection = new HashMap<>();

    private final Queue<Command> commandHistory = new ArrayDeque<>();

    /**
     * Constructs a new {@code CommandManager}.
     */
    public CommandManager() {}

    /**
     * Initializes the command collection by registering each command with its corresponding name.
     */
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

    /**
     * Registers a command with the specified name.
     *
     * @param name    The name of the command.
     * @param command The command to register.
     */
    public void registerCommand(String name, Command command) {
        commandCollection.put(name, command);
    }

    /**
     * Retrieves a command by its name.
     *
     * @param commandName The name of the command to retrieve.
     * @return The command associated with the given name, or null if no such command exists.
     */
    public Command getCommand(String commandName) {
        return commandCollection.get(commandName);
    }

    /**
     * Retrieves the entire command collection.
     *
     * @return The HashMap containing all registered commands.
     */
    public HashMap<String, Command> getCommandCollection() {
        return commandCollection;
    }

    /**
     * Adds a command to the command history.  The history is limited to 12 commands.
     * If the history is full, the oldest command is removed before adding the new one.
     *
     * @param command The command to add to the history.
     */
    public void pushHistory(Command command) {
        if (commandHistory.size() < 12) {
            commandHistory.add(command);
        } else {
            commandHistory.poll();
            commandHistory.add(command);
        }
    }

    /**
     * Retrieves the command history.
     *
     * @return The queue containing the recently executed commands.
     */
    public Queue<Command> getHistory() {
        return commandHistory;
    }

    /**
     * Checks if a command with the given name is registered.
     *
     * @param commandName The name of the command to check.
     * @return True if a command with the given name is registered, false otherwise.
     */
    public boolean isCommand(String commandName) {
        return commandCollection.containsKey(commandName);
    }
}
