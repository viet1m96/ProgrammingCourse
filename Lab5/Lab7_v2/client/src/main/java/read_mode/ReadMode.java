package read_mode;

import authorization_lib.Account;
import exceptions.log_exceptions.LogException;
import exceptions.network_exception.NetworkException;
import exceptions.user_exceptions.UserException;
import iostream.Renderer;
import network.ClientTransporter;


public interface ReadMode {

    void executeMode(Renderer renderer, ClientTransporter clientTransporter, Account account, String nameCommand, String arg) throws UserException, LogException, NetworkException;
}
