package commands;

import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.UserException;
import exceptions.user_exceptions.WrongInputException;
import iostream.Receiver;
import packets.Request;

/**
 * The {@code ExitCommand} class represents a command that exits the program without saving the current state to a file.
 * It extends the {@link Command} class and utilizes a {@link Receiver} object to perform the actual exit operation.
 */
public class ExitCommand extends Command {

    /**
     * Constructs a new {@code ExitCommand} object.
     * The constructor sets the name, argument description (which is empty, as no arguments are required), and description of the command.
     */
    public ExitCommand() {
        super("exit", "", "exit the program (without saving to a file)");
    }

    /**
     * The {@link Receiver} object used to perform the exit operation.
     */
    private Receiver receiver;

    /**
     * Sets the {@link Receiver} object to be used by this command.
     *
     * @param receiver The {@link Receiver} object to set.  This receiver will handle the actual program exit.
     */
    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Executes the exit command.
     * This method calls the {@link Receiver#exit()} method to exit the program.
     * It also checks for invalid input (an argument provided when none is expected).
     *
     * @param request The {@link Request} object containing the command and any arguments.
     * @throws UserException If the input is invalid (e.g., an argument is provided when not expected).
     * @throws LogException  If an error occurs during the logging process (if logging is implemented in {@code Receiver}).
     */
    @Override
    public void execute(Request request) throws UserException, LogException {
        if (request.getArgument() != null) {
            throw new WrongInputException();
        }
        receiver.exit();
    }
}