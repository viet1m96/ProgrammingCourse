package exceptions.log_exceptions;

/**
 * The {@code LogException} class represents a custom exception that is thrown when an error occurs during system processing.
 * It provides a general error message directing the user to consult the log file for more detailed information.
 */
public class LogException extends Exception {

    /**
     * Constructs a new {@code LogException} with a default error message.
     */
    public LogException() {
    }

    /**
     * Returns a string representation of the {@code LogException}, providing a user-friendly error message.
     *
     * @return A string describing the exception and instructing the user to check the log file.
     */
    @Override
    public String toString() {
        return "There is an error with system during processing, please look at the log file for more details.";
    }
}
