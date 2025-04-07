package exceptions.user_exceptions;

public class AccountInputException extends UserException {
    public AccountInputException() {}

    @Override
    public String toString() {
        return "The information of account must follow the conditions.";
    }
}
