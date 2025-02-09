package exceptions.user_exceptions;

/**
 * The {@code KeyTakenException} class represents an exception that occurs when attempting to use a key that is already in use.
 * This exception is typically thrown when attempting to add an element to a collection using a key that already exists.
 * It extends the {@link UserException} class, indicating that this is a user-related error.
 */
public class KeyTakenException extends UserException {

    /**
     * Constructs a new {@code KeyTakenException} with no specific detail message.
     */
    public KeyTakenException() {
    }

    /**
     * Returns a string representation of the {@code KeyTakenException}.
     *
     * @return A string that informs the user that the key is already taken and that they should choose another one.
     */
    @Override
    public String toString() {
        return "This key was taken. Please choose another one";
    }
}