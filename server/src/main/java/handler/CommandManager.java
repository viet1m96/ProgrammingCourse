package handler;


import commands.*;

import java.util.HashMap;


public class CommandManager {

    private final HashMap<String, Command> commandCollection = new HashMap<>();


    public CommandManager() {
    }

    public void init() {
        registerCommand("help", new HelpCommand());
        registerCommand("clear", new ClearCommand());
        registerCommand("info", new InfoCommand());
        registerCommand("remove_key", new Remove_keyCommand());
        registerCommand("show", new ShowCommand());
        registerCommand("update", new UpdateCommand());
        registerCommand("execute_script", new Execute_scriptCommand());
        registerCommand("insert", new InsertCommand());
        registerCommand("remove_greater", new Remove_greaterCommand());
        registerCommand("replace_if_lower", new Replace_if_lowerCommand());
        registerCommand("sign_in", new SignInCommand());
        registerCommand("sign_up", new SignUpCommand());
        registerCommand("sign_out", new SignOutCommand());
        registerCommand("history", new HistoryCommand());

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

    public boolean isCommand(String commandName) {
        return commandCollection.containsKey(commandName);
    }
}
