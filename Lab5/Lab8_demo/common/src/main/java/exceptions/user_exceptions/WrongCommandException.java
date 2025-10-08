package exceptions.user_exceptions;

/**
 * The {@code WrongCommandException} class represents an exception that is thrown when the user enters an invalid or unrecognized command.
 * It extends the {@code UserException} class and provides a specific error message to inform the user that the command was incorrect and they should try again.
 */
public class WrongCommandException extends UserException {

    /**
     * Constructs a new {@code WrongCommandException} with a default error message.
     */
    public WrongCommandException() {
    }

    /**
     * Returns a string representation of the {@code WrongCommandException}, providing a user-friendly error message.
     *
     * @return A string indicating that the command was incorrect and prompting the user to try again.
     */
    @Override
    public String toString() {
        return "wrong.command.exp";
    }
}
