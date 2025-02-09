package command_utilities;

import commands.Command;

import java.util.*;

/**
 * The {@code CommandManager} class is responsible for managing and executing commands.
 * It stores a collection of available commands and maintains a history of executed commands.
 */
public class CommandManager {

    /**
     * A HashMap that stores the available commands.
     * The key is the command name (String), and the value is the corresponding {@link Command} object.
     */
    private static HashMap<String, Command> commandCollection = new HashMap<>();

    /**
     * A Queue that stores the history of executed commands.
     * The queue maintains a limited size (12 commands in this case) to prevent excessive memory usage.
     */
    private static Queue<Command> commandHistory = new ArrayDeque<>();

    /**
     * Constructs a new {@code CommandManager} instance.
     * This constructor is currently empty as the class uses static methods for command management.
     */
    public CommandManager() {
    }

    /**
     * Registers a new command with the command manager.
     *
     * @param name    The name of the command.
     * @param command The {@link Command} object to associate with the name.
     */
    public static void registerCommand(String name, Command command) {
        commandCollection.put(name, command);
    }

    /**
     * Retrieves a command from the command collection.
     *
     * @param commandName The name of the command to retrieve.
     * @return The {@link Command} object associated with the specified name,
     *         or {@code null} if the command is not found.
     */
    public static Command getCommand(String commandName) {
        return commandCollection.get(commandName);
    }

    /**
     * Returns the entire command collection.
     *
     * @return A HashMap containing all registered commands, where the key is the command name
     *         and the value is the corresponding {@link Command} object.
     */
    public static HashMap<String, Command> getCommandCollection() {
        return commandCollection;
    }

    /**
     * Adds a command to the command history.
     * If the history is full (contains 12 commands), the oldest command is removed
     * before adding the new command.
     *
     * @param command The {@link Command} object to add to the history.
     */
    public static void pushHistory(Command command) {
        if (commandHistory.size() < 12) {
            commandHistory.add(command);
        } else {
            commandHistory.poll();
            commandHistory.add(command);
        }
    }

    /**
     * Returns the command history.
     *
     * @return A Queue containing the history of executed commands, in the order they were executed.
     */
    public static Queue<Command> getHistory() {
        return commandHistory;
    }

    /**
     * Checks if a command with the given name is registered.
     *
     * @param commandName The name of the command to check.
     * @return {@code true} if a command with the specified name is registered, {@code false} otherwise.
     */
    public static boolean isCommand(String commandName) {
        return commandCollection.containsKey(commandName);
    }
}