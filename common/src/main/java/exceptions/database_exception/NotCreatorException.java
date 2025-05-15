package exceptions.database_exception;

public class NotCreatorException extends PostgresException {
    public NotCreatorException() {
    }

    @Override
    public String toString() {
        return "You are not allowed to modify this object because you are not its creator!";
    }
}
