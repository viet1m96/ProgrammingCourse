package exceptions.log_exceptions;

import exceptions.user_exceptions.UserException;

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
