package iostream;

import command_utilities.CommandManager;
import commands.*;
import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.UserException;
import io_utilities.printers.RainbowPrinter;
import packets.Request;

/**
 * The {@code Invoker} class is responsible for executing commands. It receives a command name and a request,
 * retrieves the corresponding command from the {@link CommandManager}, sets the {@link Receiver} for the command,
 * pushes the command to the history, and executes the command.
 */
public class Invoker {
    private CommandManager commandManager;
    private Receiver receiver;

    /**
     * Constructs a new {@code Invoker} with the specified {@link CommandManager} and {@link Receiver}.
     *
     * @param commandManager The {@link CommandManager} to retrieve commands from.
     * @param receiver       The {@link Receiver} to set for the commands.
     */
    public Invoker(CommandManager commandManager, Receiver receiver) {
        this.commandManager = commandManager;
        this.receiver = receiver;
    }

    /**
     * Executes the command with the given name and request.
     *
     * @param nameCommand The name of the command to execute.
     * @param request     The request to pass to the command.
     * @throws UserException If there is an issue with the user-provided data or command execution.
     * @throws LogException  If there is an issue during logging or command execution.
     */
    public void call(String nameCommand, Request request) throws UserException, LogException {
        RainbowPrinter.printCondition(">" + "The " + nameCommand + " command is being executed!");
        Command command = commandManager.getCommand(nameCommand);
        command.setReceiver(receiver);
        commandManager.pushHistory(commandManager.getCommand(nameCommand));
        command.execute(request);
    }
}
