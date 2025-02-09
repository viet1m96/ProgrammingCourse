package exceptions.user_exceptions;

/**
 * The {@code WrongKeyException} class represents an exception that is thrown when the user provides an incorrect or invalid key.
 * It extends the {@link UserException} class, indicating that the error is related to user-provided input.
 */
public class WrongKeyException extends UserException {

    /**
     * Constructs a new {@code WrongKeyException} with no specific detail message.
     */
    public WrongKeyException() {
    }

    /**
     * Returns a string representation of the {@code WrongKeyException}.
     *
     * @return A string that informs the user that the provided key was incorrect and prompts them to try again.
     */
    @Override
    public String toString() {
        return "Wrong key! Please try again.";
    }
}