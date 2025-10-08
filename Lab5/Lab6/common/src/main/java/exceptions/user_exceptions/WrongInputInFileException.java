package exceptions.user_exceptions;

/**
 * The {@code WrongInputInFileException} class represents an exception that is thrown when invalid or incorrect input is found within a file.
 * It extends the {@code UserException} class and provides a specific error message to inform the user that the input in the file is wrong and they should check it again.
 */
public class WrongInputInFileException extends UserException {

    /**
     * Constructs a new {@code WrongInputInFileException} with a default error message.
     */
    public WrongInputInFileException() {
    }

    /**
     * Returns a string representation of the {@code WrongInputInFileException}, providing a user-friendly error message.
     *
     * @return A string indicating that the input in the file is incorrect and prompting the user to check it again.
     */
    @Override
    public String toString() {
        return "Wrong input in file, please check again";
    }
}
