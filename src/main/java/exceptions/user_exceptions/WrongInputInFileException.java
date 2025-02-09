package exceptions.user_exceptions;

/**
 * The {@code WrongInputInFileException} class represents an exception that is thrown when invalid input is found within a file.
 * It extends the {@link UserException} class, indicating that the error is related to user-provided data, specifically data read from a file.
 */
public class WrongInputInFileException extends UserException {

    /**
     * Constructs a new {@code WrongInputInFileException} with no specific detail message.
     */
    public WrongInputInFileException() {
    }

    /**
     * Returns a string representation of the {@code WrongInputInFileException}.
     *
     * @return A string that informs the user that invalid input was found in a file and prompts them to check the file for errors.
     */
    @Override
    public String toString() {
        return "Wrong input in file, please check again";
    }
}