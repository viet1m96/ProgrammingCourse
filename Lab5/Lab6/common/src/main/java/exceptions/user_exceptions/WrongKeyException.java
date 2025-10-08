package exceptions.user_exceptions;

/**
 * The {@code WrongKeyException} class represents an exception that is thrown when the user enters an incorrect or invalid key.
 * It extends the {@code UserException} class and provides a specific error message to inform the user that the key was wrong and they should try again.
 */
public class WrongKeyException extends UserException {

    /**
     * Constructs a new {@code WrongKeyException} with a default error message.
     */
    public WrongKeyException() {
    }

    /**
     * Returns a string representation of the {@code WrongKeyException}, providing a user-friendly error message.
     *
     * @return A string indicating that the key was incorrect and prompting the user to try again.
     */
    @Override
    public String toString() {
        return "Wrong key! Please try again.";
    }
}
