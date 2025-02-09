package exceptions.user_exceptions;

/**
 * The {@code WrongCommandException} class represents an exception that is thrown when the user enters an invalid or unrecognized command.
 * It extends the {@link UserException} class, indicating that it's a user-related error resulting from incorrect input.
 */
public class WrongCommandException extends UserException {

    /**
     * Constructs a new {@code WrongCommandException} with no specific detail message.
     */
    public WrongCommandException() {
    }

    /**
     * Returns a string representation of the {@code WrongCommandException}.
     *
     * @return A string that informs the user that the entered command was invalid and prompts them to try again.
     */
    @Override
    public String toString() {
        return "Wrong command, please try again";
    }
}