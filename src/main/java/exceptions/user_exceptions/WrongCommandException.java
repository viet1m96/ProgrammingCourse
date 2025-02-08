package exceptions.user_exceptions;

public class WrongCommandException extends UserException {
    public WrongCommandException() {}
    @Override
    public String toString() {
        return "Wrong command, please try again";
    }
}
