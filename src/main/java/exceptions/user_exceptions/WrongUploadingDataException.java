package exceptions.user_exceptions;

/**
 * The {@code WrongUploadingDataException} class represents an exception that is thrown when there are issues or errors during the data uploading process.
 * It extends the {@code UserException} class and provides a specific error message to inform the user that there are problems with uploading data and they should check again.
 */
public class WrongUploadingDataException extends UserException {

    /**
     * Constructs a new {@code WrongUploadingDataException} with a default error message.
     */
    public WrongUploadingDataException() {
    }

    /**
     * Returns a string representation of the {@code WrongUploadingDataException}, providing a user-friendly error message.
     *
     * @return A string indicating that there are problems with uploading data and prompting the user to check again.
     */
    @Override
    public String toString() {
        return "There are problems with uploading data, please check again.";
    }
}
