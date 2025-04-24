package authorization_lib;

import exceptions.log_exceptions.LogException;
import exceptions.network_exception.NetworkException;
import iostream.Renderer;
import network.ClientTransporter;

import java.util.HashMap;

public class AuthManager {
    private final HashMap<String, AuthMode> authModes = new HashMap<>();

    public void init() {
        registerAuthMode("sign_in", new SignInMode());
        registerAuthMode("sign_up", new SignUpMode());
    }

    public void registerAuthMode(String command, AuthMode mode) {
        authModes.put(command, mode);
    }

    public Account call(String command, ClientTransporter transporter, Renderer renderer) throws NetworkException, LogException {
        return authModes.get(command.toLowerCase()).executeAcc(transporter, renderer);
    }
}
