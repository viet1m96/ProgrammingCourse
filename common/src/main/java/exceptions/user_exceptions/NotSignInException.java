package exceptions.user_exceptions;

public class NotSignInException extends UserException {
    public NotSignInException() {}

    @Override
    public String toString() {
        return "You are not signed in! Please sign_in first or if you dont have an account, please sign_up!";
    }
}
