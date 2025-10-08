package authorization_lib;

import exceptions.log_exceptions.LogException;
import exceptions.network_exception.NetworkException;
import iostream.Renderer;
import network.ClientTransporter;

public interface AuthMode {
    Account executeAcc(ClientTransporter transporter, Renderer renderer) throws LogException, NetworkException;
}
