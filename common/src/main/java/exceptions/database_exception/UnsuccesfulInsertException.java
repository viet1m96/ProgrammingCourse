package exceptions.database_exception;

public class UnsuccesfulInsertException extends PostgresException {
    private String message;
    public UnsuccesfulInsertException() {}
    public UnsuccesfulInsertException(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
      return "The insert action for " + this.message + " failed";
    }

}
