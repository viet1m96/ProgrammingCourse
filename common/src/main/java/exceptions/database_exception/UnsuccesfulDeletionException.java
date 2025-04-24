package exceptions.database_exception;

public class UnsuccesfulDeletionException extends PostgresException {
    public UnsuccesfulDeletionException() {
    }

    @Override
    public String toString() {
        return "The delete action failed, maybe the collection is empty or the key is incorrect!";
    }
}
