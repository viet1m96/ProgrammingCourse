package exceptions.user_exceptions;

/**
 * The {@code KeyTakenException} class represents an exception that is thrown when a user attempts to use a key that is already in use.
 * It extends the {@code UserException} class and provides a specific error message to inform the user that the key is taken and they should choose another one.
 */
public class KeyTakenException extends UserException {

    /**
     * Constructs a new {@code KeyTakenException} with a default error message.
     */
    public KeyTakenException() {
    }

    /**
     * Returns a string representation of the {@code KeyTakenException}, providing a user-friendly error message.
     *
     * @return A string indicating that the key is already taken and prompting the user to choose another one.
     */
    @Override
    public String toString() {
        return "key.taken";
    }
}
