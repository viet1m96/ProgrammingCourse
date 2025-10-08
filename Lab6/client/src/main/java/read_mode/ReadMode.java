package read_mode;

import exceptions.log_exceptions.LogException;
import exceptions.network_exception.NetworkException;
import exceptions.user_exceptions.UserException;
import iostream.Renderer;
import network.Transporter;


public interface ReadMode {

    void executeMode(Renderer renderer, Transporter transporter, String nameCommand, String arg) throws UserException, LogException, NetworkException;
}
