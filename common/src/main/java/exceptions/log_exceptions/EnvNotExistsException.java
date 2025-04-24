package exceptions.log_exceptions;


public class EnvNotExistsException extends Exception {
    private String message;

    public EnvNotExistsException(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "The varaible" + message + "doesn't exist";
    }
}
