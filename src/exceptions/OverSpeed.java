package exceptions;

public class OverSpeed extends RuntimeException {
    public OverSpeed(String message) {
        super(message);
    }
}
