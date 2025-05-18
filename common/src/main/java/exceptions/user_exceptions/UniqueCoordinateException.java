package exceptions.user_exceptions;

public class UniqueCoordinateException extends UserException {
    public UniqueCoordinateException() {}

    @Override
    public String toString() {
        return "unique.coordinate";
    }
}
