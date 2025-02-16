package exceptions.user_exceptions;

public class WrongInputInFileException extends UserException {

    public WrongInputInFileException() {
    }

    @Override
    public String toString() {
        return "Wrong input in file, please check again";
    }
}