package commands;

import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.UserException;
import iostream.Receiver;
import packets.Request;

/**
 * The {@code ExitCommand} class represents a command to exit the program.
 * It extends the {@code Command} class and implements the {@code execute} method
 * to perform the program exit operation.
 */
public class ExitCommand extends Command {

    /**
     * Constructs an {@code ExitCommand} object with the name "exit", no arguments, and a description.
     */
    public ExitCommand() {
        super("exit", "", "exit the program (without saving to a file)");
    }

    private Receiver receiver;

    /**
     * Sets the receiver for this command. The receiver is responsible for the actual exit operation.
     * @param receiver The receiver object.
     */
    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Executes the exit command by calling the {@code exit} method on the receiver.
     *
     * @param request The request object (not used in this command).
     * @throws UserException If there is an error during the execution of the command related to user input or state.
     * @throws LogException  If there is an error related to logging.
     */
    @Override
    public void execute(Request request) throws UserException, LogException {
        receiver.exit();
    }
}
