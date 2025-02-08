package command_utilities;

import commands.Command;

import java.util.*;

public class CommandManager {
    private static HashMap<String, Command> commandCollection = new HashMap<>();
    private static Queue<Command> commandHistory = new ArrayDeque<>();
    public CommandManager() {}
    public static void registerCommand(String name, Command command) {
        commandCollection.put(name, command);
    }
    public static Command getCommand(String commandName) {
        return commandCollection.get(commandName);
    }
    public static HashMap<String, Command> getCommandCollection() {return commandCollection;}
    public static void pushHistory(Command command) {
        if(commandHistory.size() < 12) {
            commandHistory.add(command);
        } else {
            commandHistory.poll();
            commandHistory.add(command);
        }
    }
    public static Queue<Command> getHistory() {return commandHistory;}
    public static boolean isCommand(String commandName) {
        return commandCollection.containsKey(commandName);
    }
}
