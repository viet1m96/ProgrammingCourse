package exceptions.user_exceptions;

public class NameTakenException extends UserException {
    public NameTakenException() {
    }

    @Override
    public String toString() {
        return "This username is already taken, please choose another one";
    }
}
