package exceptions.user_exceptions;

public class WrongKeyException extends UserException {

    public WrongKeyException() {
    }

    @Override
    public String toString() {
        return "Wrong key! Please try again.";
    }
}