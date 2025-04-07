package exceptions.user_exceptions;

public class AlreadySignInException extends UserException {
    public AlreadySignInException() {}

    @Override
    public String toString() {
      return "You are already signed in! Please sign_out to use this command.";
    }
}
