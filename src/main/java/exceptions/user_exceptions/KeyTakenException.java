package exceptions.user_exceptions;



public class KeyTakenException extends UserException {
    public KeyTakenException() {}
    @Override
    public String toString() {
        return "This key was taken. Please choose another one";
    }
}
