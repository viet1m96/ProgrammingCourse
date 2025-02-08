package exceptions.user_exceptions;

public class WrongInputException extends UserException {
    public WrongInputException() {}
    @Override
    public String toString() {
        return "Wrong input, please try again";
    }

}
