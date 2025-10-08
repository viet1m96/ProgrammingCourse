package exceptions;

public class NotEnoughFuel extends RuntimeException {
    public NotEnoughFuel(String message) {
        super(message);
    }
}
