package exceptions.log_exceptions;

public class LogException extends Exception {

    public LogException() {
    }

    @Override
    public String toString() {
        return "There is an error with system during processing, please look at the log file for more details.";
    }
}