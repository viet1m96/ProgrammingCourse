package exceptions.user_exceptions;

public class NameTakenException extends UserException {
    public NameTakenException() {
    }

    @Override
    public String toString() {
        return "name.taken";
    }
}
