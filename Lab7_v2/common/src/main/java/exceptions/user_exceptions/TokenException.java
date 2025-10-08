package exceptions.user_exceptions;

public class TokenException extends UserException {

    public TokenException() {
    }

    @Override
    public String toString() {
        return "Your token is in the wrong form, please sign in or sign up again to get the right token!";
    }
}
