package exceptions.network_exception;

public class NetworkException extends Exception {
    public NetworkException() {
    }

    @Override
    public String toString() {
        return "There was an error while communicating with the network!";
    }
}
