package exceptions.user_exceptions;

/**
 * The {@code WrongInputException} class represents an exception that is thrown when the user provides invalid or incorrect input.
 * It extends the {@code UserException} class and provides a specific error message to inform the user that the input was wrong and they should try again.
 */
public class WrongInputException extends UserException {

    /**
     * Constructs a new {@code WrongInputException} with a default error message.
     */
    public WrongInputException() {
    }

    /**
     * Returns a string representation of the {@code WrongInputException}, providing a user-friendly error message.
     *
     * @return A string indicating that the input was incorrect and prompting the user to try again.
     */
    @Override
    public String toString() {
        return "wrong.input.exp";
    }
}
