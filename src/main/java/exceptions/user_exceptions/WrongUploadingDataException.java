package exceptions.user_exceptions;

/**
 * The {@code WrongUploadingDataException} class represents an exception that is thrown when there are problems uploading data.
 * This usually indicates an issue with the user-provided data or the process of transferring it.
 * It extends the {@link UserException} class, indicating that the error is related to user actions.
 */
public class WrongUploadingDataException extends UserException {

    /**
     * Constructs a new {@code WrongUploadingDataException} with no specific detail message.
     */
    public WrongUploadingDataException() {
    }

    /**
     * Returns a string representation of the {@code WrongUploadingDataException}.
     *
     * @return A string that informs the user about the issue with uploading data and prompts them to check their data and the upload process.
     */
    @Override
    public String toString() {
        return "There are problems with uploading data, please check again.";
    }
}