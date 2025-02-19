package exceptions.user_exceptions;

public class InputFormatException extends UserException {
    public InputFormatException() {}
    @Override
    public String toString() {
        return "Input Format is wrong! Please try again.";
    }
}
