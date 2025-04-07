package exceptions.network_exception;

public class NetworkException extends Exception {
    public NetworkException() {}

    @Override
    public String toString() {
        return "There was an error communicating with the network! Please look at the log file for more information.";
    }
}
