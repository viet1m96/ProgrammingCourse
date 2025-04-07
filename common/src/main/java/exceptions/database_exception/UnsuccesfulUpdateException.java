package exceptions.database_exception;

public class UnsuccesfulUpdateException extends PostgresException {
    public UnsuccesfulUpdateException() {}

    @Override
    public String toString() {
        return "The update action failed, maybe the collection is empty or the key is incorrect!";
    }
}
