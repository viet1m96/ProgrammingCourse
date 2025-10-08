package exceptions.user_exceptions;

/**
 * The {@code InputFormatException} class represents an exception that is thrown when the user provides input that does not match the expected format.
 * It extends the {@code UserException} class and provides a specific error message to inform the user about the incorrect input format.
 */
public class InputFormatException extends UserException {

    /**
     * Constructs a new {@code InputFormatException} with a default error message.
     */
    public InputFormatException() {
    }

    /**
     * Returns a string representation of the {@code InputFormatException}, providing a user-friendly error message.
     *
     * @return A string indicating that the input format is incorrect and prompting the user to try again.
     */
    @Override
    public String toString() {
        return "input.format.exp";
    }
}
