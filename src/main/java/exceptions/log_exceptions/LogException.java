package exceptions.log_exceptions;

/**
 * The {@code LogException} class represents a generic exception that occurs during processing.
 * It indicates that an error has occurred and suggests checking the log file for more detailed information.
 */
public class LogException extends Exception {

    /**
     * Constructs a new {@code LogException} with no specific detail message.
     */
    public LogException() {
    }

    /**
     * Returns a string representation of the {@code LogException}.
     *
     * @return A string that informs the user to check the log file for details about the exception.
     */
    @Override
    public String toString() {
        return "There is an exception during processing, please look at the log file for more details.";
    }
}