package exceptions.user_exceptions;

public class NotCreatorException extends UserException {
    public NotCreatorException() {
    }

    @Override
    public String toString() {
        return "not.creator";
    }
}
